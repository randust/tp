package fintrek.expense.service;

import fintrek.expense.core.RegularExpenseManager;
import fintrek.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ExpenseReporterTest {
    private ExpenseReporter reporter;

    @BeforeEach
    void setUp() {
        RegularExpenseManager.getInstance().clear();
        TestUtils.addConstantExpenses();
        reporter = AppServices.REGULAR_REPORTER;
    }

    @Test
    void getTotal_correctSumFromConstants() {
        assertEquals(TestUtils.TOTAL_TEST_EXPENSE_SUM, reporter.getTotal());
    }

    @Test
    void getTotalByCategory_returnsCorrectCategorySums() {
        Map<String, Double> totals = reporter.getTotalByCategory();

        assertEquals(TestUtils.FOOD_TOTAL, totals.get("FOOD"));
        assertEquals(TestUtils.TRANSPORT_TOTAL, totals.get("TRANSPORT"));
        assertEquals(TestUtils.ENTERTAINMENT_TOTAL, totals.get("ENTERTAINMENT"));
    }

    @Test
    void getHighestCategory_returnsCorrectCategoryAndAmount() {
        Map<String, Double> categoryTotals = reporter.getTotalByCategory();
        String result = reporter.getHighestCategory(categoryTotals);
        boolean correct = result.contains(TestUtils.HIGHEST_SPEND_CATEGORY)
                && result.contains(String.valueOf(TestUtils.HIGHEST_SPEND_AMOUNT));
        assertEquals(true, correct);
    }

    @Test
    void getExpensesByCategory_food_returnsCorrectSize() {
        assertEquals(TestUtils.FOOD_EXPENSE_COUNT, reporter.getExpensesByCategory("FOOD").size());
    }

    @Test
    void getAverage_returnsCorrectAverage() {
        assertEquals(TestUtils.EXPECTED_AVERAGE, reporter.getAverage());
    }
}
