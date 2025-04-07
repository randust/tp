package fintrek.command.summary;

import fintrek.command.registry.CommandResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import fintrek.misc.MessageDisplayer;
import fintrek.util.TestUtils;

/**
 * Unit tests for the {@code TotalCommand} class.
 * Ensures that the total expense amount is correctly calculated and returned.
 */
public class TotalCommandTest {
    /**
     * Clear all existing expenses in ExpenseManager before each test.
     */
    @BeforeEach
    public void setUp() {
        TestUtils.regularService.clearExpenses();
        TestUtils.recurringService.clearExpenses();
    }

    /**
     * Verifies that executing the average command with any input parameters
     * on an empty list of regular expenses or recurring expenses returns the correct
     * average amount of 0.0 and prints the appropriate success message.
     *
     */
    @ParameterizedTest
    @CsvSource({
        ", true",
        ", false",
        "             , true",
        "             , false",
        "hello world!, true",
        "hello world!, false",
        "CS2113      ,true",
        "CS2113      ,false",
        "          FinTrek, true",
        "          FinTrek, false",
        "  /help total, true",
        "  /help total, false",
    })
    public void testTotalCommand_emptyListAnyParams_success(String input, boolean isRecurring) {
        TotalCommand totalCommand = new TotalCommand(isRecurring);
        CommandResult result = totalCommand.execute(input);
        String expectedMessage = String.format(MessageDisplayer.TOTAL_SUCCESS_MESSAGE_TEMPLATE, 0.0);

        TestUtils.assertCommandSuccess(result, MessageDisplayer.ASSERT_EMPTY_LIST);
        TestUtils.assertCommandMessage(result, MessageDisplayer.ASSERT_EMPTY_LIST, expectedMessage);
    }

    /**
     * Verifies that executing the average command with any input parameters
     * on an empty list of regular expenses or recurring expenses returns the correct
     * average amount of 0.0 and prints the appropriate success message.
     *
     */
    @ParameterizedTest
    @CsvSource({
        ", true",
        ", false",
        "             , true",
        "             , false",
        "hello world!, true",
        "hello world!, false",
        "CS2113      ,true",
        "CS2113      ,false",
        "          FinTrek, true",
        "          FinTrek, false",
        "  /help total, true",
        "  /help total, false",
    })
    public void testTotalCommand_filledList_success(String input, boolean isRecurring) {
        TestUtils.addConstantRecurringExpenses();
        TestUtils.addConstantExpenses();
        double expectedTotal;
        if (isRecurring) {
            expectedTotal = TestUtils.recurringReporter.getTotal();
        } else {
            expectedTotal = TestUtils.regularReporter.getTotal();
        }

        TotalCommand totalCommand = new TotalCommand(isRecurring);
        CommandResult result = totalCommand.execute(input);

        String expectedMessage = String.format(MessageDisplayer.TOTAL_SUCCESS_MESSAGE_TEMPLATE, expectedTotal);

        TestUtils.assertCommandSuccess(result, MessageDisplayer.ASSERT_FILLED_LIST);
        TestUtils.assertCommandMessage(result, MessageDisplayer.ASSERT_FILLED_LIST, expectedMessage);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void testTotalCommand_expensiveFilledList_returnsError(boolean isRecurring) {
        TestUtils.addHugeConstantExpenses();
        TestUtils.addHugeConstantRecurringExpenses();
        TotalCommand command = new TotalCommand(isRecurring);
        CommandResult result = command.execute("");

        TestUtils.assertCommandFailure(result, MessageDisplayer.ASSERT_FILLED_LIST);
        TestUtils.assertCommandMessage(result, MessageDisplayer.ASSERT_FILLED_LIST,
                MessageDisplayer.ERROR_CALCULATING_TOTAL_EXPENSES +
                        MessageDisplayer.TOTAL_EXCEEDS_LIMIT_MSG);

    }

    /**
     * Tests the description of total command.
     * Ensures the command returns the correct description.
     */
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void testTotalCommand_getDescription_success(boolean isRecurring) {
        TotalCommand command = new TotalCommand(isRecurring);
        String expectedDescription;
        if (isRecurring) {
            expectedDescription = """
                Format: /total-recurring
                Returns sum of all expenses in the list, but will return 0 if the list is empty.
                Example: For a list of expenses: TransportExpense1, TransportExpense2, FoodExpense1
                /total-recurring returns (TransportExpense1 + TransportExpense2 + FoodExpense1).""";
        } else {
            expectedDescription = """
                Format: /total
                Returns sum of all expenses in the list, but will return 0 if the list is empty.
                Example: For a list of expenses: TransportExpense1, TransportExpense2, FoodExpense1
                /total returns (TransportExpense1 + TransportExpense2 + FoodExpense1).""";
        }


        assertEquals(expectedDescription, command.getDescription(),
                MessageDisplayer.ASSERT_COMMAND_EXPECTED_OUTPUT + MessageDisplayer.ASSERT_GET_DESC);
    }
}
