package fintrek.command;

import fintrek.AppServices;
import fintrek.command.registry.CommandInfo;
import fintrek.command.registry.CommandResult;
import fintrek.expense.service.ExpenseReporter;
import fintrek.expense.service.ExpenseService;
import fintrek.parser.CommandParser;

public abstract class Command {
    protected final ExpenseService service;
    protected final ExpenseReporter reporter;

    public Command(boolean isRecurring) {
        if (isRecurring) {
            this.service = AppServices.RECURRING_SERVICE;
            this.reporter = AppServices.RECURRING_REPORTER;
        } else {
            this.service = AppServices.REGULAR_SERVICE;
            this.reporter = AppServices.REGULAR_REPORTER;
        }
    }

    public CommandResult execute(String arguments) {
        throw new UnsupportedOperationException("This command requires a structured parser.");
    }

    public CommandResult executeStructured(Object parsedArgs) {
        throw new UnsupportedOperationException("Structured execution not supported.");
    }

    public boolean supportsStructuredParsing() {
        return false;
    }

    public CommandParser<?> getParser() {
        return null;
    }

    public String getDescription() {
        CommandInfo info = this.getClass().getAnnotation(CommandInfo.class);
        return info != null ? info.description() : "No description available.";
    }
}
