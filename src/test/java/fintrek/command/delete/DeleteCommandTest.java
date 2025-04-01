package fintrek.command.delete;

import fintrek.command.registry.CommandResult;
import fintrek.expense.core.Expense;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import fintrek.expense.ExpenseManager;
import fintrek.misc.MessageDisplayer;
import fintrek.util.TestUtils;

public class DeleteCommandTest {
    /**
     * Clear all existing expenses in ExpenseManager and adds set list of expenses before each test.
     */
    @BeforeEach
    public void setUp() {
        ExpenseManager.clearExpenses();
        TestUtils.addConstantExpenses();
    }

    @Test
    public void testDeleteCommandEmptyIndex() {
        DeleteCommand deleteCommand = new DeleteCommand(false);
        CommandResult result = deleteCommand.execute("");

        TestUtils.assertCommandFailure(result, "");
        TestUtils.assertCommandMessage(result, "", MessageDisplayer.IDX_EMPTY_MESSAGE);
    }

    @ParameterizedTest
    @ValueSource(strings = {"invalid", "0.99", "2.", "1.2.3", "-1", "0"})
    public void testDeleteCommandInvalidIndex(String input) {
        DeleteCommand deleteCommand = new DeleteCommand(false);
        CommandResult result = deleteCommand.execute(input);

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandMessage(result, input, MessageDisplayer.INVALID_IDX_FORMAT_MESSAGE);
    }

    @ParameterizedTest
    @ValueSource(strings = {"999"}) // Assuming ExpenseManager has <999 items
    public void testDeleteCommandOutOfBounds(String input) {
        DeleteCommand deleteCommand = new DeleteCommand(false);
        CommandResult result = deleteCommand.execute(input);

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandMessage(result, input, MessageDisplayer.IDX_OUT_OF_BOUND_MESSAGE);
    }

    @Test
    public void testDeleteCommandValidInput() {
        DeleteCommand deleteCommand = new DeleteCommand(false);
        int expectedSize = ExpenseManager.getLength() - 1;
        Expense removedExpense = ExpenseManager.getExpense(0);
        String expenseStr = '"' + removedExpense.toString() + '"';
        CommandResult result = deleteCommand.execute("1");

        TestUtils.assertCommandSuccess(result, "1");
        String expectedMessage = String.format(MessageDisplayer.DELETE_SUCCESS_MESSAGE_TEMPLATE,
                expenseStr, expectedSize);
        TestUtils.assertCommandMessage(result, "1", expectedMessage);
        TestUtils.assertCorrectListSize(expectedSize, "1");
    }
}
