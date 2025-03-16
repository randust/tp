package fintrek.expense;
import java.util.ArrayList;
import java.util.List;

import fintrek.Expense;
import fintrek.ExpenseManager;
import fintrek.misc.DisplayMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ExpenseTest {

    @BeforeEach
    void setUp() {
        // Clear all existing expenses before each test to ensure independence
        ExpenseManager.clearExpenses();
    }

    @Test
    public void sampleTest() {
        assertTrue(true);
    }

    @ParameterizedTest
    @ValueSource(strings = {"mrt", "eat", "laptop for CS2113", "123"})
    public void testValidExpensesDescription(String description) {
        Expense expense = new Expense(description, 10.0, "uncategorized"); // Dummy amount & category
        assertEquals(description, expense.getDescription());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Studies", "Food", "baaaaaa", "Computer Science", "transport"})
    public void testValidExpensesCategory(String category) {
        Expense expense = new Expense("for testing", 10.0, category); // Dummy amount & category
        assertEquals(category, expense.getCategory());
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.00, 1.50, 2.50, 12.50, 250.00 })
    public void testValidExpensesAmount(double amount) {
        Expense expense = new Expense("for testing", amount, "uncategorized"); // Dummy amount & category
        assertEquals(amount, expense.getAmount());
    }

    @Test
    public void testInvalidExpensesAmount_returnsError() throws IllegalArgumentException {
        try {
            Expense expense = new Expense("for testing", -5.0, "uncategorized");
            fail();
        } catch (IllegalArgumentException e) {
            assertEquals(DisplayMessage.INVALID_AMOUNT, e.getMessage());
        }
    }

    @Test
    public void testExpensesToStringConversion() {
        assertEquals("eat | $10.00 | food" ,new Expense("eat", 10.0, "food").toString());
        assertEquals("mrt | $2.30 | transport", new Expense("mrt", 2.30, "transport").toString());
        assertEquals("dinner | $15.90 | food",  new Expense("dinner", 15.90, "food").toString());
    }
}
