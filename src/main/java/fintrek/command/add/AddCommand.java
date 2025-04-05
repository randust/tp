//@@author szeyingg
package fintrek.command.add;

import fintrek.expense.core.Expense;
import fintrek.budget.BudgetManager;
import fintrek.command.Command;
import fintrek.command.registry.CommandInfo;
import fintrek.command.registry.CommandResult;
import fintrek.misc.MessageDisplayer;
import fintrek.parser.CommandParser;
import fintrek.parser.AddArgumentParser;
import fintrek.parser.ParseResult;
import java.time.LocalDate;

@CommandInfo(
        recurringFormat = "Format: /add-recurring <DESCRIPTION> $<AMOUNT> [/c <CATEGORY>] [/dt <DATE>]",
        regularFormat = "Format: /add <DESCRIPTION> $<AMOUNT> [/c <CATEGORY>] [/dt <DATE>]",
        description = """
                AMOUNT must be a positive number greater than 0
                CATEGORY is an optional argument
                DATE is an optional argument which must be in the form dd-MM-yyyy
                Example: /add concert tickets $35.80 /c LEISURE /d [03-05-2025] -
                        adds an expense with description 'concert tickets' with the amount $35.80,
                        with the category 'LEISURE' and date '03-05-2025'.
                """
)

public class AddCommand extends Command {
    private final AddArgumentParser parser = new AddArgumentParser();

    public AddCommand(boolean isRecurring) {
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
     * Adds an expense into the expense list, and also checks for any invalid inputs
     * @param arguments the string containing important parameters pertaining to the expense
     * @return a {@code CommandResult} object telling whether the
     *      execution is successful or not, and an error/success message
     */
    @Override
    public CommandResult execute(String arguments) {
        ParseResult<AddParseResult> result = parser.parse(arguments);
        if (!result.isSuccess()) {
            return new CommandResult(false, result.getError());
        }
        AddParseResult args = result.getResult();

        String description = args.desc();
        double amount = args.amount();
        String category = args.category();
        LocalDate date = args.date();

        Expense newExpense = new Expense(description, amount, category, date);
        service.addExpense(newExpense);
        System.out.println(checkBudgetWarnings(LocalDate.now()));

        return getCommandResult(newExpense);
    }

    /**
     * This function collates all the variables required to create a new expense
     *      It will then return a CommandResult after the process is done, signifying it is succeeded
     * @param newExpense is the Expense object of general or recurring expense to be added
     * @return a {@code CommandResult} once the process is done
     */
    private CommandResult getCommandResult(Expense newExpense) {
        String message = (isRecurringExpense) ?
                String.format(MessageDisplayer.ADD_RECURRING_SUCCESS_MESSAGE_TEMPLATE, newExpense):
                String.format(MessageDisplayer.ADD_SUCCESS_MESSAGE_TEMPLATE, newExpense);
        return new CommandResult(true, message);
    }

    /**
     * This function will generate warnings if the total expenses of the current month
     * almost exceeds or exceeds the current monthly budget. Note that expenses in
     * the previous month will not be accounted for anymore.
     * @param date the date today, which will be used to generate the current year and month
     * @return warnings depending on whether total expenses exceeds or almost exceeds the monthly budget.
     */
    private String checkBudgetWarnings(LocalDate date) {
        BudgetManager budgetManager = BudgetManager.getInstance();
        if (!budgetManager.isBudgetSet()) {
            return ""; // No budget set, no warning needed
        }

        double budget = budgetManager.getBudget();
        double totalExpenses = reporter.getTotalByMonth(date.getYear(), date.getMonthValue());

        if (totalExpenses >= budget) {
            return String.format(MessageDisplayer.EXCEEDED_BUDGET_MESSAGE, budget, totalExpenses-budget);
        } else if (totalExpenses >= budget * 0.9) {
            return String.format(MessageDisplayer.ALMOST_EXCEEDED_BUDGET_MESSAGE, budget-totalExpenses, budget);
        }
        return "";
    }
}
