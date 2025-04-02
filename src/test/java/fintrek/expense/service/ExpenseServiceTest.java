package fintrek.expense.service;

import fintrek.expense.core.RegularExpenseManager;
import fintrek.expense.core.RecurringExpenseManager;
import fintrek.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import fintrek.AppServices;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ExpenseServiceTest {
    private ExpenseService regularService;
    private ExpenseService recurringService;

    @BeforeEach
    void setUp() {
        RegularExpenseManager.getInstance().clear();
        RecurringExpenseManager.getInstance().clear();

        regularService = AppServices.REGULAR_SERVICE;
        recurringService = AppServices.RECURRING_SERVICE;
    }

    @Test
    void count_regular_addsConstant() {
        TestUtils.addConstantExpenses();
        assertEquals(TestUtils.EXPECTED_TEST_EXPENSE_COUNT, regularService.countExpenses());
    }

    @Test
    void count_recurring_addsConstant() {
        TestUtils.addConstantRecurringExpenses();
        assertEquals(TestUtils.EXPECTED_TEST_EXPENSE_COUNT, recurringService.countExpenses());
    }

    @Test
    void remove_regular_works() {
        TestUtils.addConstantExpenses();
        regularService.removeExpense(0);
        assertEquals(TestUtils.EXPECTED_TEST_EXPENSE_COUNT - 1, regularService.countExpenses());
    }

    @Test
    void remove_recurring_works() {
        TestUtils.addConstantRecurringExpenses();
        recurringService.removeExpense(0);
        assertEquals(TestUtils.EXPECTED_TEST_EXPENSE_COUNT - 1, recurringService.countExpenses());
    }

    @Test
    void get_regular_descCorrect() {
        TestUtils.addConstantExpenses();
        assertEquals(TestUtils.FIRST_TEST_DESC, regularService.getExpense(0).getDescription());
    }

    @Test
    void get_recurring_amountCorrect() {
        TestUtils.addConstantRecurringExpenses();
        assertEquals(11.20, recurringService.getExpense(1).getAmount());
    }
}
