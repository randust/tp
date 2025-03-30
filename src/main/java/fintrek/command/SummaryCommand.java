package fintrek.command;

import fintrek.ExpenseManager;
import fintrek.misc.MessageDisplayer;

@CommandInfo(
        description = """
            Format: /summary [COMMAND]
            Returns a summary of the total spending in each category.
            Optionally pass a keyword to show total spending in that category.
            """
)
public class SummaryCommand extends Command {
    @Override
    public CommandResult execute(String arguments) {
        String message;
        String categorySummary;

        if (arguments == null || arguments.isBlank()) {
            categorySummary = ExpenseManager.listAllCategoryTotals();
        } else {
            String category = arguments.trim().toUpperCase();
            categorySummary = ExpenseManager.listSingleCategoryTotal(category);

            if (categorySummary.equals(MessageDisplayer.CATEGORY_NOT_FOUND)) {
                message = MessageDisplayer.ERROR_LOADING_SUMMARY + MessageDisplayer.CATEGORY_NOT_FOUND;
                return new CommandResult(false, message);
            }
        }
        message = String.format(MessageDisplayer.LIST_SUMMARY_SUCCESS_MESSAGE_TEMPLATE, categorySummary);
        return new CommandResult(true, message);
    }
}
