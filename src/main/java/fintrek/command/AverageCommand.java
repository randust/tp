//@@author edwardrl101
package fintrek.command;

import fintrek.ExpenseManager;
import fintrek.misc.MessageDisplayer;

public class AverageCommand extends Command {

    @Override
    public CommandResult execute(String arguments) {
        try {
            double average = ExpenseManager.getAverageExpenses();
            String message = String.format(MessageDisplayer.AVERAGE_SUCCESS_MESSAGE_TEMPLATE, average);
            return new CommandResult(true, message);
        } catch (Exception e) {
            return new CommandResult(false, MessageDisplayer.ERROR_CALCULATING_AVERAGE_EXPENSES + e.getMessage());
        }
    }

    @Override
    public String getDescription() {
        return "Displays average of all expenses: /average";
    }
}
