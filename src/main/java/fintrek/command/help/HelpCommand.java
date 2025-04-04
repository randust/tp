//@@author Charly2312
package fintrek.command.help;

import java.util.Arrays;
import java.util.List;

import fintrek.command.Command;
import fintrek.command.registry.CommandInfo;
import fintrek.command.registry.CommandRegistry;
import fintrek.command.registry.CommandResult;
import fintrek.misc.MessageDisplayer;
import fintrek.util.InputValidator;

@CommandInfo(
        recurringFormat = "Format: /help [COMMAND]",
        regularFormat = "Format: /help [COMMAND]",
        description = """
            Displays help message for all commands.
            Optionally pass a keyword to show usage for a specific command.
            """
)

//@@author venicephua
public class HelpCommand extends Command {
    // List of base commands
    private static final List<String> COMMANDS = Arrays.asList(
            "add", "delete", "edit", "list", "total", "average", "summary", "sort", "budget", "help"
    );

    public HelpCommand(boolean isRecurring) {
        super(isRecurring);
    }

    @Override
    public CommandResult execute(String arguments) {
        String message;
        if (InputValidator.isNullOrBlank(arguments)) {
            message = CommandRegistry.getAllCommandDescriptions();
            return new CommandResult(true, message);
        }
        String keyword = arguments.toLowerCase();
        boolean isRecurring = keyword.contains("recurring");
        for (String cmd : COMMANDS) {
            if (keyword.contains(cmd)) {
                message = (isRecurring) ? CommandRegistry.getCommand(cmd + "-recurring").getDescription() :
                        CommandRegistry.getCommand(cmd).getDescription();
                return new CommandResult(true, message);
            }
        }
        message = MessageDisplayer.HELP_UNKNOWN_TOPIC;
        return new CommandResult(false, message);
    }
}
