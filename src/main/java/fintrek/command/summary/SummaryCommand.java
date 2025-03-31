//@@author venicephua
package fintrek.command.summary;

import fintrek.ExpenseManager;
import fintrek.command.Command;
import fintrek.command.registry.CommandInfo;
import fintrek.command.registry.CommandResult;
import fintrek.misc.MessageDisplayer;
import fintrek.utils.InputValidator;

@CommandInfo(
        description = """
            Format: /summary [CATEGORY]
            Returns a summary of the total spending in each category.
            Optionally pass a keyword to show the total spending and expenses in that category.
            """
)
public class SummaryCommand extends Command {
    @Override
    public CommandResult execute(String arguments) {
        String message;
        String categorySummary;

        if (InputValidator.isNullOrBlank(arguments)) {
            categorySummary = ExpenseManager.listAllCategoryTotals();
        } else {
            String category = arguments.trim().toUpperCase();
            categorySummary = ExpenseManager.listSingleCategoryTotal(category);

            if (categorySummary.equals(MessageDisplayer.CATEGORY_NOT_FOUND)) {
                String errorMessage = MessageDisplayer.ERROR_LOADING_SUMMARY
                        + MessageDisplayer.CATEGORY_NOT_FOUND;
                return new CommandResult(false, errorMessage);
            }
        }
        message = String.format(MessageDisplayer.LIST_SUMMARY_SUCCESS_MESSAGE_TEMPLATE, categorySummary);
        return new CommandResult(true, message);
    }
}
