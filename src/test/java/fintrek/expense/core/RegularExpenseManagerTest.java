//@@author Charly2312
package fintrek.expense.core;

import fintrek.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
class RegularExpenseManagerTest {
    private RegularExpenseManager manager;

    @BeforeEach
    void setUp() {
        manager = RegularExpenseManager.getInstance();
        manager.clear();
    }

    @Test
    void getLength_emptyList_returnsZero() {
        assertEquals(0, manager.getLength());
    }

    @Test
    void getTotalAmount_afterAddingConstantExpenses_correctSum() {
        TestUtils.addConstantExpenses();
        double actual = manager.getAll().stream().mapToDouble(Expense::getAmount).sum();
        assertEquals(TestUtils.TOTAL_TEST_EXPENSE_SUM, actual);
    }

    @Test
    void addExpense_validExpense_listIncreases() {
        TestUtils.addConstantExpenses();
        assertEquals(TestUtils.EXPECTED_TEST_EXPENSE_COUNT, manager.getLength());
    }

    @Test
    void removeExpense_removesCorrectly() {
        TestUtils.addConstantExpenses();
        Expense removed = manager.remove(0);
        assertEquals(TestUtils.FIRST_TEST_DESC, removed.getDescription());
        assertEquals(TestUtils.EXPECTED_TEST_EXPENSE_COUNT - 1, manager.getLength());
    }

    @Test
    void insertAt_validIndex_success() {
        Expense inserted = new Expense(
                TestUtils.INSERTED_DESC,
                TestUtils.INSERTED_AMOUNT,
                TestUtils.INSERTED_CATEGORY,
                java.time.LocalDate.now()
        );
        manager.insertAt(0, inserted);
        assertEquals(TestUtils.INSERTED_DESC, manager.get(0).getDescription());
    }
}
