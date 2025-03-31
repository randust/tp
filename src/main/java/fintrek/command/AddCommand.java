//@@author szeyingg
package fintrek.command;

import fintrek.expense.core.Expense;
import fintrek.expense.ExpenseManager;
import fintrek.misc.MessageDisplayer;
import fintrek.utils.InputValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@CommandInfo(
        description = """
                Format: /add [DESCRIPTION] $[AMOUNT] /c [CATEGORY]
                AMOUNT must be a positive number greater than 0
                Example: /add concert tickets $35.80 -
                """ + " adds an expense with description 'concert tickets' with the amount $35.80."
)

public class AddCommand extends Command {

    @Override
    public CommandResult execute(String arguments) {
        if (InputValidator.isNullOrBlank(arguments)) {
            return new CommandResult(false, MessageDisplayer.INVALID_ADD_FORMAT_MESSAGE);
        }

        Pattern p = Pattern.compile(InputValidator.validAddFormat());
        Matcher m = p.matcher(arguments.trim());
        if (!m.matches()) {
            return new CommandResult(false, MessageDisplayer.INVALID_ADD_FORMAT_MESSAGE);
        }

        String description = m.group(1).trim();
        String amountStr = m.group(2);
        String category = (m.group(3) != null) ? m.group(3).trim() : "Uncategorized";

        if (!InputValidator.isValidAmountInput(amountStr)) {
            return new CommandResult(false, MessageDisplayer.INVALID_AMT_MESSAGE);
        }
        double amount = Double.parseDouble(amountStr);
        assert amount > 0 : MessageDisplayer.INVALID_AMT_MESSAGE;

        Expense newExpense = new Expense(description, amount, category);
        ExpenseManager.addExpense(newExpense);

        String message = String.format(MessageDisplayer.ADD_SUCCESS_MESSAGE_TEMPLATE, newExpense);
        return new CommandResult(true, message);
    }
}
