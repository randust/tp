package fintrek;

import fintrek.command.ExecutionResult;
import fintrek.parser.ParseResult;
import fintrek.parser.Parser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static fintrek.ExpenseManager.*;
import static java.lang.Double.NaN;
import static org.junit.jupiter.api.Assertions.*;
import fintrek.misc.DisplayMessage;

import java.util.ArrayList;

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
        Expense expense1 = new Expense("eat", 10.0, "food");
        Expense expense2 = new Expense("mrt", 2.30, "transport");
        Expense expense3 = new Expense("dinner", 15.90, "food");
        addExpense(expense1);
        addExpense(expense2);
        addExpense(expense3);
        assertEquals(3, ExpenseManager.getLength());
    }

    @Test
    public void getAverageExpenses_filledArrayList_success() {
        Expense expense1 = new Expense("eat", 10.0, "food");
        Expense expense2 = new Expense("mrt", 2.30, "transport");
        Expense expense3 = new Expense("dinner", 15.90, "food");
        ExpenseManager.addExpense(expense1);
        ExpenseManager.addExpense(expense2);
        ExpenseManager.addExpense(expense3);
        assertEquals(9.40, ExpenseManager.getAverageExpenses());
    }

    @Test
    public void popExpense_filledArrayList_success() {
        Expense expense1 = new Expense("eat", 10.0, "food");
        Expense expense2 = new Expense("mrt", 2.30, "transport");
        Expense expense3 = new Expense("dinner", 15.90, "food");
        ExpenseManager.addExpense(expense1);
        ExpenseManager.addExpense(expense2);
        ExpenseManager.addExpense(expense3);
        popExpense(1);
        popExpense(1);
        assertEquals(1, ExpenseManager.getLength());
    }
}
