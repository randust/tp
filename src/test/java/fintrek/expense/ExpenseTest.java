package fintrek.expense;

import fintrek.expense.core.Expense;
import fintrek.misc.MessageDisplayer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ExpenseTest {

    @ParameterizedTest
    @ValueSource(strings = {"mrt", "eat", "laptop for CS2113", "123"})
    public void testGetValidExpensesDescription(String description) {
        Expense expense = new Expense(description, 10.0, "uncategorized", LocalDate.now()); // Dummy amount & category
        assertEquals(description, expense.getDescription());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Studies", "Food", "baaaaaa", "Computer Science", "transport"})
    public void testGetValidExpensesCategory(String category) {
        Expense expense = new Expense("for testing", 10.0, category, LocalDate.now()); // Dummy amount & category
        assertEquals(category.toUpperCase(), expense.getCategory());
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.50, 1.50, 2.50, 12.50, 250.00 })
    public void testGetValidExpensesAmount(double amount) {
        Expense expense = new Expense("for testing", amount, "uncategorized", LocalDate.now()); // Dummy amount & category
        assertEquals(amount, expense.getAmount());
    }

    /**
     * Test whether inputting zero or negative amounts for expenses
     * results in an exception being thrown
     */
    @ParameterizedTest
    @CsvSource({
        "'gift from friend', -5.0, 'gifts'",
        "'just nothing', 0.0, 'uncategorized'",
        "'monthly allowance', -750.0, 'allowance'",
        "'salary', -1250, 'salary'"
    })
    public void testSetInvalidExpensesAmount_returnsError(String description, double amount, String category) {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> new Expense(description, amount, category, LocalDate.now())
        );
        assertEquals(MessageDisplayer.INVALID_AMOUNT, exception.getMessage());
    }

    /**
     * Test whether the toString() method for the Expense class
     * effectively converts it to a string format of form
     * "{description} | ${amount} | {category}
     */
    @Test
    public void testExpensesToStringConversion() {
        LocalDate dateToday = LocalDate.now();
        assertEquals("eat | $10.00 | FOOD | " + dateToday ,new Expense("eat", 10.0, "food", LocalDate.now()).toString());
        assertEquals("mrt | $2.30 | TRANSPORT | " + dateToday, new Expense("mrt", 2.30, "transport", LocalDate.now()).toString());
        assertEquals("dinner | $15.90 | FOOD | " + dateToday,  new Expense("dinner", 15.90, "food", LocalDate.now()).toString());
    }
}
