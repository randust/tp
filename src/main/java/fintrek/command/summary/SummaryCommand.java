//@@author venicephua
package fintrek.command.summary;

import java.util.Map;

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
    private final boolean isRecurringExpense;

    public SummaryCommand(boolean isRecurring) {
        super(isRecurring);
        isRecurringExpense = isRecurring;
    }

    @Override
    public CommandResult execute(String arguments) {
        String message;
        String categorySummary;
        Map<String, Double> categoryTotals = reporter.getTotalByCategory();

        if (InputValidator.isNullOrBlank(arguments)) {
            if (categoryTotals.isEmpty()) {
                categorySummary = MessageDisplayer.EMPTY_LIST_MESSAGE;
            } else {
                categorySummary = reporter.listAllCategoryTotals(categoryTotals);
            }
        } else {
            String category = arguments.trim().toUpperCase();
            if (!categoryTotals.containsKey(category)) {
                String errorMessage =  MessageDisplayer.ERROR_LOADING_SUMMARY
                        + MessageDisplayer.CATEGORY_NOT_FOUND;
                return new CommandResult(false, errorMessage);
            } else {
                categorySummary = reporter.listSingleCategoryTotal(categoryTotals, category);
            }
        }
        message = (isRecurringExpense)?
                String.format(MessageDisplayer.LIST_SUMMARY_RECURRING_SUCCESS_MESSAGE_TEMPLATE, categorySummary) :
                String.format(MessageDisplayer.LIST_SUMMARY_SUCCESS_MESSAGE_TEMPLATE, categorySummary);
        return new CommandResult(true, message);
    }
}
