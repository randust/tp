package fintrek.command.delete;

import fintrek.expense.core.Expense;
import fintrek.command.Command;
import fintrek.command.registry.CommandInfo;
import fintrek.command.registry.CommandResult;
import fintrek.misc.MessageDisplayer;

import fintrek.util.InputValidator;

/**
 * Handles the deletion of a specified expense from the list.
 *
 * <p>This command expects a single positive integer argument representing the
 * index of the expense to delete. It performs input validation, checks index bounds,
 * removes the specified expense, and returns a formatted success or error message.</p>
 *
 * <p>The index is 1-based (i.e., the first expense has index 1).</p>
 *
 * <p>Supports both normal and recurring expenses depending on the value of {@code isRecurring}
 * passed to the constructor.</p>
 *
 * <p>Example usage:
 * <pre>
 * /delete 2
 * </pre>
 * Deletes the expense at index 2 from the list.</p>
 *
 * @see Command
 * @see fintrek.expense.service.ExpenseService
 */
@CommandInfo(
        recurringFormat = "Format: /delete-recurring <RECURRING_EXPENSE_NUMBER>",
        regularFormat = "Format: /delete <EXPENSE_NUMBER>",
        description = """
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
        String message = (isRecurringExpense)?
                String.format(MessageDisplayer.DELETE_RECURRING_SUCCESS_MESSAGE_TEMPLATE, expenseStr, remaining):
                String.format(MessageDisplayer.DELETE_SUCCESS_MESSAGE_TEMPLATE, expenseStr, remaining);
        return new CommandResult(true, message);
    }
}
