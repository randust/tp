//@@author szeyingg
package fintrek.command;

import fintrek.Expense;
import fintrek.ExpenseManager;
import fintrek.misc.MessageDisplayer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@CommandInfo(
        description = """
            Format: /add [DESCRIPTION] $[AMOUNT] /c[CATEGORY] /d[DATE]
            AMOUNT must be a positive number greater than 0
            CATEGORY is an optional argument, defaults to 'Uncategorized' if left empty
            DATE is an optional argument, defaults to today's date if left empty
            Example: /add concert tickets $35.80 -
            """ + " adds an expense with description 'concert tickets' with the amount $35.80."
)
public class AddCommand extends Command {

    @Override
    public CommandResult execute(String arguments) {
        if (arguments == null || arguments.isBlank()) {
            return new CommandResult(false, MessageDisplayer.MISSING_DESC_AND_AMOUNT_MESSAGE);
        }

        String[] parts = arguments.split("\\s*\\$\\s*|\\s*/c\\s*|\\s*/d\\s*");
        String description = (parts.length >= 1) ? parts[0].trim() : "";

        double amount = -1;
        if (parts.length >= 2) {
            if (!parts[1].matches("\\d+(\\.\\d+)?")) {
                return new CommandResult(false, MessageDisplayer.INVALID_AMT_MESSAGE);
            }
            amount = Double.parseDouble(parts[1].trim());
            assert amount > 0 : MessageDisplayer.INVALID_AMT_MESSAGE;
        }

        String category = (parts.length >= 3) ? parts[2].trim() : "Uncategorized";
        LocalDate date = (parts.length >= 4) ? LocalDate.parse(parts[3].trim(),
                DateTimeFormatter.ofPattern("dd-MM-yyyy")) : LocalDate.now();

        Expense newExpense = new Expense(description, amount, category, date);
        ExpenseManager.addExpense(newExpense);

        String message = String.format(MessageDisplayer.ADD_SUCCESS_MESSAGE_TEMPLATE, newExpense);
        return new CommandResult(true, message);
    }
}
