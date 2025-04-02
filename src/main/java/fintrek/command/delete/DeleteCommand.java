package fintrek.command.delete;

import fintrek.expense.core.Expense;
import fintrek.command.Command;
import fintrek.command.registry.CommandInfo;
import fintrek.command.registry.CommandResult;
import fintrek.misc.MessageDisplayer;

import fintrek.util.InputValidator;

@CommandInfo(
        description = """
            Format: /delete <INDEX>
            INDEX must be a positive integer > 0
            Example: /delete 2 - deletes the expense with index number 2 on the list.
            """
)
public class DeleteCommand extends Command {

    public DeleteCommand(boolean isRecurring) {
        super(isRecurring);
    }

    @Override
    public CommandResult execute(String arguments) {
        if (InputValidator.isNullOrBlank(arguments)) {
            return new CommandResult(false, MessageDisplayer.IDX_EMPTY_MESSAGE);
        }
        if (!InputValidator.isValidPositiveInteger(arguments)) {
            return new CommandResult(false, MessageDisplayer.INVALID_IDX_FORMAT_MESSAGE);
        }

        int expenseIndex = Integer.parseInt(arguments.trim());
        int smallestValidIndex = 1;
        int upperBound = service.countExpenses();
        if (!InputValidator.isInValidIntRange(expenseIndex, smallestValidIndex, upperBound)) {
            return new CommandResult(false, MessageDisplayer.IDX_OUT_OF_BOUND_MESSAGE);
        }

        int zeroBaseExpenseIndex = expenseIndex - 1;
        Expense removedExpense = service.popExpense(zeroBaseExpenseIndex);
        int remaining = service.countExpenses();
        String expenseStr = '"' + removedExpense.toString() + '"';
        String message = String.format(MessageDisplayer.DELETE_SUCCESS_MESSAGE_TEMPLATE, expenseStr, remaining);
        return new CommandResult(true, message);
    }
}
