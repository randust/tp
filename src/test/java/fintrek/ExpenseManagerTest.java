package fintrek;

import fintrek.expense.core.Expense;
import fintrek.expense.ExpenseManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


import java.time.LocalDate;
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
    private void insertConstantExpenses() {
        List<Expense> expenses = List.of(
            new Expense("eat", 10.0, "food", LocalDate.now()),
            new Expense("mrt", 2.30, "transport", LocalDate.now()),
            new Expense("dinner", 15.90, "food", LocalDate.now())
        );
        expenses.forEach(ExpenseManager::addExpense);
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
        assertEquals(0, ExpenseManager.getAverageExpenses());
    }

    /*
    Testing the functions with filled ArrayList
    * */
    @Test
    public void getLength_filledArrayList_success() {
        insertConstantExpenses();
        assertEquals(3, ExpenseManager.getLength());
    }

    @Test
    public void getAverageExpenses_filledArrayList_success() {
        insertConstantExpenses();
        assertEquals(9.40, ExpenseManager.getAverageExpenses());
    }

    @Test
    public void popExpense_filledArrayList_success() {
        insertConstantExpenses();
        ExpenseManager.popExpense(1);
        ExpenseManager.popExpense(1);
        assertEquals(1, ExpenseManager.getLength());
    }
}
