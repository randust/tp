//@@author Charly2312
package fintrek.command;

import fintrek.misc.MessageDisplayer;

@CommandInfo(
        description = """
            Format: /help [COMMAND]
            Displays help message for all commands. Optionally pass a keyword to show usage for a specific command.
            """
)
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
                return new CommandResult(false, message);
            }
        } else {
            message = CommandRegistry.getAllCommandDescriptions();
        }
        return new CommandResult(true, message);
    }
}
