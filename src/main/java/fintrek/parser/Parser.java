package fintrek.parser;

import fintrek.FinTrek;
import fintrek.command.Command;
import fintrek.misc.MessageDisplayer;
import fintrek.command.ExecutionResult;

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
        // Split input into command and arguments
        String[] tokens = userInput.split("\\s+", 2); // split into [ACTION, ARGUMENTS]
        String commandStr = tokens[0];

        // Ensure command starts with '/'
        if (!commandStr.startsWith("/")) {
            return new ParseResult(false, MessageDisplayer.NO_COMMAND_MESSAGE);
        }

        try {
            // Remove leading '/' from command string
            commandStr = commandStr.substring(1);
            Command command = Command.valueOf(commandStr.toUpperCase());

            // Determine if arguments are present
            String arguments;
            boolean argIsEmpty = (tokens.length < 2 || tokens[1].isEmpty());

            if (!command.acceptEmptyArg && argIsEmpty) {
                return new ParseResult(false, String.format(MessageDisplayer.ARG_EMPTY_MESSAGE_TEMPLATE, commandStr));
            } else if (command.acceptEmptyArg && argIsEmpty) {
                arguments = null;
            } else {
                arguments = tokens[1];
            }

            // Execute the command with the parsed arguments
            logger.info("Parsing successful.");
            ExecutionResult result = command.execute(arguments);
            System.out.println(result.message());
        } catch (IllegalArgumentException e) {
            return new ParseResult(false, MessageDisplayer.INVALID_COMMAND_MESSAGE);
        }

        return new ParseResult(true, null);
    }
}
