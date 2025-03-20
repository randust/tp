package fintrek.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import fintrek.Expense;
import fintrek.ExpenseManager;
import fintrek.misc.MessageDisplayer;

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
        ExpenseManager.addExpense(new Expense("ice cream", 2.50, "food"));
        ExpenseManager.addExpense(new Expense("train", 1.66, "transport"));
        ExpenseManager.addExpense(new Expense("concert", 256, "entertainment"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"$1 /c transport", "", "$2.5"})
    public void testAddCommandMissingDescription(String input) {
        ExecutionResult result = Command.ADD.execute(input);
        assertFalse(result.isSuccess());
        assertEquals(MessageDisplayer.MISSING_DESC_MESSAGE, result.message());
    }

    @ParameterizedTest
    @ValueSource(strings = {"invalid", "1.2.3", "-1", "2.", ""})
    public void testAddCommandInvalidAmount(String input) {
        ExecutionResult result = Command.ADD.execute("bus $" + input + "/c transport");
        assertFalse(result.isSuccess());
        assertEquals(MessageDisplayer.INVALID_AMT_MESSAGE, result.message());
    }

    @ParameterizedTest
    @ValueSource(strings = {"20", "0.99", "45.67", "1.0"})
    public void testAddCommandValidAmount(String input) {
        int initialSize = ExpenseManager.getLength();
        String addedExpense = "bus $" + input + "/c transport";
        ExecutionResult result = Command.ADD.execute(addedExpense);

        assertTrue(result.isSuccess());
        assertEquals(initialSize + 1, ExpenseManager.getLength());
        assertEquals(Double.parseDouble(input), ExpenseManager.getExpense(initialSize).getAmount());
    }

    @ParameterizedTest
    @ValueSource(strings = {"bus $1", "bus$1", "bus $ 1"})
    public void testAddCommandTwoValidInputs(String input) {
        int initialSize = ExpenseManager.getLength();
        ExecutionResult result = Command.ADD.execute(input);

        assertTrue(result.isSuccess());
        assertEquals(initialSize + 1, ExpenseManager.getLength());
        assertEquals("bus", ExpenseManager.getExpense(initialSize).getDescription());
        assertEquals(1, ExpenseManager.getExpense(initialSize).getAmount());
        assertEquals("Uncategorized", ExpenseManager.getExpense(initialSize).getCategory());
    }

    @ParameterizedTest
    @ValueSource(strings = {"bus $1 /c transport", "bus$1/ctransport", "bus $ 1 /c transport"})
    public void testAddCommandThreeValidInputs(String input) {
        int initialSize = ExpenseManager.getLength();
        ExecutionResult result = Command.ADD.execute(input);

        assertTrue(result.isSuccess());
        assertEquals(initialSize + 1, ExpenseManager.getLength());
        assertEquals("bus", ExpenseManager.getExpense(initialSize).getDescription());
        assertEquals(1, ExpenseManager.getExpense(initialSize).getAmount());
        assertEquals("transport", ExpenseManager.getExpense(initialSize).getCategory());
    }

    @Test
    public void testDeleteCommandInvalidInput() {
        ExecutionResult result = Command.DELETE.execute("invalid");
        assertFalse(result.isSuccess());
        assertEquals(MessageDisplayer.INVALID_NUM_MESSAGE, result.message());
    }

    @ParameterizedTest
    @ValueSource(strings = {"-1", "0", "999"}) // Assuming ExpenseManager has <999 items
    public void testDeleteCommandOutOfBounds(String input) {
        ExecutionResult result = Command.DELETE.execute(input);
        assertFalse(result.isSuccess());
        assertEquals(MessageDisplayer.INVALID_NUM_MESSAGE, result.message());
    }

    @Test
    public void testDeleteCommandValidInput() {
        int expectedSize = ExpenseManager.getLength() - 1;
        ExecutionResult result = Command.DELETE.execute("1");
        
        assertTrue(result.isSuccess());
        assertEquals(String.format(MessageDisplayer.DELETE_SUCCESS_MESSAGE_TEMPLATE, expectedSize), result.message());
        assertEquals(expectedSize, ExpenseManager.getLength());
    }

    @Test
    public void testTotalCommandEmptyList() {
        ExpenseManager.clearExpenses();
        ExecutionResult result = Command.TOTAL.execute("");
        assertTrue(result.isSuccess());
        assertEquals(String.format(MessageDisplayer.TOTAL_SUCCESS_MESSAGE_TEMPLATE, 0.0), result.message());
    }

    @Test
    public void testTotalCommandFilledList() {
        ExecutionResult result = Command.TOTAL.execute("");
        assertTrue(result.isSuccess());
        double expectedTotal = ExpenseManager.getTotalExpenses();
        assertEquals(String.format(MessageDisplayer.TOTAL_SUCCESS_MESSAGE_TEMPLATE, expectedTotal), result.message());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    public void testHelpCommandGeneral(String input) {
        ExecutionResult result = Command.HELP.execute(input);
        assertTrue(result.isSuccess());
        assertEquals(MessageDisplayer.getAllFeaturesMessage(), result.message());
    }

    @ParameterizedTest
    @ValueSource(strings = {"add", "ADD", " add ", "adddd", "/add"})
    public void testHelpCommandAdd(String input) {
        ExecutionResult result = Command.HELP.execute(input);
        assertTrue(result.isSuccess());
        assertEquals(MessageDisplayer.ADD_FORMAT_MESSAGE, result.message());
    }

    @ParameterizedTest
    @ValueSource(strings = {"delete", "DELETE", " delete ", "deleteee", "/delete"})
    public void testHelpCommandDelete(String input) {
        ExecutionResult result = Command.HELP.execute(input);
        assertTrue(result.isSuccess());
        assertEquals(MessageDisplayer.DELETE_FORMAT_MESSAGE, result.message());
    }

    @ParameterizedTest
    @ValueSource(strings = {"total", "TOTAL", " total ", "totalll", "/total"})
    public void testHelpCommandTotal(String input) {
        ExecutionResult result = Command.HELP.execute(input);
        assertTrue(result.isSuccess());
        assertEquals(MessageDisplayer.TOTAL_FORMAT_MESSAGE, result.message());
    }

    @ParameterizedTest
    @ValueSource(strings = {"average", "AVERAGE", " average ", "averageee", "/average"})
    public void testHelpCommandAverage(String input) {
        ExecutionResult result = Command.HELP.execute(input);
        assertTrue(result.isSuccess());
        assertEquals(MessageDisplayer.AVERAGE_FORMAT_MESSAGE, result.message());
    }

    @ParameterizedTest
    @ValueSource(strings = {"summary", "SUMMARY", " summary ", "summaryyy", "/summary"})
    public void testHelpCommandSummary(String input) {
        ExecutionResult result = Command.HELP.execute(input);
        assertTrue(result.isSuccess());
        assertEquals(MessageDisplayer.SUMMARY_FORMAT_MESSAGE, result.message());
    }

    @ParameterizedTest
    @ValueSource(strings = {"hello", "delet", "help"})
    public void testHelpCommandUnknownTopic(String input) {
        ExecutionResult result = Command.HELP.execute(input);
        assertTrue(result.isSuccess());
        assertEquals(MessageDisplayer.HELP_UNKNOWN_TOPIC, result.message());
    }
}
