//@@author venicephua
package fintrek.command.summary;

import fintrek.command.Command;
import fintrek.command.registry.CommandInfo;
import fintrek.command.registry.CommandResult;
import fintrek.misc.MessageDisplayer;

@CommandInfo(
        recurringFormat = "Format: /total-recurring",
        regularFormat = "Format: /total",
        description = """
            Returns sum of all expenses in the list, but will return 0 if the list is empty.
            Example: For a list of expenses: TransportExpense1, TransportExpense2, FoodExpense1
            /total returns (TransportExpense1 + TransportExpense2 + FoodExpense1).
            """
)
public class TotalCommand extends Command {

    public TotalCommand(boolean isRecurring) {
        super(isRecurring);
    }

    /**
     * The function calculates the total of the expenses
     * @param arguments raw user input passed to the command
     * @return a {@code CommandResult} which object telling whether the
     *      execution is successful or not, and an error/success message
     */
    @Override
    public CommandResult execute(String arguments) {
        double total = reporter.getTotal();
        if (total == -1) {
            String errorMessage = MessageDisplayer.ERROR_CALCULATING_TOTAL_EXPENSES +
                    MessageDisplayer.TOTAL_EXCEEDS_LIMIT;
            return new CommandResult(false, errorMessage);
        }
        String message = (isRecurringExpense) ?
                String.format(MessageDisplayer.TOTAL_RECURRING_SUCCESS_MESSAGE_TEMPLATE, total):
                String.format(MessageDisplayer.TOTAL_SUCCESS_MESSAGE_TEMPLATE, total);
        return new CommandResult(true, message);
    }
}
