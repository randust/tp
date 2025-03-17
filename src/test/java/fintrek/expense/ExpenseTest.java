package fintrek.expense;

import fintrek.Expense;
import fintrek.misc.DisplayMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ExpenseTest {

    @ParameterizedTest
    @ValueSource(strings = {"mrt", "eat", "laptop for CS2113", "123"})
    public void testGetValidExpensesDescription(String description) {
        Expense expense = new Expense(description, 10.0, "uncategorized"); // Dummy amount & category
        assertEquals(description, expense.getDescription());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Studies", "Food", "baaaaaa", "Computer Science", "transport"})
    public void testGetValidExpensesCategory(String category) {
        Expense expense = new Expense("for testing", 10.0, category); // Dummy amount & category
        assertEquals(category, expense.getCategory());
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.50, 1.50, 2.50, 12.50, 250.00 })
    public void testGetValidExpensesAmount(double amount) {
        Expense expense = new Expense("for testing", amount, "uncategorized"); // Dummy amount & category
        assertEquals(amount, expense.getAmount());
    }

    /**
     * Test whether inputting zero or negative amounts for expenses
     * results in an exception being thrown
     */
    @Test
    public void testSetInvalidExpensesAmount_returnsError() {
        IllegalArgumentException firstException = assertThrows(
                IllegalArgumentException.class,
                () -> new Expense("gift from friend", -5.0, "gifts")
        );
        assertEquals(DisplayMessage.INVALID_AMOUNT, firstException.getMessage());


        IllegalArgumentException secondException = assertThrows(
                IllegalArgumentException.class,
                () -> new Expense("just nothing", 0.0, "uncategorized")
        );
        assertEquals(DisplayMessage.INVALID_AMOUNT, secondException.getMessage());

        IllegalArgumentException thirdException = assertThrows(
                IllegalArgumentException.class,
                () -> new Expense("monthly allowance", -500.0, "allowance")
        );
        assertEquals(DisplayMessage.INVALID_AMOUNT, thirdException.getMessage());
    }

    /**
     * Test whether the toString() method for the Expense class
     * effectively converts it to a string format of form
     * "{description} | ${amount} | {category}
     */
    @Test
    public void testExpensesToStringConversion() {
        assertEquals("eat | $10.00 | food" ,new Expense("eat", 10.0, "food").toString());
        assertEquals("mrt | $2.30 | transport", new Expense("mrt", 2.30, "transport").toString());
        assertEquals("dinner | $15.90 | food",  new Expense("dinner", 15.90, "food").toString());
    }
}
