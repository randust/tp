package fintrek;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static fintrek.ExpenseManager.*;
import static java.lang.Double.NaN;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

//unitBeingTested_descriptionOfTestInputs_expectedOutcome
class ExpenseManagerTest {
    //clearing the ArrayList before each test case
    @BeforeEach
    void setUp() {
        // Clear all existing expenses before each test to ensure independence
        while (ExpenseManager.getLength() > 0) {
            ExpenseManager.popExpense(0);
        }
    }

    /*
    add 3 constant expenses to be used for relevant test cases
    **/
    private void constantExpenses() {
        List<Expense> expenses = List.of(
            new Expense("eat", 10.0, "food"),
            new Expense("mrt", 2.30, "transport"),
            new Expense("dinner", 15.90, "food")
        );
        expenses.forEach(ExpenseManager::addExpense);
    }

    @Test
    public void sampleTest() {
        assertTrue(true);
    }

    /*
    Testing the functions with empty ArrayList
    * */
    @Test
    public void getTotalExpenses_emptyArrayList_success() {
        assertEquals(0.0, ExpenseManager.getTotalExpenses());
    }

    @Test
    public void getLength_emptyArrayList_success() {
        assertEquals(0, ExpenseManager.getLength());
    }

    @Test
    public void getAverageExpenses_emptyArrayList_success() {
        assertEquals(NaN, ExpenseManager.getAverageExpenses());
    }

    /*
    Testing the functions with filled ArrayList
    * */
    @Test
    public void getLength_filledArrayList_success() {
        constantExpenses();
        assertEquals(3, ExpenseManager.getLength());
    }

    @Test
    public void getAverageExpenses_filledArrayList_success() {
        constantExpenses();
        assertEquals(9.40, ExpenseManager.getAverageExpenses());
    }

    @Test
    public void popExpense_filledArrayList_success() {
        constantExpenses();
        popExpense(1);
        popExpense(1);
        assertEquals(1, ExpenseManager.getLength());
    }
}
