package fintrek.expense.service;

import fintrek.expense.core.RegularExpenseManager;
import fintrek.expense.core.RecurringExpenseManager;
import fintrek.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for {@code ExpenseService} class.
 * Verifies correct behaviour of both regular and recurring instances of {@code ExpenseService}.
 */
class ExpenseServiceTest {
    private ExpenseService regularService;
    private ExpenseService recurringService;

    /**
     * Clears both regular and recurring expense managers before each test.
     * Initializes the service instances for use in the tests.
     */
    @BeforeEach
    void setUp() {
        RegularExpenseManager.getInstance().clear();
        RecurringExpenseManager.getInstance().clear();

        regularService = AppServices.REGULAR_SERVICE;
        recurringService = AppServices.RECURRING_SERVICE;
    }

    /**
     * Verifies that adding a list of predefined regular expenses results in the
     * expected number of regular expenses counted.
     */
    @Test
    void count_regular_addsConstant() {
        TestUtils.addConstantExpenses();
        assertEquals(TestUtils.EXPECTED_TEST_EXPENSE_COUNT, regularService.countExpenses());
    }

    /**
     * Verifies that adding a list of predefined recurring expenses results in the
     * expected number of recurring expenses counted.
     */
    @Test
    void count_recurring_addsConstant() {
        TestUtils.addConstantRecurringExpenses();
        assertEquals(TestUtils.EXPECTED_TEST_EXPENSE_COUNT, recurringService.countExpenses());
    }

    /**
     * Verifies that removing a regular expense will reduce the number of
     * regular expenses counted by one.
     */
    @Test
    void remove_regular_works() {
        TestUtils.addConstantExpenses();
        regularService.removeExpense(0);
        assertEquals(TestUtils.EXPECTED_TEST_EXPENSE_COUNT - 1, regularService.countExpenses());
    }

    /**
     * Verifies that removing a recurring expense will reduce the number of
     * recurring expenses counted by one.
     */
    @Test
    void remove_recurring_works() {
        TestUtils.addConstantRecurringExpenses();
        recurringService.removeExpense(0);
        assertEquals(TestUtils.EXPECTED_TEST_EXPENSE_COUNT - 1, recurringService.countExpenses());
    }

    /**
     * Verifies that the description of the first regular expense in the list fetched
     * matches the expected description of the first expense.
     */
    @Test
    void get_regular_descCorrect() {
        TestUtils.addConstantExpenses();
        assertEquals(TestUtils.FIRST_TEST_DESC, regularService.getExpense(0).getDescription());
    }

    /**
     * Verifies the amount of the second recurring expense in the list fetched
     * matches the expected amount of the second expense.
     */
    @Test
    void get_recurring_amountCorrect() {
        TestUtils.addConstantRecurringExpenses();
        assertEquals(11.20, recurringService.getExpense(1).getAmount());
    }
}
