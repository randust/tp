//@@author szeyingg
package fintrek.command.list;

import fintrek.command.Command;
import fintrek.command.registry.CommandInfo;
import fintrek.command.registry.CommandResult;
import fintrek.misc.MessageDisplayer;

@CommandInfo(
        recurringFormat = "Format: /list-recurring",
        regularFormat = "Format: /list",
        description = "",
        recurringExample = "Lists all recorded recurring expenses.",
        regularExample = "Lists all recorded expenses."
)
public class ListCommand extends Command {

    public ListCommand(boolean isRecurring) {
        super(isRecurring);
    }

    /**
     * This functions list out all the recurring or general expenses based on the argument received
     * @param arguments raw user input passed to the command
     * @return a {@code CommandResult} object telling whether the
     *      execution is successful or not, and an error/success message
     */
    @Override
    public CommandResult execute(String arguments) {
        String template = (isRecurringExpense) ? MessageDisplayer.LIST_RECURRING_SUCCESS_MESSAGE_TEMPLATE :
                MessageDisplayer.LIST_SUCCESS_MESSAGE_TEMPLATE;
        String message = String.format(template,
                reporter.listExpenses());
        return new CommandResult(true, message);
    }
}
