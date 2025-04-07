//@@author Charly2312
package fintrek.expense.core;

import fintrek.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
class RegularExpenseManagerTest {
    private RegularExpenseManager manager;

    /**
     * Clears all existing expenses in the RegularExpenseManager before each test.
     */
    @BeforeEach
    void setUp() {
        manager = RegularExpenseManager.getInstance();
        manager.clear();
    }

    /**
     * Verifies that getting the size of an empty list returns a size of 0.
     */
    @Test
    void getLength_emptyList_returnsZero() {
        assertEquals(0, manager.getLength());
    }

    /**
     * Verifies that the total amount of expenses after adding a constant amount of expenses
     * is correct.
     */
    @Test
    void getTotalAmount_afterAddingConstantExpenses_correctSum() {
        TestUtils.addConstantExpenses();
        double actual = manager.getAll().stream().mapToDouble(Expense::getAmount).sum();
        assertEquals(TestUtils.TOTAL_TEST_EXPENSE_SUM, actual);
    }

    /**
     * Verifies that adding expenses indeed increases the size of the list of expenses.
     */
    @Test
    void addExpense_validExpense_listIncreases() {
        TestUtils.addConstantExpenses();
        assertEquals(TestUtils.EXPECTED_TEST_EXPENSE_COUNT, manager.getLength());
    }

    /**
     * Verifies that removing an expense at a certain index works.
     */
    @Test
    void removeExpense_removesCorrectly() {
        TestUtils.addConstantExpenses();
        Expense removed = manager.remove(0);
        assertEquals(TestUtils.FIRST_TEST_DESC, removed.getDescription());
        assertEquals(TestUtils.EXPECTED_TEST_EXPENSE_COUNT - 1, manager.getLength());
    }

    /**
     * Verifies that inserting an expense at a certain valid index of the list works.
     */
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
