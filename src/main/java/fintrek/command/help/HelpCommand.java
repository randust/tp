//@@author Charly2312
package fintrek.command.help;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import fintrek.command.Command;
import fintrek.command.registry.CommandInfo;
import fintrek.command.registry.CommandRegistry;
import fintrek.command.registry.CommandResult;
import fintrek.misc.MessageDisplayer;

@CommandInfo(
        recurringFormat = "Format: /help [COMMAND]",
        regularFormat = "Format: /help [COMMAND]",
        description = """
            Displays help message for all commands.
            Optionally pass a keyword to show usage for a specific command.""",
        recurringExample = "",
        regularExample = ""
)

//@@author venicephua
public class HelpCommand extends Command {
    // List of commands
    private static final Set<String> COMMANDS = new HashSet<>(Arrays.asList(
            "add", "add-category", "average", "budget", "budget-left", "delete", "edit", "help", "list",
            "list-category", "list-sort", "summary", "total", "add-recurring", "average-recurring",
            "delete-recurring", "edit-recurring", "list-sort-recurring", "list-recurring",
            "summary-recurring", "total-recurring"
    ));

    public HelpCommand(boolean isRecurring) {
        super(isRecurring);
    }

    /**
     * This functions prints out the format for a function based on the argument
     * @param arguments raw user input passed to the command
     * @return a {@code CommandResult} which object telling whether the
     *      execution is successful or not, and an error/success message
     */
    @Override
    public CommandResult execute(String arguments) {
        String message;
        String keyword;
        if (arguments != null) {
            keyword = arguments.trim().toLowerCase();
        } else {
            message = CommandRegistry.getAllCommandDescriptions();
            return new CommandResult(true, message);
        }
        if (COMMANDS.contains(keyword)) {
            message = CommandRegistry.getCommand(keyword).getDescription();
            return new CommandResult(true, message);
        }

        message = MessageDisplayer.HELP_UNKNOWN_TOPIC;
        return new CommandResult(false, message);
    }
}
