package fintrek.command.delete;

import fintrek.expense.core.Expense;
import fintrek.expense.ExpenseManager;
import fintrek.command.Command;
import fintrek.command.registry.CommandInfo;
import fintrek.command.registry.CommandResult;
import fintrek.misc.MessageDisplayer;

import fintrek.utils.InputValidator;

@CommandInfo(
        description = """
            Format: /delete [INDEX]
            INDEX must be a positive integer > 0
            Example: /delete 2 - deletes the expense with index number 2 on the list.
            """
)
public class DeleteCommand extends Command {

    @Override
    public CommandResult execute(String arguments) {
        if (isInvalidArgument(arguments)) {
            return new CommandResult(false, MessageDisplayer.IDX_EMPTY_MESSAGE);
        }

        int index = Integer.parseInt(arguments.trim());
        int totalExpenses = ExpenseManager.getLength();

        if (!isIndexWithinRange(index, totalExpenses)) {
            return new CommandResult(false, MessageDisplayer.IDX_OUT_OF_BOUND_MESSAGE);
        }

        Expense removedExpense = ExpenseManager.popExpense(index - 1);
        String message = formatSuccessMessage(removedExpense, ExpenseManager.getLength());

        return new CommandResult(true, message);
    }

    private boolean isInvalidArgument(String arguments) {
        return InputValidator.isNullOrBlank(arguments)
                || !InputValidator.isValidPositiveInteger(arguments);
    }

    private boolean isIndexWithinRange(int index, int upperBound) {
        return InputValidator.isInValidIntRange(index, 1, upperBound);
    }

    private String formatSuccessMessage(Expense expense, int remainingCount) {
        return String.format(
                MessageDisplayer.DELETE_SUCCESS_MESSAGE_TEMPLATE,
                '"' + expense.toString() + '"',
                remainingCount
        );
    }
}

