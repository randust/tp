package fintrek.util;


import fintrek.AppServices;
import fintrek.command.registry.CommandResult;
import fintrek.expense.core.Expense;
import fintrek.expense.ExpenseManager;
import fintrek.expense.service.ExpenseReporter;
import fintrek.expense.service.ExpenseService;
import fintrek.misc.MessageDisplayer;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestUtils {
    // ==== REGULAREXPENSEMANAGER AND RECURRINGEXPENSEMANAGER FOR TESTS ====
    public static ExpenseReporter regularReporter = AppServices.REGULAR_REPORTER;
    public static ExpenseService regularService = AppServices.REGULAR_SERVICE;
    public static ExpenseReporter recurringReporter = AppServices.RECURRING_REPORTER;
    public static ExpenseService recurringService = AppServices.RECURRING_SERVICE;

    // ==== SHARED TEST CONSTANTS ====
    public static final int EXPECTED_TEST_EXPENSE_COUNT = 6;
    public static final String FIRST_TEST_DESC = "lunch";
    public static final double TOTAL_TEST_EXPENSE_SUM =
            5.50 + 11.20 + 9.80 + 2.50 + 1.66 + 256.00;

    public static final String INSERTED_DESC = "spotify";
    public static final double INSERTED_AMOUNT = 9.99;
    public static final String INSERTED_CATEGORY = "entertainment";

    // ==== CATEGORY CONSTANTS FOR TEST EXPENSES ====
    public static final int FOOD_EXPENSE_COUNT = 3;
    public static final int TRANSPORT_EXPENSE_COUNT = 2;
    public static final int ENTERTAINMENT_EXPENSE_COUNT = 1;

    public static final double FOOD_TOTAL = 5.50 + 9.80 + 2.50;           // = 17.80
    public static final double TRANSPORT_TOTAL = 11.20 + 1.66;            // = 12.86
    public static final double ENTERTAINMENT_TOTAL = 256.00;

    public static final double EXPECTED_AVERAGE =
            TOTAL_TEST_EXPENSE_SUM / EXPECTED_TEST_EXPENSE_COUNT;

    public static final String HIGHEST_SPEND_CATEGORY = "ENTERTAINMENT";
    public static final double HIGHEST_SPEND_AMOUNT = ENTERTAINMENT_TOTAL;

    /**
     * Adds predefined expenses to ExpenseManager for consistent test scenarios.
     */
    public static void addConstantExpenses() {
        LocalDate today = LocalDate.now();
        List<Expense> expenses = List.of(
                new Expense("lunch", 5.50, "food", today),
                new Expense("taxi", 11.20, "transport", today),
                new Expense("dinner", 9.80, "food", today),
                new Expense("ice cream", 2.50, "food", today),
                new Expense("train", 1.66, "transport", today),
                new Expense("concert", 256, "entertainment", today)
        );
        expenses.forEach(regularService::addExpense);
    }

    public static void addConstantRecurringExpenses() {
        LocalDate today = LocalDate.now();
        List<Expense> expenses = List.of(
                new Expense("lunch", 5.50, "food", today),
                new Expense("taxi", 11.20, "transport", today),
                new Expense("dinner", 9.80, "food", today),
                new Expense("ice cream", 2.50, "food", today),
                new Expense("train", 1.66, "transport", today),
                new Expense("concert", 256, "entertainment", today)
        );
        expenses.forEach(ExpenseManager::addRecurringExpense);
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

    public static void assertCommandErrorMessage(CommandResult result, String input, String expectedMessage) {
        assertEquals(expectedMessage, result.message(),
                MessageDisplayer.ASSERT_EXPECTED_ERROR + "'" + input + "'");
    }

    public static void assertListSizeIncreased(int initialSize, String input) {
        assertEquals(initialSize + 1, ExpenseManager.getLength(),
                MessageDisplayer.ASSERT_COMMAND_LIST_LENGTH_FAILURE + "'" + input + "'");
    }

    public static void assertCorrectListSize(int expectedSize, String input) {
        assertEquals(expectedSize, ExpenseManager.getLength(),
                MessageDisplayer.ASSERT_COMMAND_LIST_LENGTH_FAILURE + "'" + input + "'");
    }

    public static void assertCorrectRecurringListSize(int expectedSize, String input) {
        assertEquals(expectedSize, ExpenseManager.checkRecurringExpenseSize(),
                MessageDisplayer.ASSERT_COMMAND_LIST_LENGTH_FAILURE + "'" + input + "'");
    }

    public static void assertCorrectDesc(int initialSize, String input, String expected) {
        assertEquals(expected, ExpenseManager.getExpense(initialSize).getDescription(),
                MessageDisplayer.ASSERT_COMMAND_DESC_FAILURE + "'" + input + "'");
    }

    public static void assertCorrectRecurringDesc(int initialSize, String input, String expected) {
        assertEquals(expected, ExpenseManager.getRecurringExpense(initialSize).getDescription(),
                MessageDisplayer.ASSERT_COMMAND_DESC_FAILURE + "'" + input + "'");
    }

    public static void assertCorrectAmount(int initialSize, String input, double expected) {
        assertEquals(expected, ExpenseManager.getExpense(initialSize).getAmount(),
                MessageDisplayer.ASSERT_COMMAND_AMT_FAILURE + "'" + input + "'");
    }

    public static void assertCorrectRecurringAmount(int initialSize, String input, double expected) {
        assertEquals(expected, ExpenseManager.getRecurringExpense(initialSize).getAmount(),
                MessageDisplayer.ASSERT_COMMAND_AMT_FAILURE + "'" + input + "'");
    }

    public static void assertCorrectCategory(int initialSize, String input, String expected) {
        assertEquals(expected, ExpenseManager.getExpense(initialSize).getCategory(),
                MessageDisplayer.ASSERT_COMMAND_CATEGORY_FAILURE + "'" + input + "'");
    }

    public static void assertCorrectRecurringCategory(int initialSize, String input, String expected) {
        assertEquals(expected, ExpenseManager.getRecurringExpense(initialSize).getCategory(),
                MessageDisplayer.ASSERT_COMMAND_CATEGORY_FAILURE + "'" + input + "'");
    }

}
