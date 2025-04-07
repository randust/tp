//@@author Charly2312
package fintrek.expense.core;

import fintrek.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This test class will test out all the plausible scenarios and assert if it behaves as expected or not.
 * The tests mainly focus on the functionalities of the class
 */
class RecurringExpenseManagerTest {
    private RecurringExpenseManager manager;

    /**
     * Before each test, a manager is instantiated from RecurringExpenseManager
     * It is then used to clear the recurring expense list before each test.
     */
    @BeforeEach
    void setUp() {
        manager = RecurringExpenseManager.getInstance();
        manager.clear();
    }

    /**
     * This test case checks if the function getLength() returns zero
     *              for an empty recurring expense list
     */
    @Test
    void getLength_emptyList_returnsZero() {
        assertEquals(0, manager.getLength());
    }

    /**
     * This test case checks if the function getLength() returns the correct
     *              size upon addition of constant recurring expenses.
     */
    @Test
    void addRecurringExpenses_validExpenses_listSizeCorrect() {
        TestUtils.addConstantRecurringExpenses();
        assertEquals(TestUtils.EXPECTED_TEST_EXPENSE_COUNT, manager.getLength());
    }

    /**
     * This test case checks if the function getLength() returns the correct
     *              size upon removal of one expense from the recurring expenses.
     */
    @Test
    void removeRecurringExpense_correctIndex_success() {
        TestUtils.addConstantRecurringExpenses();
        Expense removed = manager.remove(0);
        assertEquals(TestUtils.FIRST_TEST_DESC, removed.getDescription());
        assertEquals(TestUtils.EXPECTED_TEST_EXPENSE_COUNT - 1, manager.getLength());
    }

    /**
     * This test case checks if the function getDescription() returns the correct
     *              description upon insertion of a new recurring expense
     *              into the constant recurring expenses.
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

    /**
     * This test case checks if the function getLength() exposes the internal data structure.
     * This should return a new list without messing up the internal state.
     */
    @Test
    void getAll_returnsDefensiveCopy() {
        TestUtils.addConstantRecurringExpenses();
        var copy = manager.getAll();
        copy.clear();
        assertEquals(TestUtils.EXPECTED_TEST_EXPENSE_COUNT, manager.getLength());
    }
}
