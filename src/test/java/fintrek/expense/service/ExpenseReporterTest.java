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

public class ExpenseReporterTest {
    private ExpenseReporter reporter;

    @BeforeEach
    void setUp() {
        AppServices.REGULAR_SERVICE.clearExpenses();
        TestUtils.addConstantExpenses();
        reporter = AppServices.REGULAR_REPORTER;
    }

    @Test
    void testGetTotal() {
        assertEquals(TestUtils.TOTAL_TEST_EXPENSE_SUM, reporter.getTotal(), TestUtils.DELTA);
    }

    @Test
    void testGetTotalByCategory() {
        Map<String, Double> map = reporter.getTotalByCategory();
        assertEquals(TestUtils.FOOD_TOTAL, map.get(TestUtils.CATEGORY_FOOD), TestUtils.DELTA);
        assertEquals(TestUtils.TRANSPORT_TOTAL, map.get(TestUtils.CATEGORY_TRANSPORT), TestUtils.DELTA);
        assertEquals(TestUtils.ENTERTAINMENT_TOTAL, map.get(TestUtils.CATEGORY_ENTERTAINMENT), TestUtils.DELTA);
    }

    @Test
    void testGetHighestCategory() {
        String result = reporter.getHighestCategory();
        assertTrue(result.contains(TestUtils.HIGHEST_SPEND_CATEGORY));
        assertTrue(result.contains(String.format("%.2f", TestUtils.HIGHEST_SPEND_AMOUNT)));
    }

    @Test
    void testGetAverage() {
        assertEquals(TestUtils.EXPECTED_AVERAGE, reporter.getAverage(), TestUtils.DELTA);
    }

    @Test
    void testGetTotalByMonth() {
        LocalDate today = LocalDate.now();
        double expected = TestUtils.TOTAL_TEST_EXPENSE_SUM;
        double actual = reporter.getTotalByMonth(today.getYear(), today.getMonthValue());
        assertEquals(expected, actual, TestUtils.DELTA);
    }

    @Test
    void testListExpensesFormatNotEmpty() {
        String list = reporter.listExpenses();
        assertFalse(list.isEmpty());
        assertTrue(list.contains("$"));
    }

    @Test
    void testListAllCategoryTotals() {
        String result = reporter.listAllCategoryTotals();
        assertTrue(result.contains(TestUtils.CATEGORY_FOOD));
        assertTrue(result.contains(TestUtils.CATEGORY_TRANSPORT));
        assertTrue(result.contains(TestUtils.CATEGORY_ENTERTAINMENT));
        assertTrue(result.contains(MessageDisplayer.SUMMARY_GRAND_TOTAL));
    }

    @Test
    void testListSingleCategoryTotal_success() {
        String result = reporter.listSingleCategoryTotal(TestUtils.CATEGORY_FOOD);
        assertTrue(result.contains(TestUtils.CATEGORY_FOOD));
        assertTrue(result.contains("$"));
    }

    @Test
    void testListSingleCategoryTotal_invalidCategory() {
        String result = reporter.listSingleCategoryTotal(TestUtils.CATEGORY_INVALID);
        assertEquals(MessageDisplayer.CATEGORY_NOT_FOUND, result);
    }

    @Test
    void testEmptyReporterReturnsSafeDefaults() {
        AppServices.REGULAR_SERVICE.clearExpenses();
        reporter = AppServices.REGULAR_REPORTER;

        assertEquals(0, reporter.getTotal(), TestUtils.DELTA);
        assertEquals(0, reporter.getAverage(), TestUtils.DELTA);
        assertEquals(MessageDisplayer.EMPTY_LIST_MESSAGE, reporter.getHighestCategory());
        assertEquals(MessageDisplayer.EMPTY_LIST_MESSAGE, reporter.listAllCategoryTotals());
    }

    @Test
    void testGetExpensesByCategory_nullThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> reporter.getExpensesByCategory(null));
    }

    @Test
    void testConstructor_nullManagerThrows() {
        assertThrows(IllegalArgumentException.class, () -> new ExpenseReporter(null));
    }
}
