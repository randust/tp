package fintrek.util;

import fintrek.Expense;
import fintrek.ExpenseManager;
import fintrek.command.registry.CommandResult;
import fintrek.misc.MessageDisplayer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestUtils {
    /**
     * Adds predefined expenses to ExpenseManager for consistent test scenarios.
     */
    public static void addConstantExpenses() {
        List<Expense> expenses = List.of(
                new Expense("lunch", 5.50, "food"),
                new Expense("taxi", 11.20, "transport"),
                new Expense("dinner", 9.80, "food"),
                new Expense("ice cream", 2.50, "food"),
                new Expense("train", 1.66, "transport"),
                new Expense("concert", 256, "entertainment")
        );
        expenses.forEach(ExpenseManager::addExpense);
    }

    //helper functions to print assertion messages
    public static void assertCommandSuccess(CommandResult result, String input) {
        assertTrue(result.isSuccess(), MessageDisplayer.ASSERT_COMMAND_SUCCESS_PREFIX + "'" + input + "'");
    }

    public static void assertCommandFailure(CommandResult result, String input) {
        assertFalse(result.isSuccess(), MessageDisplayer.ASSERT_COMMAND_FAILURE_PREFIX + "'" + input + "'");
    }

    public static void assertCommandMessage(CommandResult result, String input, String expectedMessage) {
        assertEquals(expectedMessage, result.message(),
                MessageDisplayer.ASSERT_COMMAND_EXPECTED_OUTPUT + "'" + input + "'");
    }

    public static void assertListSizeIncreased(int initialSize, String input) {
        assertEquals(initialSize + 1, ExpenseManager.getLength(),
                MessageDisplayer.ASSERT_COMMAND_LIST_LENGTH_FAILURE + "'" + input + "'");
    }

    public static void assertCorrectListSize(int expectedSize, String input) {
        assertEquals(expectedSize, ExpenseManager.getLength(),
                MessageDisplayer.ASSERT_COMMAND_LIST_LENGTH_FAILURE + "'" + input + "'");
    }

    public static void assertCorrectDesc(int initialSize, String input, String expected) {
        assertEquals(expected, ExpenseManager.getExpense(initialSize).getDescription(),
                MessageDisplayer.ASSERT_COMMAND_DESC_FAILURE + "'" + input + "'");
    }

    public static void assertCorrectAmount(int initialSize, String input, double expected) {
        assertEquals(expected, ExpenseManager.getExpense(initialSize).getAmount(),
                MessageDisplayer.ASSERT_COMMAND_AMT_FAILURE + "'" + input + "'");
    }

    public static void assertCorrectCategory(int initialSize, String input, String expected) {
        assertEquals(expected, ExpenseManager.getExpense(initialSize).getCategory(),
                MessageDisplayer.ASSERT_COMMAND_CATEGORY_FAILURE + "'" + input + "'");
    }
}
