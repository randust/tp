//@@author Charly2312
package fintrek.command.list;

import fintrek.ExpenseManager;
import fintrek.command.Command;
import fintrek.command.registry.CommandInfo;
import fintrek.command.registry.CommandResult;
import fintrek.misc.MessageDisplayer;

@CommandInfo(
        description = """
            Format: /list-recurring
            List all recorded recurring expenses.
            """
)
public class ListRecurringCommand extends Command {

    @Override
    public CommandResult execute(String arguments) {
        String message = String.format(MessageDisplayer.LIST_RECURRING_SUCCESS_MESSAGE_TEMPLATE,
                ExpenseManager.listRecurringExpenses());
        return new CommandResult(true, message);
    }
}
