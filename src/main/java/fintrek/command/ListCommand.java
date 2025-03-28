//@@author szeyingg
package fintrek.command;

import fintrek.ExpenseManager;
import fintrek.misc.MessageDisplayer;

public class ListCommand extends Command {

    @Override
    public CommandResult execute(String arguments) {
        String message = String.format(MessageDisplayer.LIST_SUCCESS_MESSAGE_TEMPLATE,
                ExpenseManager.listExpenses());
        return new CommandResult(true, message);
    }

    @Override
    public String getDescription() {
        return "Lists all recorded expenses: /list";
    }
}
