package fintrek.expense.service;

import fintrek.util.TestUtils;
import fintrek.misc.MessageDisplayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit tests for {@code ExpenseReporter} class.
 * Verifies correct behaviour of both regular and recurring instances of {@code ExpenseReporter}.
 */
public class ExpenseReporterTest {
    private ExpenseReporter reporter;

    /**
     * Clears the list of regular expenses before each test.
     * Adds a list of predefined regular expenses to RegularExpenseManager.
     */
    @BeforeEach
    void setUp() {
        AppServices.REGULAR_SERVICE.clearExpenses();
        TestUtils.addConstantExpenses();
        reporter = AppServices.REGULAR_REPORTER;
    }

    /**
     * Verifies that the total spending of all expenses in the list is correctly calculated.
     */
    @Test
    void testGetTotal() {
        assertEquals(TestUtils.TOTAL_TEST_EXPENSE_SUM, reporter.getTotal(), TestUtils.DELTA);
    }

    /**
     * Verifies that the total spending in each category are correctly calculated.
     */
    @Test
    void testGetTotalByCategory() {
        Map<String, Double> map = reporter.getTotalByCategory();
        assertEquals(TestUtils.FOOD_TOTAL, map.get(TestUtils.CATEGORY_FOOD), TestUtils.DELTA);
        assertEquals(TestUtils.TRANSPORT_TOTAL, map.get(TestUtils.CATEGORY_TRANSPORT), TestUtils.DELTA);
        assertEquals(TestUtils.ENTERTAINMENT_TOTAL, map.get(TestUtils.CATEGORY_ENTERTAINMENT), TestUtils.DELTA);
    }

    /**
     * Verifies that the highest spending category and amount is correctly identified and calculated.
     */
    @Test
    void getHighestCategory_returnsCorrectCategoryAndAmount() {
        Map<String, Double> categoryTotals = reporter.getTotalByCategory();
        String result = reporter.getHighestCategory(categoryTotals);
        boolean correct = result.contains(TestUtils.HIGHEST_SPEND_CATEGORY)
                && result.contains(String.valueOf(TestUtils.HIGHEST_SPEND_AMOUNT));
        assertTrue(correct);
    }

    /**
     * Verifies that the average amount of the total expenses is correctly calculated.
     */
    @Test
    void testGetAverage() {
        assertEquals(TestUtils.EXPECTED_AVERAGE, reporter.getAverage(), TestUtils.DELTA);
    }

    /**
     * Verifies that the total spending in a specific month is correctly calculated.
     */
    @Test
    void testGetTotalByMonth() {
        LocalDate today = LocalDate.now();
        double expected = TestUtils.TOTAL_TEST_EXPENSE_SUM;
        double actual = reporter.getTotalByMonthOfYear(today.getYear(), today.getMonthValue());
        assertEquals(expected, actual, TestUtils.DELTA);
    }

    /**
     * Verifies that the correct list of expenses is printed for a non-empty list of expenses.
     */
    @Test
    void testListExpensesFormatNotEmpty() {
        String list = reporter.listExpenses();
        assertFalse(list.isEmpty());
        assertTrue(list.contains("$"));
    }

    /**
     * Verifies that the correct categories in the expense list are identified and
     * the grand total is correctly calculated.
     */
    @Test
    void testListAllCategoryTotals() {
        Map<String, Double> categoryTotals = reporter.getTotalByCategory();
        String result = reporter.listAllCategoryTotals(categoryTotals);
        assertTrue(result.contains(TestUtils.CATEGORY_FOOD));
        assertTrue(result.contains(TestUtils.CATEGORY_TRANSPORT));
        assertTrue(result.contains(TestUtils.CATEGORY_ENTERTAINMENT));
        assertTrue(result.contains(MessageDisplayer.SUMMARY_GRAND_TOTAL));
    }

    /**
     * Verifies that the correct category is identified and the expenses in this category are listed.
     */
    @Test
    void testListSingleCategoryTotal_success() {
        Map<String, Double> categoryTotals = reporter.getTotalByCategory();
        String result = reporter.listSingleCategoryTotal(categoryTotals, TestUtils.CATEGORY_FOOD);
        assertTrue(result.contains(TestUtils.CATEGORY_FOOD));
        assertTrue(result.contains("$"));
    }

    /**
     * Verifies that an error message is printed for an invalid category.
     */
    @Test
    void testListSingleCategoryTotal_invalidCategory() {
        Map<String, Double> categoryTotals = reporter.getTotalByCategory();
        String result = reporter.listSingleCategoryTotal(categoryTotals, TestUtils.CATEGORY_INVALID);
        assertEquals(MessageDisplayer.CATEGORY_NOT_FOUND, result);
    }

    /**
     * Verifies that the reporter returns safe default values.
     * Ensures that:
     * <ul>
     *     <li>Total and average amounts are zero</li>
     *     <li>The highest category message displays the empty list message</li>
     *     <li>The string output for all categories is the empty list message</li>
     * </ul>
     */
    @Test
    void testEmptyReporterReturnsSafeDefaults() {
        AppServices.REGULAR_SERVICE.clearExpenses();
        reporter = AppServices.REGULAR_REPORTER;

        StringBuilder list = new StringBuilder();
        Map<String, Double> categoryTotals = reporter.getTotalByCategory();
        double noTotal = 0.00;
        list.append(String.format(MessageDisplayer.HIGHEST_CAT_FORMAT,
                MessageDisplayer.SUMMARY_HIGHEST_SPEND, MessageDisplayer.EMPTY_LIST_MESSAGE));
        list.append(String.format(MessageDisplayer.GRAND_TOTAL_FORMAT,
                MessageDisplayer.SUMMARY_GRAND_TOTAL, noTotal));
        assertEquals(0, reporter.getTotal(), TestUtils.DELTA);
        assertEquals(0, reporter.getAverage(), TestUtils.DELTA);
        assertEquals(MessageDisplayer.EMPTY_LIST_MESSAGE, reporter.getHighestCategory(categoryTotals));
        assertEquals(list.toString(), reporter.listAllCategoryTotals(categoryTotals));
    }

    /**
     * Verifies that that {@code getExpensesByCategory(null)} throws an exception.
     */
    @Test
    void testGetExpensesByCategory_nullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> reporter.getExpensesByCategory(null));
    }

    /**
     * Verifies that constructing an {@code ExpenseReporter} with a null manager
     * throws an {@code IllegalArgumentException}.
     */
    @Test
    void testConstructor_nullManagerThrows() {
        assertThrows(IllegalArgumentException.class, () -> new ExpenseReporter(null));
    }
}
