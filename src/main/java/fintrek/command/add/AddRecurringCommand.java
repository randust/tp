//@@author Charly2312
package fintrek.command.add;

import fintrek.command.Command;
import fintrek.command.registry.CommandInfo;
import fintrek.command.registry.CommandResult;
import fintrek.expense.core.Expense;
import fintrek.expense.ExpenseManager;
import fintrek.misc.MessageDisplayer;
import fintrek.util.InputValidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@CommandInfo(
        description = """
                Format: /recurring [DESCRIPTION] $[AMOUNT] /c[CATEGORY] [DATE]
                Add a recurring expense to be automatically added into the general list of expenses 
                at the stipulated date
                [DATE] must be in the format of dd-MM-yyyy.
                Example: /recurring mobile data $19.00 29-01-2025
                will add 'mobile data' expense of $19.00 at every 29th date of each month
                """
)
@Deprecated
public class AddRecurringCommand extends Command {

    public AddRecurringCommand(boolean isRecurring) {
        super(isRecurring);
    }

    @Override
    public CommandResult execute(String arguments) {
        if(InputValidator.isNullOrBlank(arguments)) {
            return new CommandResult(false, MessageDisplayer.INVALID_ADD_RECURRING_FORMAT_MESSAGE);
        }

        Pattern p = Pattern.compile(InputValidator.validAddRecurringFormat());
        Matcher m = p.matcher(arguments.trim());
        if (!m.matches()) {
            return new CommandResult(false, MessageDisplayer.INVALID_ADD_RECURRING_FORMAT_MESSAGE);
        }

        String description = m.group(1).trim();
        String amountStr = m.group(2);
        String category = (m.group(3) != null) ? m.group(3).trim() : "Uncategorized";
        String dateStr = (m.group(4));

        if (!InputValidator.isValidAmountInput(amountStr)) {
            return new CommandResult(false, MessageDisplayer.INVALID_AMT_MESSAGE);
        }
        double amount = Double.parseDouble(amountStr);
        assert amount > 0 : MessageDisplayer.INVALID_AMT_MESSAGE;

        assert !dateStr.isEmpty() : new CommandResult(false, MessageDisplayer.EMPTY_DATE_MESSAGE);
        assert isValidDate(dateStr) : new CommandResult(false, MessageDisplayer.WRONG_DATE_FORMAT_MESSAGE);
        LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        Expense newExpense = new Expense(description, amount, category, date);
        newExpense.updateDate(date);
        ExpenseManager.addRecurringExpense(newExpense);
        String message = String.format(MessageDisplayer.ADD_RECURRING_SUCCESS_MESSAGE_TEMPLATE, newExpense);
        return new CommandResult(true, message);
    }

    public static boolean isValidDate(String input) {
        try {
            // Try to parse the input string as a LocalDate in "dd-MM-yyyy" format
            LocalDate.parse(input, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

}
