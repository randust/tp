package fintrek.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import fintrek.ExpenseManager;
import fintrek.misc.MessageDisplayer;
import fintrek.TestUtils;

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
        DeleteCommand deleteCommand = new DeleteCommand();
        CommandResult result = deleteCommand.execute("");
        TestUtils.assertCommandFailure(result, "");
        assertEquals(MessageDisplayer.IDX_EMPTY_MESSAGE, result.message());
    }

    @ParameterizedTest
    @ValueSource(strings = {"invalid", "0.99", "2.", "1.2.3"})
    public void testDeleteCommandInvalidIndex(String input) {
        DeleteCommand deleteCommand = new DeleteCommand();
        CommandResult result = deleteCommand.execute(input);
        TestUtils.assertCommandFailure(result, input);
        assertEquals(MessageDisplayer.INVALID_IDX_FORMAT_MESSAGE, result.message());
    }

    @ParameterizedTest
    @ValueSource(strings = {"-1", "0", "999"}) // Assuming ExpenseManager has <999 items
    public void testDeleteCommandOutOfBounds(String input) {
        DeleteCommand deleteCommand = new DeleteCommand();
        CommandResult result = deleteCommand.execute(input);
        TestUtils.assertCommandFailure(result, input);
        assertEquals(MessageDisplayer.IDX_OUT_OF_BOUND_MESSAGE, result.message());
    }

    @Test
    public void testDeleteCommandValidInput() {
        DeleteCommand deleteCommand = new DeleteCommand();
        int expectedSize = ExpenseManager.getLength() - 1;
        CommandResult result = deleteCommand.execute("1");

        TestUtils.assertCommandSuccess(result, "1");
        assertEquals(String.format(MessageDisplayer.DELETE_SUCCESS_MESSAGE_TEMPLATE,
                expectedSize), result.message());
        TestUtils.assertCorrectListSize(expectedSize, "1");
    }
}
