package fintrek.command.edit;

import fintrek.command.Command;
import fintrek.command.registry.CommandInfo;
import fintrek.command.registry.CommandResult;
import fintrek.expense.core.Expense;
import fintrek.misc.MessageDisplayer;
import fintrek.parser.CommandParser;
import fintrek.parser.EditArgumentParser;
import fintrek.parser.ParseResult;

import static fintrek.util.InputValidator.isInValidIntRange;

@CommandInfo(
        description = """
                Format: /edit [INDEX] [/d DESCRIPTION] [/$ AMOUNT] [/c CATEGORY]
                Example: /edit 2 /d dinner /$ 25 /c Dining
                """
)
public class EditCommand extends Command {
    private static final int INDEX_LOWER_BOUND = 0;
    private static final String SUCCESS_MESSAGE_FORMAT = "Expense at index %d updated successfully:\n%s";

    private final EditArgumentParser parser = new EditArgumentParser();

    public EditCommand(boolean isRecurring) {
        super(isRecurring);
    }

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
        int index = args.zeroBaseIndex();

        if (!isInValidIntRange(index, INDEX_LOWER_BOUND, service.countExpenses() - 1)) {
            return new CommandResult(false, MessageDisplayer.IDX_OUT_OF_BOUND_MESSAGE);
        }

        Expense original = service.getExpense(index);
        Expense updated = buildUpdatedExpense(original, args.descriptor());

        service.popExpense(index);
        service.insertExpenseAt(index, updated);

        return new CommandResult(
                true,
                String.format(SUCCESS_MESSAGE_FORMAT, index + 1, updated)
        );
    }

    private Expense buildUpdatedExpense(Expense original, EditExpenseDescriptor descriptor) {
        String description = descriptor.getDescription() != null
                ? descriptor.getDescription() : original.getDescription();

        double amount = descriptor.getAmount() != null
                ? descriptor.getAmount() : original.getAmount();

        String category = descriptor.getCategory() != null
                ? descriptor.getCategory() : original.getCategory();

        Expense updated = new Expense(description, amount, category);
        updated.updateDate(original.getDate());
        return updated;
    }
}
