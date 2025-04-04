package fintrek.command.list;

import fintrek.command.registry.CommandResult;
import fintrek.expense.service.AppServices;
import fintrek.expense.service.ExpenseReporter;
import fintrek.expense.service.ExpenseService;
import fintrek.misc.MessageDisplayer;
import fintrek.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static fintrek.expense.service.AppServices.*;

public class SortCommandTest {
    private ExpenseReporter reporter;

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
            "name asending, true",
            "amount    dscending, true",
            "date fjeirjf, true",
            "name asending, false",
            "amount    dscending, false",
            "date fjeirjf, false"
    })
    public void testSortCommandInvalidFormat(String input, boolean isRecurring) {
        SortCommand sortCommand = new SortCommand(isRecurring);
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
    @CsvSource({
            "ediwfo, true",
            "  amt  , true",
            ",true",
            "     ,true",
            "ascending, true",
            "ediwfo, false",
            "  amt  , false",
            ",false",
            "     ,false",
            "ascending, false"
    })
    public void testSortCommandInvalidSortField(String input, boolean isRecurring) {
        SortCommand sortCommand = new SortCommand(isRecurring);
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
    @CsvSource({
            "name asending, true",
            "  amount    dscending, true",
            "date fjeirjf, true",
            "name asending, false",
            "  amount    dscending, false",
            "date fjeirjf, false"
    })
    public void testSortCommandInvalidSortDirection(String input, boolean isRecurring) {
        SortCommand sortCommand = new SortCommand(isRecurring);
        CommandResult result = sortCommand.execute(input);

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandMessage(result, input, MessageDisplayer.INVALID_SORT_DIRECTION);
    }
}
