package fintrek.command.sort;

import fintrek.command.Command;
import fintrek.command.registry.CommandInfo;
import fintrek.command.registry.CommandResult;
import fintrek.expense.core.Expense;
import fintrek.misc.MessageDisplayer;
import fintrek.parser.CommandParser;
import fintrek.parser.ParseResult;
import fintrek.parser.ListSortArgumentParser;

import java.util.Comparator;
import java.util.List;

@CommandInfo(
        recurringFormat = "Format: /list-sort-recurring <SORT FIELD> <SORT DIRECTION>",
        regularFormat = "Format: /list-sort <SORT FIELD> <SORT DIRECTION>",
        description = """
                SORT FIELD valid inputs: name, amount, category, date
                SORT DIRECTION valid inputs: asc, dsc
                """,
        recurringExample = "Example: /list-sort-recurring name asc - " +
                "prints sorted recurring list in ascending alphabetical order.",
        regularExample = "Example: /list-sort name asc - " +
                "prints regular sorted list in ascending alphabetical order."
)

public class ListSortCommand extends Command {

    //@@author szeyingg
    private static final Comparator<Expense> AMOUNT_ASC = Comparator.comparingDouble(Expense::getAmount);
    private static final Comparator<Expense> CATEGORY_ASC = Comparator.comparing(Expense::getCategory);
    private static final Comparator<Expense> DATE_ASC = Comparator.comparing(Expense::getDate);
    private static final Comparator<Expense> NAME_ASC =
            Comparator.comparing(expense -> expense.getDescription().toLowerCase());

    private final ListSortArgumentParser parser = new ListSortArgumentParser();

    public ListSortCommand(boolean isRecurring) {
        super(isRecurring);
    }

    @Override
    public CommandParser<?> getParser() {
        return parser;
    }

    @Override
    public boolean supportsStructuredParsing() {
        return true;
    }

    /**
     * This function will sort general or recurring expenses
     *      based on the type and direction the user wants
     * @param arguments has important inputs which are how is it sorted by (e.g. category)
     *                  and direction either ascending or descending
     * @return a {@code CommandResult} object telling whether the
     *      execution is successful or not, and an error/success message
     */
    @Override
    public CommandResult execute(String arguments) {
        ParseResult<ListSortParseResult> result = parser.parse(arguments);
        if (!result.isSuccess()) {
            return new CommandResult(false, result.getError());
        }
        ListSortParseResult args = result.getResult();

        String sortBy = args.sortBy();
        String sortDir = args.sortDir();

        List<Expense> expenses = service.getAllExpenses();
        if (expenses.isEmpty()) {
            return new CommandResult(false, MessageDisplayer.EMPTY_LIST_MESSAGE);
        }

        Comparator<Expense> comparator = getComparator(sortBy);
        if (comparator == null) {
            return new CommandResult(false, MessageDisplayer.INVALID_SORT_FIELD);
        }

        comparator = setDirection(sortDir, comparator);
        if (comparator == null) {
            return new CommandResult(false, MessageDisplayer.INVALID_SORT_DIRECTION);
        }

        return getCommandResult(expenses, comparator, sortBy, sortDir, isRecurringExpense);
    }

    /**
     * The function sets the direction of the comparator
     *      which will be used to sort the expenses
     * @param sortDir is the direction to sort the list
     * @param comparator to compare the expenses in the list
     * @return the comparator if it is reversed or not
     */
    //@@Charly2312
    private static Comparator<Expense> setDirection(String sortDir, Comparator<Expense> comparator) {
        if (sortDir.equals("DSC")) {
            return comparator.reversed();
        } else if (!sortDir.equals("ASC")) {
            return null;
        }
        return comparator;
    }

    /**
     * This function returns the CommandResult after validating all the inputs
     * @param expenses the list to be sorted
     * @param comparator the comparator used for the list
     * @param sortBy the type on how the expenses are sorted
     * @param sortDir the direction on how the expenses are sorted
     * @return a {@code CommandResult} object telling whether the
     *      execution is successful or not, and an error/success message
     */
    //@@Charly2312
    private CommandResult getCommandResult(List<Expense> expenses, Comparator<Expense> comparator,
                                           String sortBy, String sortDir, boolean isRecurringExpense) {
        expenses.sort(comparator);
        String sortedExpenses = reporter.listExpenseBuilder(expenses);
        String message = (isRecurringExpense)?
                String.format(MessageDisplayer.SORT_RECUR_SUCCESS_MESSAGE_TEMPLATE, sortBy, sortDir, sortedExpenses):
                String.format(MessageDisplayer.SORT_SUCCESS_MESSAGE_TEMPLATE, sortBy, sortDir, sortedExpenses);
        return new CommandResult(true, message);
    }

    /**
     * This function sets the type of the comparator
     * @param sortBy is how the expenses in the list is sorted by
     * @return the type of comparator to be used
     */
    //@@Charly2312
    private Comparator<Expense> getComparator (String sortBy) {
        return switch (sortBy) {
        case "NAME" -> NAME_ASC;
        case "AMOUNT" -> AMOUNT_ASC;
        case "CATEGORY" -> CATEGORY_ASC;
        case "DATE" -> DATE_ASC;
        default -> null;
        };
    }
}
