//@@author Charly2312
package fintrek.expense.core;

import fintrek.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RecurringExpenseManagerTest {
    private RecurringExpenseManager manager;

    @BeforeEach
    void setUp() {
        manager = RecurringExpenseManager.getInstance();
        manager.clear();
    }

    @Test
    void getLength_emptyList_returnsZero() {
        assertEquals(0, manager.getLength());
    }

    @Test
    void addRecurringExpenses_validExpenses_listSizeCorrect() {
        TestUtils.addConstantRecurringExpenses();
        assertEquals(TestUtils.EXPECTED_TEST_EXPENSE_COUNT, manager.getLength());
    }

    @Test
    void removeRecurringExpense_correctIndex_success() {
        TestUtils.addConstantRecurringExpenses();
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

    @Test
    void getAll_returnsDefensiveCopy() {
        TestUtils.addConstantRecurringExpenses();
        var copy = manager.getAll();
        copy.clear();
        assertEquals(TestUtils.EXPECTED_TEST_EXPENSE_COUNT, manager.getLength());
    }
}
