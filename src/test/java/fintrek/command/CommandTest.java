//TODO: Update Junit test for the new Command structure
package fintrek.command;

import fintrek.Expense;
import fintrek.ExpenseManager;

import java.util.List;

public class CommandTest {
    /**
     * Adds predefined expenses to ExpenseManager for consistent test scenarios.
     */
    public static void constantExpenses() {
        List<Expense> expenses = List.of(
                new Expense("lunch", 5.50, "food"),
                new Expense("lunch", 5.50, "food"),
                new Expense("taxi", 11.20, "transport"),
                new Expense("dinner", 9.80, "food"),
                new Expense("ice cream", 1.66, "food"),
                new Expense("concert", 256, "entertainment")
        );
        expenses.forEach(ExpenseManager::addExpense);
    }
//    @BeforeEach
//    public void setUp() {
//        ExpenseManager.clearExpenses();
//        ExpenseManager.addExpense(new Expense("lunch", 5.50, "food"));
//        ExpenseManager.addExpense(new Expense("taxi", 11.20, "transport"));
//        ExpenseManager.addExpense(new Expense("dinner", 9.80, "food"));
//        ExpenseManager.addExpense(new Expense("ice cream", 2.50, "food"));
//        ExpenseManager.addExpense(new Expense("train", 1.66, "transport"));
//        ExpenseManager.addExpense(new Expense("concert", 256, "entertainment"));
//    }
//
//    @ParameterizedTest
//    @ValueSource(strings = {"$1 /c transport", "", "$2.5"})
//    public void testAddCommandMissingDescription(String input) {
//        CommandResult result = Command.ADD.execute(input);
//        assertFalse(result.isSuccess());
//        assertEquals(MessageDisplayer.MISSING_DESC_MESSAGE, result.message());
//    }
//
//    @ParameterizedTest
//    @ValueSource(strings = {"invalid", "1.2.3", "-1", "2.", ""})
//    public void testAddCommandInvalidAmount(String input) {
//        CommandResult result = Command.ADD.execute("bus $" + input + "/c transport");
//        assertFalse(result.isSuccess());
//        assertEquals(MessageDisplayer.INVALID_AMT_MESSAGE, result.message());
//    }
//
//    @ParameterizedTest
//    @ValueSource(strings = {"20", "0.99", "45.67", "1.0"})
//    public void testAddCommandValidAmount(String input) {
//        int initialSize = ExpenseManager.getLength();
//        String addedExpense = "bus $" + input + "/c transport";
//        CommandResult result = Command.ADD.execute(addedExpense);
//
//        assertTrue(result.isSuccess());
//        assertEquals(initialSize + 1, ExpenseManager.getLength());
//        assertEquals(Double.parseDouble(input), ExpenseManager.getExpense(initialSize).getAmount());
//    }
//
//    @ParameterizedTest
//    @ValueSource(strings = {"bus $1", "bus$1", "bus $ 1"})
//    public void testAddCommandTwoValidInputs(String input) {
//        int initialSize = ExpenseManager.getLength();
//        CommandResult result = Command.ADD.execute(input);
//
//        assertTrue(result.isSuccess());
//        assertEquals(initialSize + 1, ExpenseManager.getLength());
//        assertEquals("bus", ExpenseManager.getExpense(initialSize).getDescription());
//        assertEquals(1, ExpenseManager.getExpense(initialSize).getAmount());
//        assertEquals("Uncategorized", ExpenseManager.getExpense(initialSize).getCategory());
//    }
//
//    @ParameterizedTest
//    @ValueSource(strings = {"bus $1 /c transport", "bus$1/ctransport", "bus $ 1 /c transport"})
//    public void testAddCommandThreeValidInputs(String input) {
//        int initialSize = ExpenseManager.getLength();
//        CommandResult result = Command.ADD.execute(input);
//
//        assertTrue(result.isSuccess());
//        assertEquals(initialSize + 1, ExpenseManager.getLength());
//        assertEquals("bus", ExpenseManager.getExpense(initialSize).getDescription());
//        assertEquals(1, ExpenseManager.getExpense(initialSize).getAmount());
//        assertEquals("transport", ExpenseManager.getExpense(initialSize).getCategory());
//    }
//
//    @Test
//    public void testDeleteCommandInvalidInput() {
//        CommandResult result = Command.DELETE.execute("invalid");
//        assertFalse(result.isSuccess());
//        assertEquals(MessageDisplayer.INVALID_IDX_MESSAGE, result.message());
//    }
//
//    @ParameterizedTest
//    @ValueSource(strings = {"-1", "0", "999"}) // Assuming ExpenseManager has <999 items
//    public void testDeleteCommandOutOfBounds(String input) {
//        CommandResult result = Command.DELETE.execute(input);
//        assertFalse(result.isSuccess());
//        assertEquals(MessageDisplayer.INVALID_IDX_MESSAGE, result.message());
//    }
//
//    @Test
//    public void testDeleteCommandValidInput() {
//        int expectedSize = ExpenseManager.getLength() - 1;
//        CommandResult result = Command.DELETE.execute("1");
//
//        assertTrue(result.isSuccess());
//        assertEquals(String.format(MessageDisplayer.DELETE_SUCCESS_MESSAGE_TEMPLATE,
//                expectedSize), result.message());
//        assertEquals(expectedSize, ExpenseManager.getLength());
//    }
}
