//@@author szeyingg
package fintrek.command.list;

import fintrek.command.Command;
import fintrek.command.registry.CommandInfo;
import fintrek.command.registry.CommandResult;
import fintrek.expense.core.Expense;
import fintrek.misc.MessageDisplayer;
import fintrek.util.InputValidator;

import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@CommandInfo(
        description = """
                Format: /sort <SORT FIELD> <SORT DIRECTION>
                SORT FIELD valid inputs: name, amount, category, date
                SORT DIRECTION valid inputs: ascending, descending
                Example: /sort name ascending - sorts list in ascending alphabetical order.
                """
)

public class SortCommand extends Command {
    private static final String COMMAND_NAME = "sort";
    
    private static final Comparator<Expense> AMOUNT_ASC = Comparator.comparingDouble(Expense::getAmount);
    private static final Comparator<Expense> CATEGORY_ASC = Comparator.comparing(Expense::getCategory);
    private static final Comparator<Expense> DATE_ASC = Comparator.comparing(Expense::getDate);
    private static final Comparator<Expense> NAME_ASC = Comparator.comparing(Expense::getDescription);

    public SortCommand(boolean isRecurring) {
        super(isRecurring);
    }

    @Override
    public CommandResult execute(String arguments) {
        if (InputValidator.isNullOrBlank(arguments)) {
            return new CommandResult(false,
                    String.format(MessageDisplayer.INVALID_FORMAT_MESSAGE_TEMPLATE, COMMAND_NAME));
        }

        Pattern p = Pattern.compile("^\\s*(\\w+)\\s+(\\w+)\\s*$");
        Matcher m = p.matcher(arguments.trim().toUpperCase());

        if (!m.matches()) {
            return new CommandResult(false,
                    String.format(MessageDisplayer.INVALID_FORMAT_MESSAGE_TEMPLATE, COMMAND_NAME));
        }
        String sortBy = m.group(1).trim();
        String sortDir = m.group(2).trim();

        List<Expense> expenses = service.getAllExpenses();
        if (expenses.isEmpty()) {
            return new CommandResult(false, MessageDisplayer.EMPTY_LIST_MESSAGE);
        }

        Comparator<Expense> comparator;
        switch (sortBy) {
        case "NAME" -> comparator = NAME_ASC;
        case "AMOUNT" -> comparator = AMOUNT_ASC;
        case "CATEGORY" -> comparator = CATEGORY_ASC;
        case "DATE" -> comparator = DATE_ASC;
        default -> comparator = null;
        }
        if (comparator == null) {
            return new CommandResult(false, MessageDisplayer.INVALID_SORT_FIELD);
        }

        if (sortDir.equals("DESCENDING")) {
            comparator = comparator.reversed();
        } else if (!sortDir.equals("ASCENDING")) {
            return new CommandResult(false, MessageDisplayer.INVALID_SORT_DIRECTION);
        }

        expenses.sort(comparator);
        String sortedExpenses = reporter.listExpenseBuilder(expenses);
        String message = String.format(MessageDisplayer.SORT_SUCCESS_MESSAGE_TEMPLATE, sortBy, sortDir, sortedExpenses);
        return new CommandResult(true, message);
    }
}
