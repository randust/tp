package fintrek.command.sort;

import fintrek.command.registry.CommandResult;
import fintrek.expense.service.ExpenseService;
import fintrek.misc.MessageDisplayer;
import fintrek.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static fintrek.expense.service.AppServices.RECURRING_SERVICE;
import static fintrek.expense.service.AppServices.REGULAR_SERVICE;

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
     * Tests if AddCommand is able to deal with complications regarding whitespaces
     *
     * @param input valid inputs consisting of issues with the whitespaces
     */
    @ParameterizedTest
    @CsvSource({
        "name ascending, false",
        "amount    descending, false",
        "date ascending, false",
        "name ascending, true",
        "amount    descending, true",
        "date ascending, true"
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
     * Tests if AddCommand is able to deal with complications regarding whitespaces
     *
     * @param input valid inputs consisting of issues with the whitespaces
     */
    @ParameterizedTest
    @CsvSource({
        "name  , true",
        "   descending, true",
        "date , true",
        "name  , false",
        "   descending, false",
        "date , false",
    })
    public void testSortCommand_emptySortOrDirection_fail(String input, boolean isRecurring) {
        SortCommand sortCommand = new SortCommand(isRecurring);
        CommandResult result = sortCommand.execute(input);

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandMessage(result, input,
                String.format(MessageDisplayer.INVALID_FORMAT_MESSAGE_TEMPLATE, COMMAND_NAME));
    }

    /**
     * Tests if AddCommand is able to deal with complications regarding whitespaces
     *
     * @param input valid inputs consisting of issues with the whitespaces
     */
    @ParameterizedTest
    @CsvSource({
        "ediwfo, true",
        "  amt  , true",
        "ascending, true",
        "ediwfo, false",
        "  amt  , false",
        "ascending, false"
    })
    public void testSortCommand_invalidSortField_fail(String input, boolean isRecurring) {
        SortCommand sortCommand = new SortCommand(isRecurring);
        CommandResult result = sortCommand.execute(input);

        TestUtils.assertCommandFailure(result, input);
        String expectedMessage = String.format(MessageDisplayer.INVALID_FORMAT_MESSAGE_TEMPLATE, COMMAND_NAME);
        TestUtils.assertCommandMessage(result, input, expectedMessage);
    }

    /**
     * Tests if AddCommand is able to deal with complications regarding whitespaces
     *
     * @param input valid inputs consisting of issues with the whitespaces
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
     * Tests if AddCommand is able to deal with complications regarding whitespaces
     *
     * @param input valid inputs consisting of issues with the whitespaces
     */
    @ParameterizedTest
    @CsvSource({
        "name asending, true",
        "  amount    dscending, true",
        "date fjeirjf, true",
        "name asending, false",
        "  amount    dscending, false",
        "date fjeirjf, false"
    })
    public void testSortCommand_invalidSortDirection_fail(String input, boolean isRecurring) {
        SortCommand sortCommand = new SortCommand(isRecurring);
        CommandResult result = sortCommand.execute(input);

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandMessage(result, input, MessageDisplayer.INVALID_SORT_DIRECTION);
    }
}
