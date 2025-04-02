package fintrek.command.budget;

import fintrek.command.registry.CommandResult;
import fintrek.util.ExpenseManager;
import fintrek.misc.MessageDisplayer;
import fintrek.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class BudgetCommandTest {
    /**
     * Clear all existing expenses in ExpenseManager before each test.
     */
    public static final String COMMAND_NAME = "budget";

    @BeforeEach
    public void setUp() {
        ExpenseManager.clearExpenses();
    }

    /**
     * Tests if BudgetCommand is able to set monthly budgets given valid inputs
     * @param input valid inputs in the form of $[AMOUNT] where AMOUNT is positive
     */
    @ParameterizedTest
    @ValueSource(strings = {
        "$  2.00", "$2.41", "    $3.14",
        "$3", "$0.50", "$1.50", "$200.00", "$350.00", "$231"
    })
    public void testBudgetCommandValidInput(String input) {
        BudgetCommand budgetCommand = new BudgetCommand(false);
        CommandResult result = budgetCommand.execute(input);

        TestUtils.assertCommandSuccess(result, input);
    }

    @ParameterizedTest
    @ValueSource (strings = {"", "             ", "  "})
    public void testBudgetCommandEmptyInput(String input) {
        BudgetCommand budgetCommand = new BudgetCommand(false);
        CommandResult result = budgetCommand.execute(input);

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandMessage(result, input,
                String.format(MessageDisplayer.ARG_EMPTY_MESSAGE_TEMPLATE, COMMAND_NAME));
    }

    /**
     * Tests if BudgetCommand returns error messages for invalid inputs
     * not in the format of $[AMOUNT]
     * @param input invalid inputs in various wrong formats
     */
    @ParameterizedTest
    @ValueSource (strings = {"65", "HI!", "CS2113", "$          S5.78", "$CS3230. 98", "Hello $5"})
    public void testBudgetInvalidFormatInput(String input) {
        BudgetCommand budgetCommand = new BudgetCommand(false);
        CommandResult result = budgetCommand.execute(input);

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandMessage(result, input,
               String.format(MessageDisplayer.INVALID_FORMAT_MESSAGE_TEMPLATE, COMMAND_NAME));
    }

    @ParameterizedTest
    @ValueSource (strings = {"$0"})
    public void testBudgetInvalidAmount(String input) {
        BudgetCommand budgetCommand = new BudgetCommand(false);
        CommandResult result = budgetCommand.execute(input);

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandMessage(result, input, MessageDisplayer.INVALID_AMT_MESSAGE);
    }
}
