package fintrek.command.edit;

import fintrek.expense.core.Expense;
import fintrek.expense.ExpenseManager;
import fintrek.command.Command;
import fintrek.command.registry.CommandInfo;
import fintrek.command.registry.CommandResult;
import fintrek.misc.MessageDisplayer;
import fintrek.parser.CommandParser;
import fintrek.parser.EditArgumentParser;
import fintrek.parser.ParseResult;

@CommandInfo(
        description = """
                Format: /edit [INDEX] [/d DESCRIPTION] [/$ AMOUNT] [/c CATEGORY]
                Example: /edit 2 /d dinner /$ 25 /c Dining
                """
)
public class EditCommand extends Command {
    private final EditArgumentParser parser = new EditArgumentParser();

    @Override
    public boolean supportsStructuredParsing() {
        return true;
    }

    @Override
    public CommandParser<?> getParser() {
        return parser;
    }

    @Override
    public CommandResult execute(String arguments) {
        ParseResult<EditParseResult> result = parser.parse(arguments);

        if (!result.isSuccess()) {
            return new CommandResult(false, result.getError());
        }

        EditParseResult args = result.getResult();
        int index = args.index();

        if (index < 0 || index >= ExpenseManager.getLength()) {
            return new CommandResult(false, MessageDisplayer.IDX_OUT_OF_BOUND_MESSAGE);
        }

        Expense original = ExpenseManager.getExpense(index);
        Expense updated = buildUpdatedExpense(original, args.descriptor());

        ExpenseManager.popExpense(index);
        ExpenseManager.insertExpenseAt(index, updated);

        return new CommandResult(
                true,
                String.format("Expense at index %d updated successfully:\n%s", index + 1, updated)
        );
    }

    private Expense buildUpdatedExpense(Expense original, EditExpenseDescriptor descriptor) {
        String description = descriptor.getDescription() != null
                ? descriptor.getDescription() : original.getDescription();

        double amount = descriptor.getAmount() != null
                ? descriptor.getAmount() : original.getAmount();

        String category = descriptor.getCategory() != null
                ? descriptor.getCategory() : original.getCategory();

        return new Expense(description, amount, category);
    }
}
