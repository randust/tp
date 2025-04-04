//@@author venicephua
package fintrek.command.summary;

import fintrek.command.Command;
import fintrek.command.registry.CommandInfo;
import fintrek.command.registry.CommandResult;
import fintrek.misc.MessageDisplayer;
import fintrek.util.InputValidator;

@CommandInfo(
        recurringFormat = "Format: /summary-recurring [CATEGORY]",
        regularFormat = "Format: /summary [CATEGORY]",
        description = """
            Returns a summary of the total spending in each category.
            Optionally pass a keyword to show the total spending and expenses in that category.
            """
)
public class SummaryCommand extends Command {

    public SummaryCommand(boolean isRecurring) {
        super(isRecurring);
    }

    /**
     * This function summarizes general or recurring expense in each category
     * @param arguments is the category to be summarised,
     *                  but can be empty to summarise all categories
     * @return a {@code CommandResult} which object telling whether the
     *      execution is successful or not, and an error/success message
     */
    @Override
    public CommandResult execute(String arguments) {
        String message;
        String categorySummary;

        if (InputValidator.isNullOrBlank(arguments)) {
            categorySummary = reporter.listAllCategoryTotals();
        } else {
            String category = arguments.trim().toUpperCase();
            categorySummary = reporter.listSingleCategoryTotal(category);

            if (categorySummary.equals(MessageDisplayer.CATEGORY_NOT_FOUND)) {
                String errorMessage = MessageDisplayer.ERROR_LOADING_SUMMARY
                        + MessageDisplayer.CATEGORY_NOT_FOUND;
                return new CommandResult(false, errorMessage);
            }
        }
        message = (isRecurringExpense)?
                String.format(MessageDisplayer.LIST_SUMMARY_RECURRING_SUCCESS_MESSAGE_TEMPLATE, categorySummary):
                String.format(MessageDisplayer.LIST_SUMMARY_SUCCESS_MESSAGE_TEMPLATE, categorySummary);
        return new CommandResult(true, message);
    }
}
