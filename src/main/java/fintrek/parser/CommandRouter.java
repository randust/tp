package fintrek.parser;

import fintrek.command.registry.CommandExecutor;
import fintrek.command.registry.CommandResult;
import fintrek.misc.MessageDisplayer;

import java.util.logging.Logger;

import static fintrek.utils.InputValidator.isNullOrBlank;

/**
 * The {@code Parser} class is responsible for interpreting user input and executing the corresponding commands.
 * It validates the input format, checks for known commands, and ensures that required arguments are provided.
 */
public class CommandRouter {
    private static final Logger logger = Logger.getLogger(CommandRouter.class.getName());

    /**
     * Parses the user input and executes the corresponding command if valid.
     *
     * @param userInput The raw user input string.
     * @return A {@code ParseResult} object indicating whether the command execution was successful
     *         and containing an error message if applicable.
     */
    public static RouteResult routeUserInput(String userInput) {
        if (isNullOrBlank(userInput)) {
            return new RouteResult(false, MessageDisplayer.NO_COMMAND_MESSAGE);
        }

        String[] tokens = userInput.trim().split("\\s+", 2); // [command, arguments]
        String commandToken = tokens[0];

        if (!commandToken.startsWith("/")) {
            return new RouteResult(false, MessageDisplayer.NO_COMMAND_MESSAGE);
        }

        String commandName = commandToken.substring(1);
        String arguments = (tokens.length > 1) ? tokens[1] : null;

        CommandResult result = CommandExecutor.dispatch(commandName, arguments);

        return new RouteResult(result.isSuccess(), result.isSuccess() ? null : result.message());
    }
}
