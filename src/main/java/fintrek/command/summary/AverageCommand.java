//@@author edwardrl101
package fintrek.command.summary;

import fintrek.command.Command;
import fintrek.command.registry.CommandInfo;
import fintrek.command.registry.CommandResult;
import fintrek.misc.MessageDisplayer;

@CommandInfo(
        recurringFormat = "Format: /average-recurring",
        regularFormat = "Format: /average",
        description = """
            Returns average of all expenses in list, but will return 0 if the list is empty
            Example: For a list of expenses: TransportExpense1, TransportExpense2, FoodExpense1
            """,
        recurringExample = "/average-recurring returns (TransportExpense1 + TransportExpense2 + FoodExpense1) / 3",
        regularExample = "/average returns (TransportExpense1 + TransportExpense2 + FoodExpense1) / 3"
)
public class AverageCommand extends Command {
    private final boolean isRecurringExpense;

    public AverageCommand(boolean isRecurring) {
        super(isRecurring);
        isRecurringExpense = isRecurring;
    }

    /**
     * this function will find the average of general or recurring expenses
     * @param arguments raw user input passed to the command
     * @return a {@code CommandResult} which object telling whether the
     *      execution is successful or not, and an error/success message
     */
    @Override
    public CommandResult execute(String arguments) {
        try {
            double average = reporter.getAverage();
            String message = (isRecurringExpense) ?
                    String.format(MessageDisplayer.AVERAGE_RECURRING_SUCCESS_MESSAGE_TEMPLATE, average):
                    String.format(MessageDisplayer.AVERAGE_SUCCESS_MESSAGE_TEMPLATE, average);
            return new CommandResult(true, message);
        } catch (Exception e) {
            return new CommandResult(false,
                    MessageDisplayer.ERROR_CALCULATING_AVERAGE_EXPENSES + e.getMessage());
        }
    }
}
