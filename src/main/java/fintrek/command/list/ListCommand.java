//@@author szeyingg
package fintrek.command.list;

import fintrek.command.Command;
import fintrek.command.registry.CommandInfo;
import fintrek.command.registry.CommandResult;
import fintrek.misc.MessageDisplayer;

@CommandInfo(
        description = """
            Format: /list
            Lists all recorded expenses.
            """

)
public class ListCommand extends Command {

    public ListCommand(boolean isRecurring) {
        super(isRecurring);
    }

    @Override
    public CommandResult execute(String arguments) {
        String message = String.format(MessageDisplayer.LIST_SUCCESS_MESSAGE_TEMPLATE,
                reporter.listExpenses());
        return new CommandResult(true, message);
    }
}
