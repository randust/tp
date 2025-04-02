//@@author Charly2312
package fintrek.command.help;

import fintrek.command.Command;
import fintrek.command.registry.CommandInfo;
import fintrek.command.registry.CommandRegistry;
import fintrek.command.registry.CommandResult;
import fintrek.misc.MessageDisplayer;

@CommandInfo(
        description = """
            Format: /help <COMMAND>
            Displays help message for all commands. Optionally pass a keyword to show usage for a specific command.
            """
)
public class HelpCommand extends Command {

    public HelpCommand(boolean isRecurring) {
        super(isRecurring);
    }

    @Override
    public CommandResult execute(String arguments) {
        String message;
        if (arguments != null && !arguments.isBlank()) {
            String keyword = arguments.toLowerCase();
            if (keyword.contains("add")) {
                message = CommandRegistry.getCommand("add").getDescription();
            } else if (keyword.contains("budget")) {
                message = CommandRegistry.getCommand("budget").getDescription();
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
