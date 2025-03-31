package fintrek.command;

import fintrek.command.registry.CommandInfo;
import fintrek.command.registry.CommandResult;
import fintrek.parser.CommandParser;

public abstract class Command {
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
