//@@author Charly2312
package fintrek.command.list;

import fintrek.command.Command;
import fintrek.command.registry.CommandInfo;
import fintrek.command.registry.CommandResult;
import fintrek.misc.MessageDisplayer;

@CommandInfo(
        recurringFormat = "",
        regularFormat = "",
        description = """
            Format: /list-recurring
            List all recorded recurring expenses.
            """
)
@Deprecated
public class ListRecurringCommand extends Command {

    public ListRecurringCommand(boolean isRecurring) {
        super(isRecurring);
    }

    @Override
    public CommandResult execute(String arguments) {
        String message = String.format(MessageDisplayer.LIST_RECURRING_SUCCESS_MESSAGE_TEMPLATE,
                reporter.listExpenses());
        return new CommandResult(true, message);
    }
}
