package fintrek.command.summary;

import fintrek.expense.core.RecurringExpenseManager;
import fintrek.expense.core.RegularExpenseManager;
import fintrek.expense.service.ExpenseReporter;
import fintrek.expense.service.ExpenseService;
import fintrek.util.TestUtils;
import fintrek.command.registry.CommandResult;
import fintrek.misc.MessageDisplayer;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static fintrek.expense.service.AppServices.REGULAR_REPORTER;
import static fintrek.expense.service.AppServices.REGULAR_SERVICE;
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
    public void testAverageCommand_emptyList_anyParams_success(String input, boolean isRecurring) {
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
    public void testAverageCommand_filledList_anyParams_success(String input, boolean isRecurring) {
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
        if (isRecurring) {
            formatString = "Format: /average-recurring";
        } else {
            formatString = "Format: /average";
        }
        String expectedDescription = formatString + "\n" +
                """
                Returns average of all expenses in list, but will return 0 if the list is empty.
                Example: For a list of expenses: TransportExpense1, TransportExpense2, FoodExpense1
                /average returns (TransportExpense1 + TransportExpense2 + FoodExpense1) / 3.
                """;

        assertEquals(expectedDescription, averageCommand.getDescription(),
                MessageDisplayer.ASSERT_COMMAND_EXPECTED_OUTPUT + MessageDisplayer.ASSERT_GET_DESC);
    }
}
