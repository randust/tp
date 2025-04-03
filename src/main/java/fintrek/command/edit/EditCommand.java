package fintrek.command.edit;

import fintrek.command.Command;
import fintrek.command.registry.CommandInfo;
import fintrek.command.registry.CommandResult;
import fintrek.expense.core.Expense;
import fintrek.misc.MessageDisplayer;
import fintrek.parser.CommandParser;
import fintrek.parser.EditArgumentParser;
import fintrek.parser.ParseResult;

import java.time.LocalDate;

import static fintrek.util.InputValidator.isInValidIntRange;
/**
 * Handles the editing of an existing expense entry.
 *
 * <p>This command allows partial or full updates to an expense's description, amount,
 * category, and date using optional flags. It parses the input using
 * {@link EditArgumentParser}, performs index validation, and updates the specified
 * expense in-place within the expense list.</p>
 *
 * <p>Example usage:
 * <pre>
 * /edit 2 /d dinner /$ 25 /c Dining /dt 25-12-2024
 * </pre>
 * Edits the expense at index 2 with the provided updates.</p>
 *
 * <p>Supports both normal and recurring expenses depending on the constructor flag {@code isRecurring}.</p>
 *
 * @see Command
 * @see EditArgumentParser
 * @see fintrek.expense.core.Expense
 */
@CommandInfo(
        recurringFormat =
                "Format: /edit-recurring <INDEX> [/d <DESCRIPTION>] [/$ <AMOUNT>] [/c <CATEGORY>] [/dt <DATE>]",
        regularFormat = "Format: /edit <INDEX> [/d <DESCRIPTION>] [/$ <AMOUNT>] [/c <CATEGORY>] [/dt <DATE>]",
        description = """
                Example: /edit 2 /d dinner /$ 25 /c Dining /dt 25-12-2024
                """
)
public class EditCommand extends Command {
    private static final int INDEX_LOWER_BOUND = 0;

    private final EditArgumentParser parser = new EditArgumentParser();
    /**
     * Constructs an EditCommand.
     *
     * @param isRecurring true if this command operates on recurring expenses, false otherwise
     */
    public EditCommand(boolean isRecurring) {
        super(isRecurring);
    }
    /**
     * Indicates that this command supports structured parsing (via {@link EditArgumentParser}).
     *
     * @return true always, since EditCommand uses structured parsing
     */
    @Override
    public boolean supportsStructuredParsing() {
        return true;
    }

    /**
     * Returns the structured parser used by this command.
     *
     * @return an instance of {@link EditArgumentParser}
     */
    @Override
    public CommandParser<?> getParser() {
        return parser;
    }

    /**
     * Executes the edit operation on an existing expense.
     *
     * @param arguments the user input string containing the index and optional update fields
     * @return a CommandResult indicating success or failure with a message
     */
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
                String.format(MessageDisplayer.EDIT_SUCCESS_MESSAGE_FORMAT, index + 1, updated)
        );
    }

    /**
     * Builds a new Expense object using the original values and the provided descriptor fields.
     *
     * <p>Fields in the descriptor that are null will retain the original values.</p>
     *
     * @param original the original Expense to update
     * @param descriptor an EditExpenseDescriptor containing updated fields (nullable)
     * @return a new Expense instance with updated fields
     */
    private Expense buildUpdatedExpense(Expense original, EditExpenseDescriptor descriptor) {
        String description = descriptor.getDescription() != null
                ? descriptor.getDescription() : original.getDescription();

        double amount = descriptor.getAmount() != null
                ? descriptor.getAmount() : original.getAmount();

        String category = descriptor.getCategory() != null
                ? descriptor.getCategory() : original.getCategory();

        LocalDate date = descriptor.getDate() != null
                ? descriptor.getDate() : original.getDate();

        return new Expense(description, amount, category, date);
    }

}
