package fintrek.command.summary;

import fintrek.command.registry.CommandResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
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
     * Tests total command with empty list.
     * Ensures the command returns a successful CommandResult with a total of 0.0.
     */
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void testTotalCommand_emptyList_success(boolean isRecurring) {
        TotalCommand totalCommand = new TotalCommand(isRecurring);
        CommandResult result = totalCommand.execute("");
        String expectedMessage = String.format(MessageDisplayer.TOTAL_SUCCESS_MESSAGE_TEMPLATE, 0.0);

        TestUtils.assertCommandSuccess(result, MessageDisplayer.ASSERT_EMPTY_LIST);
        TestUtils.assertCommandMessage(result, MessageDisplayer.ASSERT_EMPTY_LIST, expectedMessage);
    }

    /**
     * Tests total command with a list of predefined expenses.
     * Ensures the command calculates and returns a successful CommandResult
     * and the correct total expense amount.
     */
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void testTotalCommand_filledList_success(boolean isRecurring) {
        if (isRecurring) {
            TestUtils.addConstantRecurringExpenses();
        } else {
            TestUtils.addConstantExpenses();
        }

        TotalCommand totalCommand = new TotalCommand(isRecurring);
        CommandResult result = totalCommand.execute("");
        double expectedTotal;
        if (isRecurring) {
            expectedTotal = TestUtils.recurringReporter.getTotal();
        } else {
            expectedTotal = TestUtils.regularReporter.getTotal();
        }

        String expectedMessage = String.format(MessageDisplayer.TOTAL_SUCCESS_MESSAGE_TEMPLATE, expectedTotal);

        TestUtils.assertCommandSuccess(result, MessageDisplayer.ASSERT_FILLED_LIST);
        TestUtils.assertCommandMessage(result, MessageDisplayer.ASSERT_FILLED_LIST, expectedMessage);
    }

    /**
     * Tests the description of total command.
     * Ensures the command returns the correct description.
     */
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void testTotalCommand_getDescription_success(boolean isRecurring) {
        TotalCommand command = new TotalCommand(isRecurring);
        String expectedDescription = """
                Format: /total
                Returns sum of all expenses in the list, but will return 0 if the list is empty.
                Example: For a list of expenses: TransportExpense1, TransportExpense2, FoodExpense1
                /total returns (TransportExpense1 + TransportExpense2 + FoodExpense1).
                """;

        assertEquals(expectedDescription, command.getDescription(),
                MessageDisplayer.ASSERT_COMMAND_EXPECTED_OUTPUT + MessageDisplayer.ASSERT_GET_DESC);
    }
}
