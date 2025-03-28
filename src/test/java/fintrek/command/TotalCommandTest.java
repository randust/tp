package fintrek.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import fintrek.Expense;
import fintrek.ExpenseManager;
import fintrek.misc.MessageDisplayer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TotalCommandTest {
    @BeforeEach
    public void setUp() {
        ExpenseManager.clearExpenses();
    }
    @Test
    public void testTotalCommandEmptyList() {
        TotalCommand totalCommand = new TotalCommand();
        CommandResult result = totalCommand.execute("");

        assertTrue(result.isSuccess());
        assertEquals(String.format(MessageDisplayer.TOTAL_SUCCESS_MESSAGE_TEMPLATE,
                0.0), result.message());
    }

    @Test
    public void testTotalCommandFilledList() {
        ExpenseManager.addExpense(new Expense("lunch", 5.50, "food"));
        ExpenseManager.addExpense(new Expense("taxi", 11.20, "transport"));
        ExpenseManager.addExpense(new Expense("dinner", 9.80, "food"));
        ExpenseManager.addExpense(new Expense("ice cream", 2.50, "food"));
        ExpenseManager.addExpense(new Expense("train", 1.66, "transport"));
        ExpenseManager.addExpense(new Expense("concert", 256, "entertainment"));

        TotalCommand totalCommand = new TotalCommand();
        CommandResult result = totalCommand.execute("");

        assertTrue(result.isSuccess());
        double expectedTotal = ExpenseManager.getTotalExpenses();
        assertEquals(String.format(MessageDisplayer.TOTAL_SUCCESS_MESSAGE_TEMPLATE,
                expectedTotal), result.message());
    }
}
