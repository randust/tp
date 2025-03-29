package fintrek.parser;

import fintrek.command.Command;
import fintrek.command.CommandRegistry;
import fintrek.command.CommandResult;
import fintrek.misc.MessageDisplayer;

import java.util.logging.Logger;

/**
 * The {@code Parser} class is responsible for interpreting user input and executing the corresponding commands.
 * It validates the input format, checks for known commands, and ensures that required arguments are provided.
 */
public class Parser {
    private static final Logger logger = Logger.getLogger(Parser.class.getName());

    /**
     * Parses the user input and executes the corresponding command if valid.
     *
     * @param userInput The raw user input string.
     * @return A {@code ParseResult} object indicating whether the command execution was successful
     *         and containing an error message if applicable.
     */
    public static ParseResult parseUserInput(String userInput) {
        assert userInput != null : MessageDisplayer.INVALID_COMMAND_MESSAGE;
        String[] tokens = userInput.split("\\s+", 2); // [command, arguments]
        String commandStr = tokens[0];

        if (!commandStr.startsWith("/")) {
            return new ParseResult(false, MessageDisplayer.NO_COMMAND_MESSAGE);
        }

        String commandStrWithoutLeadingSlash = commandStr.substring(1);
        if (!CommandRegistry.hasCommand(commandStrWithoutLeadingSlash)) {
            return new ParseResult(false, MessageDisplayer.INVALID_COMMAND_MESSAGE);
        }

        Command command = CommandRegistry.getCommand(commandStrWithoutLeadingSlash);

        String arguments = (tokens.length >= 2) ? tokens[1] : null;

        logger.info("Executing command: " + commandStrWithoutLeadingSlash);
        CommandResult result = command.execute(arguments);
        System.out.println(result.message());

        return new ParseResult(true, null);
    }
}
