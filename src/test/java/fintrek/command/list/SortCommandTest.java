package fintrek.command.list;

import fintrek.command.add.AddCommand;
import fintrek.command.registry.CommandResult;
import fintrek.expense.service.ExpenseReporter;
import fintrek.expense.service.ExpenseService;
import fintrek.misc.MessageDisplayer;
import fintrek.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static fintrek.expense.AppServices.REGULAR_REPORTER;
import static fintrek.expense.AppServices.REGULAR_SERVICE;

public class SortCommandTest {
    private ExpenseService service;
    private ExpenseReporter reporter;

    /**
     * Clear all existing expenses in ExpenseManager before each test.
     */
    @BeforeEach
    public void setUp() {
        service = REGULAR_SERVICE;
        reporter = REGULAR_REPORTER;
        service.clearExpenses();
        TestUtils.addConstantExpenses();
    }

    /**
     * Tests if AddCommand is able to deal with complications regarding whitespaces
     *
     * @param input valid inputs consisting of issues with the whitespaces
     */
    @ParameterizedTest
    @ValueSource(strings = {"name ascending", "amount    descending", "date ascending"})
    public void testSortCommandValidInput(String input) {
        SortCommand sortCommand = new SortCommand(false);
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
    @ValueSource(strings = {"name asending", "amount    dscending", "date fjeirjf"})
    public void testSortCommandInvalidFormat(String input) {
        SortCommand sortCommand = new SortCommand(false);
        CommandResult result = sortCommand.execute(input);

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandMessage(result, input, MessageDisplayer.INVALID_SORT_DIRECTION);
    }

    /**
     * Tests if AddCommand is able to deal with complications regarding whitespaces
     *
     * @param input valid inputs consisting of issues with the whitespaces
     */
    @ParameterizedTest
    @ValueSource(strings = {"ediwfo", "  amt  ", "", "     ", "ascending"})
    public void testSortCommandInvalidSortField(String input) {
        SortCommand sortCommand = new SortCommand(false);
        CommandResult result = sortCommand.execute(input);

        TestUtils.assertCommandFailure(result, input);
        String expectedMessage = String.format(MessageDisplayer.INVALID_FORMAT_MESSAGE_TEMPLATE, "sort");
        TestUtils.assertCommandMessage(result, input, expectedMessage);
    }

    /**
     * Tests if AddCommand is able to deal with complications regarding whitespaces
     *
     * @param input valid inputs consisting of issues with the whitespaces
     */
    @ParameterizedTest
    @ValueSource(strings = {"name asending", "  amount    dscending", "date fjeirjf"})
    public void testSortCommandInvalidSortDirection(String input) {
        SortCommand sortCommand = new SortCommand(false);
        CommandResult result = sortCommand.execute(input);

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandMessage(result, input, MessageDisplayer.INVALID_SORT_DIRECTION);
    }
}
