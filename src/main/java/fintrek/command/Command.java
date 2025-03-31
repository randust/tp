package fintrek.command;

import fintrek.command.registry.CommandInfo;
import fintrek.command.registry.CommandResult;

public abstract class Command {
    public abstract CommandResult execute(String arguments);

    public String getDescription() {
        CommandInfo info = this.getClass().getAnnotation(CommandInfo.class);
        return info != null ? info.description() : "No description available.";
    }
}
