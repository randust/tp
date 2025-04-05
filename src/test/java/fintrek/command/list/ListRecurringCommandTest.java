package fintrek.command.list;

import fintrek.command.registry.CommandResult;
import fintrek.expense.core.RecurringExpenseManager;
import fintrek.misc.MessageDisplayer;
import fintrek.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ListRecurringCommandTest {
    private static final RecurringExpenseManager recurringExpenseManager =
            RecurringExpenseManager.getInstance();
    /**
     * Clear all existing expenses in ExpenseManager before each test.
     */
    @BeforeEach
    public void setUp() {
        recurringExpenseManager.clear();
    }

    /**
     * Tests list command with empty ArrayList.
     * Ensures the command returns a successful CommandResult with the correct empty list message.
     */
    @Test
    public void testListCommand_emptyList_success() {
        ListCommand command = new ListCommand(true);
        CommandResult result = command.execute("");
        String expectedMessage = String.format(MessageDisplayer.LIST_RECURRING_SUCCESS_MESSAGE_TEMPLATE,
                TestUtils.recurringReporter.listExpenses());

        assertTrue(result.isSuccess(), MessageDisplayer.ASSERT_COMMAND_SUCCESS_PREFIX
                + MessageDisplayer.ASSERT_EMPTY_LIST);
        assertEquals(expectedMessage, result.message(), MessageDisplayer.ASSERT_COMMAND_EXPECTED_OUTPUT
                + MessageDisplayer.ASSERT_EMPTY_LIST);
    }

    /**
     * Tests list command with list of predefined expenses.
     * Ensures the command returns a successful CommandResult with the correct list of expenses.
     */
    @Test
    public void testListCommand_filledList_success() {
        TestUtils.addConstantRecurringExpenses();

        ListCommand command = new ListCommand(true);
        CommandResult result = command.execute("");
        String expectedMessage = String.format(MessageDisplayer.LIST_RECURRING_SUCCESS_MESSAGE_TEMPLATE,
                TestUtils.recurringReporter.listExpenses());

        assertTrue(result.isSuccess(), MessageDisplayer.ASSERT_COMMAND_SUCCESS_PREFIX
                + MessageDisplayer.ASSERT_FILLED_LIST);
        assertEquals(expectedMessage, result.message(),MessageDisplayer.ASSERT_COMMAND_EXPECTED_OUTPUT
                + MessageDisplayer.ASSERT_FILLED_LIST);
    }

    /**
     * Tests the description of list command.
     * Ensures the command returns the correct description.
     */
    @Test
    public void testListCommand_getDescription_success() {
        ListCommand command = new ListCommand(true);
        String expectedDescription = """
            Format: /list-recurring
            Lists all recorded expenses.
            """;

        assertEquals(expectedDescription, command.getDescription(),
                MessageDisplayer.ASSERT_COMMAND_EXPECTED_OUTPUT + MessageDisplayer.ASSERT_GET_DESC);
    }
}
