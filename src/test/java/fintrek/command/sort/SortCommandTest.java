package fintrek.command.sort;

import fintrek.command.list.ListCommand;
import fintrek.command.registry.CommandResult;
import fintrek.expense.service.ExpenseService;
import fintrek.misc.MessageDisplayer;
import fintrek.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static fintrek.expense.service.AppServices.RECURRING_SERVICE;
import static fintrek.expense.service.AppServices.REGULAR_SERVICE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SortCommandTest {
    private static final String COMMAND_NAME = "sort";

    /**
     * Clear all existing expenses in ExpenseManager before each test.
     */
    @BeforeEach
    public void setUp() {
        TestUtils.regularService.clearExpenses();
        TestUtils.recurringService.clearExpenses();
        TestUtils.addConstantExpenses();
        TestUtils.addConstantRecurringExpenses();
    }

    /**
     * Verifies that attempting to call the sort command with valid inputs
     * consisting of valid sort fields, sort directions, and isRecurring status works.
     * @param input String containing valid sort field and valid sort direction.
     * @param isRecurring Boolean value representing whether the expense is recurring.
     */
    @ParameterizedTest
    @CsvSource({
        "name asc, false",
        "amount    dsc, false",
        "date asc, false",
        "name asc, true",
        "amount    dsc, true",
        "date asc, true"
    })
    public void testSortCommandValidInput(String input, boolean isRecurring) {
        SortCommand sortCommand = new SortCommand(isRecurring);
        ExpenseService service;
        if (isRecurring) {
            service = RECURRING_SERVICE;
        } else {
            service = REGULAR_SERVICE;
        }
        int initialSize = service.countExpenses();
        CommandResult result = sortCommand.execute(input);

        TestUtils.assertCommandSuccess(result, input);
        TestUtils.assertCorrectListSize(initialSize, input);
    }

    /**
     * Verifies that attempting to invoke the sort command with an empty sort field
     * or empty sort direction results in failure and will print the appropriate error
     * message for both recurring and regular expenses.
     * @param input String with either the sort field or sort direction left empty.
     * @param isRecurring Boolean value which indicates if the expense is recurring.
     */
    @ParameterizedTest
    @CsvSource({
        "name  , true",
        "   dsc, true",
        "date , true",
        "name  , false",
        "   dsc, false",
        "date , false",
    })
    public void testSortCommand_emptySortFieldOrDirection_fail(String input, boolean isRecurring) {
        SortCommand sortCommand = new SortCommand(isRecurring);
        CommandResult result = sortCommand.execute(input);

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandMessage(result, input,
                String.format(MessageDisplayer.INVALID_FORMAT_MESSAGE_TEMPLATE, COMMAND_NAME));
    }

    /**
     * Verifies that attempting to invoke the sort command with an invalid
     * sort field fails and will print the appropriate error message for both
     * recurring and regular expenses.
     * @param input String containing an invalid sort field.
     * @param isRecurring Boolean value which states if the expense is recurring.
     */
    @ParameterizedTest
    @CsvSource({
        "ediwfo, true",
        "  amt  , true",
        "asc, true",
        "ediwfo, false",
        "  amt  , false",
        "asc, false"
    })
    public void testSortCommand_invalidSortField_fail(String input, boolean isRecurring) {
        SortCommand sortCommand = new SortCommand(isRecurring);
        CommandResult result = sortCommand.execute(input);

        TestUtils.assertCommandFailure(result, input);
        String expectedMessage = String.format(MessageDisplayer.INVALID_FORMAT_MESSAGE_TEMPLATE, COMMAND_NAME);
        TestUtils.assertCommandMessage(result, input, expectedMessage);
    }

    /**
     * Verifies that attempting to sort with an empty sort field and empty sort direction
     * results in failure and will print the appropriate error message for both recurring
     * and regular expenses.
     * @param input String containing no sort field or sort direction
     */
    @ParameterizedTest
    @CsvSource({
        ",true",
        "     ,true",
        ",false",
        "     ,false"
    })
    public void testSortCommand_emptySortField_fail(String input) {
        SortCommand sortCommand = new SortCommand(false);
        CommandResult result = sortCommand.execute(input);

        TestUtils.assertCommandFailure(result, input);
        String expectedMessage = String.format(MessageDisplayer.ARG_EMPTY_MESSAGE_TEMPLATE, COMMAND_NAME);
        TestUtils.assertCommandMessage(result, input, expectedMessage);
    }

    /**
     * Verifies that calling the sort command with an invalid sort direction
     * will result in failure and will print the appropriate error message for both
     * recurring and regular expenses
     * @param input String containing a valid sort field with an invalid sort direction.
     * @param isRecurring Boolean value which states if the expense is recurring.
     */
    @ParameterizedTest
    @CsvSource({
        "name ascs, true",
        "  amount    desc, true",
        "date fjeirjf, true",
        "name ascs, false",
        "  amount    desc, false",
        "date fjeirjf, false"
    })
    public void testSortCommand_invalidSortDirection_fail(String input, boolean isRecurring) {
        SortCommand sortCommand = new SortCommand(isRecurring);
        CommandResult result = sortCommand.execute(input);

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandMessage(result, input, MessageDisplayer.INVALID_SORT_DIRECTION);
    }

    /**
     * Tests the description of the list-sort command.
     * Verifies the command returns the correct description.
     */
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void testSortCommand_getDescription_success(boolean isRecurring) {
        SortCommand sortCommand = new SortCommand(isRecurring);
        String formatString;
        if (isRecurring) {
            formatString = "Format: /list-sorted-recurring <SORT FIELD> <SORT DIRECTION>";
        } else {
            formatString = "Format: /list-sorted <SORT FIELD> <SORT DIRECTION>";
        }
        String expectedDescription = formatString + "\n" +
                """
                SORT FIELD valid inputs: name, amount, category, date
                SORT DIRECTION valid inputs: asc, dsc
                Example: /list-sorted name asc - prints sorted list in ascending alphabetical order.
                """;

        assertEquals(expectedDescription, sortCommand.getDescription(),
                MessageDisplayer.ASSERT_COMMAND_EXPECTED_OUTPUT + MessageDisplayer.ASSERT_GET_DESC);
    }
}
