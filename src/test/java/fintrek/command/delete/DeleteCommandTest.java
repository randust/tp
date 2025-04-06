package fintrek.command.delete;

import fintrek.command.registry.CommandResult;
import fintrek.command.sort.SortCommand;
import fintrek.expense.core.Expense;
import fintrek.expense.core.RegularExpenseManager;
import fintrek.expense.service.ExpenseService;
import fintrek.misc.MessageDisplayer;
import fintrek.util.TestUtils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static fintrek.expense.service.AppServices.REGULAR_SERVICE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeleteCommandTest {
    private ExpenseService service;

    /**
     * Clears all existing expenses in RegularExpenseManager before each test.
     */
    @BeforeEach
    public void setUp() {
        RegularExpenseManager.getInstance().clear();
        service = REGULAR_SERVICE;
        TestUtils.addConstantExpenses();
    }

    /**
     * Verifies that invoking the delete command without providing an index
     * prints the appropriate error message.
     */
    @Test
    public void testDeleteCommandEmptyIndex() {
        DeleteCommand deleteCommand = new DeleteCommand(false);
        CommandResult result = deleteCommand.execute("");

        TestUtils.assertCommandFailure(result, "");
        TestUtils.assertCommandMessage(result, "", MessageDisplayer.IDX_EMPTY_MESSAGE);
    }

    /**
     * Verifies that invoking the delete command with various invalid forms of indices
     * returns an error message
     * @param input a String of the form of an invalid index
     */
    @ParameterizedTest
    @ValueSource(strings = {"invalid", "0.99", "2.", "1.2.3", "-1", "0"})
    public void testDeleteCommandInvalidIndex(String input) {
        DeleteCommand deleteCommand = new DeleteCommand(false);
        CommandResult result = deleteCommand.execute(input);

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandMessage(result, input, MessageDisplayer.INVALID_IDX_FORMAT_MESSAGE);
    }

    /**
     * Verifies that attempting to delete an expense at an index beyond the current list size
     * returns an appropriate error message.
     * @param input a String containing a large number beyond the size of the list
     */
    @ParameterizedTest
    @ValueSource(strings = {"999"}) // Assuming <999 expenses exist
    public void testDeleteCommandOutOfBounds(String input) {
        DeleteCommand deleteCommand = new DeleteCommand(false);
        CommandResult result = deleteCommand.execute(input);

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandMessage(result, input, MessageDisplayer.IDX_OUT_OF_BOUND_MESSAGE);
    }

    /**
     * Verifies that deleting the first expense out a nonempty list of expenses
     * deletes the correct expense and deducts the current size by 1.
     */
    @Test
    public void testDeleteCommandValidInput() {
        DeleteCommand deleteCommand = new DeleteCommand(false);
        int expectedSize = service.countExpenses() - 1;
        Expense removedExpense = service.getExpense(0);
        String expenseStr = '"' + removedExpense.toString() + '"';

        CommandResult result = deleteCommand.execute("1");

        TestUtils.assertCommandSuccess(result, "1");

        String expectedMessage = String.format(MessageDisplayer.DELETE_SUCCESS_MESSAGE_TEMPLATE,
                expenseStr, expectedSize);
        TestUtils.assertCommandMessage(result, "1", expectedMessage);

        // Re-validate count from service
        assert expectedSize == service.countExpenses();
    }

    /**
     * Tests the description of the delete command.
     * Verifies the command returns the correct description.
     */
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void testDeleteCommand_getDescription_success(boolean isRecurring) {
        DeleteCommand deleteCommand = new DeleteCommand(isRecurring);
        String formatString;
        if (isRecurring) {
            formatString = "Format: /delete-recurring <RECURRING_EXPENSE_NUMBER>";
        } else {
            formatString = "Format: /delete <EXPENSE_NUMBER>";
        }
        String expectedDescription = formatString + "\n" +
            """
            INDEX must be a positive integer > 0
            Example: /delete 2 - deletes the expense with index number 2 on the list.
            """;

        assertEquals(expectedDescription, deleteCommand.getDescription(),
                MessageDisplayer.ASSERT_COMMAND_EXPECTED_OUTPUT + MessageDisplayer.ASSERT_GET_DESC);
    }
}
