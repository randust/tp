package fintrek.command;

import fintrek.expense.service.AppServices;
import fintrek.command.registry.CommandInfo;
import fintrek.command.registry.CommandResult;
import fintrek.expense.service.ExpenseReporter;
import fintrek.expense.service.ExpenseService;
import fintrek.misc.MessageDisplayer;
import fintrek.parser.CommandParser;

/**
 * Abstract base class for all user commands.
 *
 * <p>Each {@code Command} subclass represents a specific action the user can take,
 * such as adding, editing, or deleting expenses. Commands may operate on either
 * regular or recurring expenses, which determines the backing service and reporter used.</p>
 *
 * <p>Commands can support either free-form string execution or structured parsing
 * via {@link CommandParser}. Subclasses may override the relevant methods to support
 * the intended execution style.</p>
 *
 * @see fintrek.command.registry.CommandInfo
 * @see fintrek.command.registry.CommandResult
 */
public abstract class Command {
    /**
     * The service layer used to manipulate expense data (regular or recurring).
     */
    protected final ExpenseService service;

    /**
     * The reporter used to generate summaries, totals, and other views.
     */
    protected final ExpenseReporter reporter;

    /**
     * The reporter used to generate summaries, totals, and other views.
     */
    protected final boolean isRecurringExpense;

    /**
     * Constructs a command and assigns the appropriate service and reporter based on recurrence mode.
     *
     * @param isRecurring whether the command operates on recurring expenses
     */
    public Command(boolean isRecurring) {
        if (isRecurring) {
            this.service = AppServices.RECURRING_SERVICE;
            this.reporter = AppServices.RECURRING_REPORTER;
        } else {
            this.service = AppServices.REGULAR_SERVICE;
            this.reporter = AppServices.REGULAR_REPORTER;
        }
        this.isRecurringExpense = isRecurring;
    }

    /**
     * Executes the command with the given raw string arguments.
     *
     * <p>This method should be overridden by subclasses that support string-based execution.</p>
     *
     * @param arguments raw user input passed to the command
     * @return a {@link CommandResult} representing the outcome
     * @throws UnsupportedOperationException if not implemented
     */
    public CommandResult execute(String arguments) {
        throw new UnsupportedOperationException(MessageDisplayer.REQUIRES_STRUCTURED_PARSER_MESSAGE);
    }

    /**
     * Executes the command with parsed arguments for structured parsing.
     *
     * <p>Override this method in commands that support structured execution via a {@link CommandParser}.</p>
     *
     * @param parsedArgs parsed object representing the command input
     * @return a {@link CommandResult} representing the outcome
     * @throws UnsupportedOperationException if not implemented
     */
    public CommandResult executeStructured(Object parsedArgs) {
        throw new UnsupportedOperationException(MessageDisplayer.STRUCTURED_EXECUTION_NOT_SUPPORTED_MESSAGE);
    }

    /**
     * Indicates whether this command supports structured parsing.
     *
     * @return true if structured parsing is supported, false otherwise
     */
    public boolean supportsStructuredParsing() {
        return false;
    }

    /**
     * Returns the parser associated with this command, if structured parsing is supported.
     *
     * @return the {@link CommandParser}, or {@code null} if unsupported
     */
    public CommandParser<?> getParser() {
        return null;
    }

    /**
     * Retrieves the command's description from its {@link CommandInfo} annotation.
     *
     * @return the command's usage description or a default message if not annotated
     */
    public String getDescription() {
        CommandInfo info = this.getClass().getAnnotation(CommandInfo.class);
        String format;
        String example;
        if (this.isRecurringExpense) {
            format = info.recurringFormat();
            example = info.recurringExample();
        } else {
            format = info.regularFormat();
            example = info.regularExample();
        }
        String fullDescription = format + "\n" + info.description() + example;
        return info != null ? fullDescription : MessageDisplayer.NO_DESCRIPTION_AVAILABLE_MESSAGE;
    }
}
