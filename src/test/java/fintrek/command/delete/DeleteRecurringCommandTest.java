//@@Charly2312
package fintrek.command.delete;

import fintrek.command.registry.CommandResult;
import fintrek.expense.ExpenseManager;
import fintrek.expense.core.Expense;
import fintrek.misc.MessageDisplayer;
import fintrek.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class DeleteRecurringCommandTest {
    /**
     * Clear all existing expenses in ExpenseManager and adds set list of expenses before each test.
     */
    @BeforeEach
    public void setUp() {
        ExpenseManager.clearRecurringExpenses();
        TestUtils.addConstantRecurringExpenses();
    }

    @Test
    public void testDeleteRecurringCommandEmptyIndexFail() {
        DeleteRecurringCommand deleteCommand = new DeleteRecurringCommand(true);
        CommandResult result = deleteCommand.execute("");

        TestUtils.assertCommandFailure(result, "");
        TestUtils.assertCommandMessage(result, "", MessageDisplayer.IDX_EMPTY_MESSAGE);
    }

    @ParameterizedTest
    @ValueSource(strings = {"invalid", "0.99", "2.", "1.2.3", "-1", "0"})
    public void testDeleteRecurringCommandInvalidIndexFail(String input) {
        DeleteRecurringCommand deleteCommand = new DeleteRecurringCommand(true);
        CommandResult result = deleteCommand.execute(input);

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandMessage(result, input, MessageDisplayer.INVALID_IDX_FORMAT_MESSAGE);
    }

    @ParameterizedTest
    @ValueSource(strings = {"999"}) // Assuming ExpenseManager has <999 items
    public void testDeleteRecurringCommandOutOfBoundsFail(String input) {
        DeleteRecurringCommand deleteCommand = new DeleteRecurringCommand(true);
        CommandResult result = deleteCommand.execute(input);

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandMessage(result, input, MessageDisplayer.IDX_OUT_OF_BOUND_MESSAGE);
    }

    @Test
    public void testDeleteCommandValidInputSuccess() {
        DeleteRecurringCommand deleteCommand = new DeleteRecurringCommand(true);
        int expectedSize = ExpenseManager.checkRecurringExpenseSize() - 1;
        Expense removedExpense = ExpenseManager.getRecurringExpense(0);
        String expenseStr = '"' + removedExpense.toString() + '"';
        CommandResult result = deleteCommand.execute("1");

        TestUtils.assertCommandSuccess(result, "1");
        String expectedMessage = String.format(MessageDisplayer.DELETE_RECURRING_SUCCESS_MESSAGE_TEMPLATE,
                expenseStr, expectedSize);
        TestUtils.assertCommandMessage(result, "1", expectedMessage);
        TestUtils.assertCorrectRecurringListSize(expectedSize, "1");
    }
}
