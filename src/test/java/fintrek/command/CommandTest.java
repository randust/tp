package fintrek.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import fintrek.Expense;
import fintrek.ExpenseManager;
import fintrek.misc.DisplayMessage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class CommandTest {
    @BeforeEach
    public void setUp() {
        ExpenseManager.clearExpenses();
        ExpenseManager.addExpense(new Expense("lunch", 5.50, "food"));
        ExpenseManager.addExpense(new Expense("taxi", 11.20, "transport"));
        ExpenseManager.addExpense(new Expense("dinner", 9.80, "food"));
    }

    @Test
    public void testDeleteCommandInvalidInput() {
        ExecutionResult result = Command.DELETE.execute("invalid");
        assertFalse(result.isSuccess());
        assertEquals(DisplayMessage.INVALID_NUM_MESSAGE, result.message());
    }

    @Test
    public void testDeleteCommandOutOfBounds() {
        ExecutionResult result = Command.DELETE.execute("999");  // Assuming ExpenseManager has fewer items
        assertFalse(result.isSuccess());
        assertEquals(DisplayMessage.INVALID_NUM_MESSAGE, result.message());
    }

    @Test
    public void testDeleteCommandValidInput() {
        //TODO: implement after having ADD
    }

    @Test
    public void testTotalCommandEmptyList() {
        ExpenseManager.clearExpenses();
        ExecutionResult result = Command.TOTAL.execute("");
        assertTrue(result.isSuccess());
        assertEquals(String.format(DisplayMessage.TOTAL_SUCCESS_MESSAGE_TEMPLATE, 0.0), result.message());
    }

    @Test
    public void testTotalCommandFilledList() {
        ExecutionResult result = Command.TOTAL.execute("");
        assertTrue(result.isSuccess());
        double expectedTotal = ExpenseManager.getTotalExpenses();
        assertEquals(String.format(DisplayMessage.TOTAL_SUCCESS_MESSAGE_TEMPLATE, expectedTotal), result.message());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    public void testHelpCommandGeneral(String input) {
        ExecutionResult result = Command.HELP.execute(input);
        assertTrue(result.isSuccess());
        assertEquals(DisplayMessage.getAllFeaturesMessage(), result.message());
    }

    @ParameterizedTest
    @ValueSource(strings = {"add", "ADD", " add ", "adddd", "/add"})
    public void testHelpCommandAdd(String input) {
        ExecutionResult result = Command.HELP.execute(input);
        assertTrue(result.isSuccess());
        assertEquals(DisplayMessage.ADD_FORMAT_MESSAGE, result.message());
    }

    @ParameterizedTest
    @ValueSource(strings = {"delete", "DELETE", " delete ", "deleteee", "/delete"})
    public void testHelpCommandDelete(String input) {
        ExecutionResult result = Command.HELP.execute(input);
        assertTrue(result.isSuccess());
        assertEquals(DisplayMessage.DELETE_FORMAT_MESSAGE, result.message());
    }

    @ParameterizedTest
    @ValueSource(strings = {"total", "TOTAL", " total ", "totalll", "/total"})
    public void testHelpCommandTotal(String input) {
        ExecutionResult result = Command.HELP.execute(input);
        assertTrue(result.isSuccess());
        assertEquals(DisplayMessage.TOTAL_FORMAT_MESSAGE, result.message());
    }

    @ParameterizedTest
    @ValueSource(strings = {"average", "AVERAGE", " average ", "averageee", "/average"})
    public void testHelpCommandAverage(String input) {
        ExecutionResult result = Command.HELP.execute(input);
        assertTrue(result.isSuccess());
        assertEquals(DisplayMessage.AVERAGE_FORMAT_MESSAGE, result.message());
    }

    @ParameterizedTest
    @ValueSource(strings = {"summary", "SUMMARY", " summary ", "summaryyy", "/summary"})
    public void testHelpCommandSummary(String input) {
        ExecutionResult result = Command.HELP.execute(input);
        assertTrue(result.isSuccess());
        assertEquals(DisplayMessage.SUMMARY_FORMAT_MESSAGE, result.message());
    }

    @ParameterizedTest
    @ValueSource(strings = {"hello", "delet", "help"})
    public void testHelpCommandUnknownTopic(String input) {
        ExecutionResult result = Command.HELP.execute(input);
        assertTrue(result.isSuccess());
        assertEquals(DisplayMessage.HELP_UNKNOWN_TOPIC, result.message());
    }
}
