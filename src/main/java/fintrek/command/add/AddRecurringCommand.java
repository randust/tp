//@@author Charly2312
package fintrek.command.add;

import fintrek.command.Command;
import fintrek.command.registry.CommandInfo;
import fintrek.command.registry.CommandResult;
import fintrek.expense.core.Expense;
import fintrek.expense.ExpenseManager;
import fintrek.misc.MessageDisplayer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


@CommandInfo(
        description = """
                Format: /recurring [DESCRIPTION] $[AMOUNT] [DATE]
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
        if (arguments == null || arguments.isBlank()) {
            return new CommandResult(false, MessageDisplayer.INVALID_ADD_FORMAT_MESSAGE);
        }

        String[] parts = arguments.split("\\s*\\$\\s*|\\s*/c\\s*|\\s*/d\\s*");

        //handling description
        String description = (parts.length >= 1) ? parts[0].trim() : "";

        //handling amount
        String[] segments = parts[1].split(" ", 3); //[amount, date]

        double amount = -1;
        if (parts.length >= 2) {
            if (!segments[0].matches("\\d+(\\.\\d+)?")) {
                System.out.println(segments[0]);
                return new CommandResult(false, MessageDisplayer.INVALID_AMT_MESSAGE);
            }
            amount = Double.parseDouble(segments[0].trim());
            assert amount > 0 : MessageDisplayer.INVALID_AMT_MESSAGE;
        }

        //handling date
        LocalDate date = null;
        if (segments.length >= 2) {
            String dateStr = segments[1].trim();
            if (dateStr.isEmpty()) {
                return new CommandResult(false, MessageDisplayer.EMPTY_DATE_MESSAGE);
            }

            if (!isValidFormat(dateStr)) {
                return new CommandResult(false, MessageDisplayer.WRONG_DATE_FORMAT_MESSAGE);
            }

            try {
                date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            } catch (DateTimeParseException e) {
                return new CommandResult(false, MessageDisplayer.WRONG_DATE_FORMAT_MESSAGE);
            }
        }

        //handling category
        String category = (parts.length >= 4) ? parts[3].trim() : "Uncategorized";
        LocalDate currDate = (parts.length >= 5) ? LocalDate.parse(parts[4].trim()) : LocalDate.now();

        Expense newExpense = new Expense(description, amount, category, currDate);
        newExpense.updateDate(date);
        ExpenseManager.addRecurringExpense(newExpense);

        String message = String.format(MessageDisplayer.ADD_RECURRING_SUCCESS_MESSAGE_TEMPLATE, newExpense);
        return new CommandResult(true, message);
    }

    public static boolean isValidFormat(String input) {
        try {
            // Try to parse the input string as a LocalDate in "dd-MM-yyyy" format
            LocalDate.parse(input, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
