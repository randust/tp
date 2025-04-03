package fintrek.expense.core;

import fintrek.command.add.AddRecurringCommand;
import fintrek.command.registry.CommandResult;
import fintrek.misc.MessageDisplayer;
import fintrek.util.ExpenseManager;
import fintrek.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ExpenseTest {
    @BeforeEach
    public void setUp() {
        ExpenseManager.clearRecurringExpenses();
        ExpenseManager.clearExpenses();
    }

    @ParameterizedTest
    @ValueSource(strings = {"mrt", "eat", "laptop for CS2113", "123"})
    public void testGetValidExpensesDescription(String description) {
        Expense expense = new Expense(description, 10.0,
                "uncategorized", LocalDate.now()); // Dummy amount & category
        assertEquals(description, expense.getDescription());
    }

    @ParameterizedTest
    @ValueSource(strings = {"Studies", "Food", "baaaaaa", "Computer Science", "transport"})
    public void testGetValidExpensesCategory(String category) {
        Expense expense = new Expense("for testing", 10.0,
                category, LocalDate.now()); // Dummy amount & category
        assertEquals(category.toUpperCase(), expense.getCategory());
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.50, 1.50, 2.50, 12.50, 250.00 })
    public void testGetValidExpensesAmount(double amount) {
        Expense expense = new Expense("for testing", amount,
                "uncategorized", LocalDate.now()); // Dummy amount & category
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
     * "{description} | ${amount} | {category} | {date}
     * where the date format is "dd-MM-yyyy"
     */
    @Test
    public void testExpensesToStringConversion() {
        String dateToday = "02-04-2025";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = LocalDate.parse(dateToday, formatter);
        assertEquals("eat | $10.00 | FOOD | " + dateToday,
                new Expense("eat", 10.0, "food", date).toString());
        assertEquals("mrt | $2.30 | TRANSPORT | " + dateToday,
                new Expense("mrt", 2.30, "transport", date).toString());
        assertEquals("dinner | $15.90 | FOOD | " + dateToday,
                new Expense("dinner", 15.90, "food", date).toString());
    }

    /**
     * did a test when a recurring expense's date does not match current date
     * hence, it will not be added to the general list
     */
    @Test
    public void checkRecurringExpenseTest_existingRecurringNonMatchingDate_success() {
        String oldDate = "01-01-2025";
        AddRecurringCommand addCommand = new AddRecurringCommand(true);
        String input = "Spotify $9.99 /c entertainment " + oldDate;
        CommandResult result = addCommand.execute(input);
        TestUtils.assertCommandSuccess(result, input);

        ExpenseManager.checkRecurringExpense();

        TestUtils.assertCorrectListSize(0, input);
    }

    /**
     * did a test when a recurring expense's date matches current date
     * hence, it will be added to the general list
     */
    @Test
    public void checkRecurringExpenseTest_existingRecurringMatchingDate_success() {
        String oldDate = "01-01-2025";
        AddRecurringCommand addCommand = new AddRecurringCommand(true);
        String input = "Spotify $9.99 /c entertainment " + oldDate;
        LocalDate dateToday = LocalDate.now();
        CommandResult result = addCommand.execute(input);
        ExpenseManager.getRecurringExpense(0).updateDate(dateToday);
        TestUtils.assertCommandSuccess(result, input);

        ExpenseManager.checkRecurringExpense();

        TestUtils.assertCorrectListSize(1, input);
    }
}
