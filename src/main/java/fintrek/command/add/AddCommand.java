//@@author szeyingg
package fintrek.command.add;

import fintrek.expense.core.Expense;
import fintrek.budget.service.BudgetWarningService;
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
                AMOUNT must be a positive number greater than 0.
                CATEGORY is an optional argument.
                DATE is an optional argument which must be in the form dd-MM-yyyy.
                """,
        recurringExample = """
                Example: /add-recurring concert tickets $35.80 /c LEISURE /d 03-05-2025 -
                         adds a recurring expense with description 'concert tickets' with the amount $35.80,
                         category 'LEISURE' and date '03-05-2025'.""",
        regularExample = """
                Example: /add concert tickets $35.80 /c LEISURE /d 03-05-2025 -
                         adds a regular expense with description 'concert tickets' with the amount $35.80,
                         category 'LEISURE' and date '03-05-2025'."""
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
     * @return a successful {@code CommandResult} object with the success message, or a
     *      failed {@code CommandResult} object with the error message.
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
        return getCommandResult(newExpense);
    }

    /**
     * This function returns a successful command result containing the correct success message
     * for the type of expense, i.e. recurring or regular, that is to be added.
     * @param newExpense is the Expense object of general or recurring expense to be added
     * @return a successful {@code CommandResult} with the appropriate success message for the type
     *      expense.
     */
    private CommandResult getCommandResult(Expense newExpense) {
        String budgetWarning = BudgetWarningService.generateBudgetWarnings(LocalDate.now());
        String successMessage = (isRecurringExpense) ?
                String.format(MessageDisplayer.ADD_RECURRING_SUCCESS_MESSAGE_TEMPLATE, newExpense):
                String.format(MessageDisplayer.ADD_SUCCESS_MESSAGE_TEMPLATE, newExpense);

        if(budgetWarning.equals("")) { // If no budget warning is generated
            return new CommandResult(true, successMessage);
        }
        String message = budgetWarning + "\n" + successMessage;
        return new CommandResult(true, message);
    }
}
