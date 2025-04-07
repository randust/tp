package fintrek.command.budget;

import fintrek.budget.core.BudgetManager;
import fintrek.command.registry.CommandResult;
import fintrek.expense.core.RegularExpenseManager;
import fintrek.misc.MessageDisplayer;

import fintrek.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class BudgetLeftCommandTest {
    public static final String COMMAND_NAME = "budget-left";
    public static final double HIGH_MONTHLY_BUDGET = 500.0;
    public static final double LOW_MONTHLY_BUDGET = 50.0;
    private static final RegularExpenseManager regularExpenseManager =
            RegularExpenseManager.getInstance();

    /**
     * Clear all existing expenses in regularExpenseManager before each test.
     * Then add some constant regular expenses into the list.
     */
    @BeforeEach
    public void setUp() {
        regularExpenseManager.clear();
        TestUtils.addConstantExpenses();
    }

    /**
     * Tests if /budget-left can execute properly when it is below budget
     * @param input are empty arguments or whitespaces
     */
    @ParameterizedTest
    @ValueSource(strings = {
        "", "     "
    })
    public void testBudgetLeftCommand_emptyArgumentBelowBudget_success(String input) {
        BudgetLeftCommand budgetCommand = new BudgetLeftCommand(false);
        BudgetManager.getInstance().setBudget(HIGH_MONTHLY_BUDGET);
        double budgetNow = BudgetManager.getInstance().getBudget();
        double total = TestUtils.regularReporter.getTotal();
        double budgetLeft = budgetNow - total;
        double leftPercentage = budgetLeft / budgetNow * 100.0;

        CommandResult result = budgetCommand.execute(input);

        TestUtils.assertCommandSuccess(result, input);
        TestUtils.assertCommandMessage(result, input,
                String.format(MessageDisplayer.CURRENT_BUDGET_LEFT,
                        budgetNow, total, budgetLeft, leftPercentage));
    }

    /**
     * Tests if /budget-left can execute properly when it is below budget
     * @param input are non-empty arguments of words
     */
    @ParameterizedTest
    @ValueSource(strings = {
        "hihihihi", "   sup  "
    })
    public void testBudgetLeftCommand_nonEmptyArgumentBelowBudget_success(String input) {
        BudgetLeftCommand budgetCommand = new BudgetLeftCommand(false);
        BudgetManager.getInstance().setBudget(HIGH_MONTHLY_BUDGET);
        double budgetNow = BudgetManager.getInstance().getBudget();
        double total = TestUtils.regularReporter.getTotal();
        double budgetLeft = budgetNow - total;
        double leftPercentage = budgetLeft / budgetNow * 100.0;

        CommandResult result = budgetCommand.execute(input);

        TestUtils.assertCommandSuccess(result, input);
        TestUtils.assertCommandMessage(result, input,
                String.format(MessageDisplayer.CURRENT_BUDGET_LEFT,
                        budgetNow, total, budgetLeft, leftPercentage));
    }

    /**
     * Tests if /budget-left can execute properly when it is above budget
     * @param input are empty arguments or whitespaces
     */
    @ParameterizedTest
    @ValueSource(strings = {
        "", "     "
    })
    public void testBudgetLeftCommand_emptyArgumentExceedBudget_success(String input) {
        BudgetLeftCommand budgetCommand = new BudgetLeftCommand(false);
        BudgetManager.getInstance().setBudget(LOW_MONTHLY_BUDGET);
        double budgetNow = BudgetManager.getInstance().getBudget();
        double total = TestUtils.regularReporter.getTotal();
        double budgetLeft = budgetNow - total;
        double leftPercentage = budgetLeft / budgetNow * 100.0;

        CommandResult result = budgetCommand.execute(input);

        TestUtils.assertCommandSuccess(result, input);
        TestUtils.assertCommandMessage(result, input,
                String.format(MessageDisplayer.EXCEEDED_BUDGET_MESSAGE, budgetNow, -budgetLeft));
    }

    /**
     * Tests if /budget-left can execute properly when it is above budget
     * @param input are non-empty arguments of words
     */
    @ParameterizedTest
    @ValueSource(strings = {
        "hihihihi", "   sup  "
    })
    public void testBudgetLeftCommand_nonEmptyArgumentExceedBudget_success(String input) {
        BudgetLeftCommand budgetCommand = new BudgetLeftCommand(false);
        BudgetManager.getInstance().setBudget(LOW_MONTHLY_BUDGET);
        double budgetNow = BudgetManager.getInstance().getBudget();
        double total = TestUtils.regularReporter.getTotal();
        double budgetLeft = budgetNow - total;
        double leftPercentage = budgetLeft / budgetNow * 100.0;

        CommandResult result = budgetCommand.execute(input);

        TestUtils.assertCommandSuccess(result, input);
        TestUtils.assertCommandMessage(result, input,
                String.format(MessageDisplayer.EXCEEDED_BUDGET_MESSAGE, budgetNow, -budgetLeft));
    }
}
