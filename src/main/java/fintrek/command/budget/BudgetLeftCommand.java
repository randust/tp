package fintrek.command.budget;

import fintrek.budget.core.BudgetManager;
import fintrek.command.Command;
import fintrek.command.registry.CommandInfo;
import fintrek.command.registry.CommandResult;
import fintrek.misc.MessageDisplayer;

@CommandInfo(
        recurringFormat = "Format: /budget-left-recurring",
        regularFormat = "Format: /budget-left",
        description = """
                It will return the user's monthly budget and current budget left
                """,
        recurringExample = """
                Example: /budget-left
                Assuming an initial monthly budget of $500, with total expenses of $280.
                It will print out the monthly budget $500 and current budget left of $220.""",
        regularExample = """
                Example: /budget-left
                Assuming an initial monthly budget of $500, with total expenses of $280.
                It will print out the monthly budget $500 and current budget left of $220."""
)

public class BudgetLeftCommand extends Command {
    private static final String COMMAND_NAME = "budget-left";
    public BudgetLeftCommand(boolean isRecurring) {
        super(isRecurring);
    }

    /**
     * This function helps to print out the current monthly budget
     *      and budget left after deducting total expenses
     * @param arguments is the raw input of the user
     * @return a {@code CommandResult} object telling whether
     *      the execution is successful or not, and an error/success message
     */
    @Override
    public CommandResult execute(String arguments) {
        double budgetNow = BudgetManager.getInstance().getBudget();
        double total = reporter.getTotal();
        double budgetLeft = budgetNow - total;
        double leftPercentage = budgetLeft / budgetNow * 100.0;

        if (budgetLeft < 0) {
            return new CommandResult(true,
                    String.format(MessageDisplayer.EXCEEDED_BUDGET_MESSAGE, budgetNow, -budgetLeft));
        }

        return new CommandResult(true,
                String.format(MessageDisplayer.CURRENT_BUDGET_LEFT, budgetNow, total, budgetLeft,
                        leftPercentage));
    }
}
