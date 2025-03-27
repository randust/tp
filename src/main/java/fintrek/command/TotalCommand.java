//@@author venicephua
package fintrek.command;

import fintrek.ExpenseManager;
import fintrek.misc.MessageDisplayer;

public class TotalCommand extends Command {

    @Override
    public CommandResult execute(String arguments) {
        try {
            double total = ExpenseManager.getTotalExpenses();
            String message = String.format(MessageDisplayer.TOTAL_SUCCESS_MESSAGE_TEMPLATE, total);
            return new CommandResult(true, message);
        } catch (Exception e) {
            return new CommandResult(false, MessageDisplayer.ERROR_CALCULATING_TOTAL_EXPENSES + e.getMessage());
        }
    }

    @Override
    public String getDescription() {
        return "Shows total of all expenses: /total";
    }
}
