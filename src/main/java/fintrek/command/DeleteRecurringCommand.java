//@@author Charly2312
package fintrek.command;

import fintrek.Expense;
import fintrek.ExpenseManager;
import fintrek.misc.MessageDisplayer;
import fintrek.utils.InputValidator;

@CommandInfo(
        description = """
            Format: /delete-recurring [INDEX]
            INDEX must be a positive integer > 0
            Example: /delete 2 - deletes the recurring expense with index number 2 on the list.
            """
)
public class DeleteRecurringCommand extends Command{

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
        int upperBound = ExpenseManager.checkRecurringExpenseSize();
        if (!InputValidator.isInValidIntRange(expenseIndex, smallestValidIndex, upperBound)) {
            return new CommandResult(false, MessageDisplayer.IDX_OUT_OF_BOUND_MESSAGE);
        }

        int zeroBaseExpenseIndex = expenseIndex - 1;
        Expense removedExpense = ExpenseManager.deleteRecurringExpense(zeroBaseExpenseIndex);
        int remaining = ExpenseManager.checkRecurringExpenseSize();
        String expenseStr = '"' + removedExpense.toString() + '"';
        String message = String.format(MessageDisplayer.DELETE_RECURRING_SUCCESS_MESSAGE_TEMPLATE, expenseStr, remaining);
        return new CommandResult(true, message);
    }
}
