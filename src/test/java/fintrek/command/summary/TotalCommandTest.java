package fintrek.command.summary;

import fintrek.command.registry.CommandResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import fintrek.expense.ExpenseManager;
import fintrek.misc.MessageDisplayer;
import fintrek.util.TestUtils;

/**
 * Unit tests for the {@code TotalCommand} class.
 * Ensures that the total expense amount is correctly calculated and returned.
 */
public class TotalCommandTest {
    /**
     * Clear all existing expenses in ExpenseManager before each test.
     */
    @BeforeEach
    public void setUp() {
        ExpenseManager.clearExpenses();
    }

    /**
     * Tests total command with empty list.
     * Ensures the command returns a successful CommandResult with a total of 0.0.
     */
    @Test
    public void testTotalCommand_emptyList_success() {
        TotalCommand totalCommand = new TotalCommand();
        CommandResult result = totalCommand.execute("");
        String expectedMessage = String.format(MessageDisplayer.TOTAL_SUCCESS_MESSAGE_TEMPLATE, 0.0);

        assertTrue(result.isSuccess(), MessageDisplayer.ASSERT_COMMAND_SUCCESS_PREFIX
                + MessageDisplayer.ASSERT_EMPTY_LIST);
        assertEquals(expectedMessage, result.message(), MessageDisplayer.ASSERT_COMMAND_EXPECTED_OUTPUT
                + MessageDisplayer.ASSERT_EMPTY_LIST);
    }

    /**
     * Tests total command with a list of predefined expenses.
     * Ensures the command calculates and returns a successful CommandResult
     * and the correct total expense amount.
     */
    @Test
    public void testTotalCommand_filledList_success() {
        TestUtils.addConstantExpenses();

        TotalCommand totalCommand = new TotalCommand();
        CommandResult result = totalCommand.execute("");
        double expectedTotal = ExpenseManager.getTotalExpenses();
        String expectedMessage = String.format(MessageDisplayer.TOTAL_SUCCESS_MESSAGE_TEMPLATE, expectedTotal);

        assertTrue(result.isSuccess(), MessageDisplayer.ASSERT_COMMAND_SUCCESS_PREFIX
                + MessageDisplayer.ASSERT_FILLED_LIST);
        assertEquals(expectedMessage, result.message(), MessageDisplayer.ASSERT_COMMAND_EXPECTED_OUTPUT
                + MessageDisplayer.ASSERT_FILLED_LIST);
    }

    /**
     * Tests the description of total command.
     * Ensures the command returns the correct description.
     */
    @Test
    public void testTotalCommand_getDescription_success() {
        TotalCommand command = new TotalCommand();
        String expectedDescription = """
            Format: /total
            Returns sum of all expenses in the list, but will return 0 if the list is empty.
            Example: For a list of expenses: TransportExpense1, TransportExpense2, FoodExpense1
            /total returns (TransportExpense1 + TransportExpense2 + FoodExpense1).
            """;

        assertEquals(expectedDescription, command.getDescription(),
                MessageDisplayer.ASSERT_COMMAND_EXPECTED_OUTPUT + MessageDisplayer.ASSERT_GET_DESC);
    }
}
