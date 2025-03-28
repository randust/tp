//@@author Charly2312
package fintrek.command;

import fintrek.misc.MessageDisplayer;

public class HelpCommand extends Command {

    @Override
    public CommandResult execute(String arguments) {
        String message;
        if (arguments != null && !arguments.isBlank()) {
            String keyword = arguments.toLowerCase();
            if (keyword.contains("add")) {
                message = CommandRegistry.getCommand("add").getDescription();
            } else if (keyword.contains("delete")) {
                message = CommandRegistry.getCommand("delete").getDescription();
            } else if (keyword.contains("total")) {
                message = CommandRegistry.getCommand("total").getDescription();
            } else if (keyword.contains("average")) {
                message = CommandRegistry.getCommand("average").getDescription();
            } else if (keyword.contains("summary")) {
                message = CommandRegistry.getCommand("summary").getDescription();
            } else {
                message = MessageDisplayer.HELP_UNKNOWN_TOPIC;
            }
        } else {
            message = CommandRegistry.getAllCommandDescriptions();
        }
        return new CommandResult(true, message);
    }

    @Override
    public String getDescription() {
        return "Provides help information. Optionally pass a keyword: /help [command]";
    }
}
