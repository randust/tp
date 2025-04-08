package fintrek.command.summary;

import fintrek.expense.core.RecurringExpenseManager;
import fintrek.expense.core.RegularExpenseManager;
import fintrek.util.TestUtils;
import fintrek.command.registry.CommandResult;
import fintrek.misc.MessageDisplayer;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AverageCommandTest {

    /**
     * Clear all existing expenses in ExpensManager before each test.
     */
    @BeforeEach
    public void setUp() {
        RegularExpenseManager.getInstance().clear();
        RecurringExpenseManager.getInstance().clear();
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
        "                           , true",
        "                           , false",
        "hello!, true",
        "hello!, false",
        "CS2113                ,true",
        "CS2113                ,false",
        "                 Hi, true",
        "                 Hi, false",
        "  $50 04-03-2025, true",
        "  $50 04-03-2025, false"
    })
    public void testAverageCommand_emptyListAnyParams_success(String input, boolean isRecurring) {
        AverageCommand averageCommand = new AverageCommand(isRecurring);
        CommandResult result = averageCommand.execute(input);
        String expectedMessage;

        if(isRecurring) {
            expectedMessage = String.format(MessageDisplayer.AVERAGE_RECURRING_SUCCESS_MESSAGE_TEMPLATE
                    , 0.0);
        } else {
            expectedMessage = String.format(MessageDisplayer.AVERAGE_SUCCESS_MESSAGE_TEMPLATE
                    , 0.0);
        }

        TestUtils.assertCommandSuccess(result, MessageDisplayer.ASSERT_EMPTY_LIST);
        TestUtils.assertCommandMessage(result, MessageDisplayer.ASSERT_EMPTY_LIST, expectedMessage);
    }

    /**
     * Verifies that invoking the average command on a filled list of regular expenses
     * or recurring expenses with any input parameters returns the correct average amount
     * and prints the correct success message.
     */
    @ParameterizedTest
    @CsvSource({
        ", true",
        ", false",
        "                           , true",
        "                           , false",
        "hi!, true",
        "hi!, false",
        "CS2113                , false",
        "CS2113                , true",
        "                 Hello, true",
        "                 Hello, false",
    })
    public void testAverageCommand_filledListAnyParams_success(String input, boolean isRecurring) {
        TestUtils.addConstantExpenses();
        TestUtils.addConstantRecurringExpenses();
        AverageCommand averageCommand = new AverageCommand(isRecurring);
        CommandResult result = averageCommand.execute(input);
        String expectedMessage;
        double expectedAverage;

        if (isRecurring) {
            expectedAverage = TestUtils.recurringReporter.getAverage();
            expectedMessage = String.format(MessageDisplayer.AVERAGE_RECURRING_SUCCESS_MESSAGE_TEMPLATE
                    ,expectedAverage);
        } else {
            expectedAverage = TestUtils.regularReporter.getAverage();
            expectedMessage = String.format(MessageDisplayer.AVERAGE_SUCCESS_MESSAGE_TEMPLATE, expectedAverage);
        }
        TestUtils.assertCommandSuccess(result, MessageDisplayer.ASSERT_FILLED_LIST);
        TestUtils.assertCommandMessage(result, MessageDisplayer.ASSERT_FILLED_LIST, expectedMessage);
    }

    /**
     * Tests the description of the average command.
     * Ensures the command returns the correct description.
     */
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void testAverageCommand_getDescription_success(boolean isRecurring) {
        AverageCommand averageCommand = new AverageCommand(isRecurring);
        String formatString;
        String exampleString;
        if (isRecurring) {
            formatString = "Format: /average-recurring";
            exampleString = "/average-recurring returns (TransportExpense1 + TransportExpense2 + FoodExpense1) / 3.";
        } else {
            formatString = "Format: /average";
            exampleString = "/average returns (TransportExpense1 + TransportExpense2 + FoodExpense1) / 3.";
        }
        String expectedDescription = formatString + "\n" +
                """
                Returns average of all expenses in list, but will return 0 if the list is empty.
                Example: For a list of expenses: TransportExpense1, TransportExpense2, FoodExpense1
                """
                + exampleString;

        assertEquals(expectedDescription, averageCommand.getDescription(),
                MessageDisplayer.ASSERT_COMMAND_EXPECTED_OUTPUT + MessageDisplayer.ASSERT_GET_DESC);
    }

    /**
     * Verifies that attempting to calculate the average amount spent out of
     * a list of expenses whose total amount exceeds $10 billion returns an error message.
     * @param isRecurring a boolean value indicating if the expense is recurring.
     */
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void testAverageCommand_expensiveFilledList_returnsError(boolean isRecurring) {
        TestUtils.addHugeConstantExpenses();
        TestUtils.addHugeConstantRecurringExpenses();
        AverageCommand command = new AverageCommand(isRecurring);
        CommandResult result = command.execute("");

        TestUtils.assertCommandMessage(result, MessageDisplayer.ASSERT_FILLED_LIST,
                MessageDisplayer.ERROR_CALCULATING_AVERAGE_EXPENSES +
                        MessageDisplayer.TOTAL_EXCEEDS_LIMIT_MSG);
        TestUtils.assertCommandFailure(result, MessageDisplayer.ASSERT_FILLED_LIST);

    }
}
