package fintrek.command;

import fintrek.Expense;
import fintrek.ExpenseManager;
import fintrek.misc.MessageDisplayer;

public class DeleteCommand extends Command {

    @Override
    public CommandResult execute(String arguments) {
        if (arguments == null || arguments.isBlank()) {
            return new CommandResult(false, MessageDisplayer.IDX_EMPTY_MESSAGE);
        }

        if (!arguments.trim().matches("\\d+")) {
            return new CommandResult(false, MessageDisplayer.INVALID_IDX_MESSAGE);
        }

        int expenseIndex = Integer.parseInt(arguments.trim());
        if (expenseIndex <= 0 || expenseIndex > ExpenseManager.getLength()) {
            return new CommandResult(false, MessageDisplayer.INVALID_IDX_MESSAGE);
        }
        assert expenseIndex > 0;

        Expense removedExpense = ExpenseManager.popExpense(expenseIndex - 1);
        int remaining = ExpenseManager.getLength();

        String message = String.format(MessageDisplayer.DELETE_SUCCESS_MESSAGE_TEMPLATE, remaining);
        return new CommandResult(true, message);
    }

    @Override
    public String getDescription() {
        return """
            Format: /delete [INDEX]
            INDEX must be a positive integer > 0
            Example: /delete 2 - deletes the expense with index number 2 on the list.
            """;
    }
}
