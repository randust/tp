package fintrek.parser;

import fintrek.command.Command;
import fintrek.misc.DisplayMessage;
import fintrek.command.ExecutionResult;
/**
 * The {@code Parser} class is responsible for interpreting user input and executing the corresponding commands.
 * It validates the input format, checks for known commands, and ensures that required arguments are provided.
 */
public class Parser {

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
            return new ParseResult(false, DisplayMessage.NO_COMMAND_MESSAGE);
        }

        try {
            // Remove leading '/' from command string
            commandStr = commandStr.substring(1);
            Command command = Command.valueOf(commandStr.toUpperCase());

            // Determine if arguments are present
            String arguments;
            boolean argIsEmpty = (tokens.length < 2 || tokens[1].isEmpty());

            if (!command.acceptEmptyArg && argIsEmpty) {
                return new ParseResult(false, String.format(DisplayMessage.ARG_EMPTY_MESSAGE_TEMPLATE, commandStr));
            } else if (command.acceptEmptyArg && argIsEmpty) {
                arguments = null;
            } else {
                arguments = tokens[1];
            }

            // Execute the command with the parsed arguments
            ExecutionResult result = command.execute(arguments);
            System.out.println(result.message());
        } catch (IllegalArgumentException e) {
            return new ParseResult(false, DisplayMessage.INVALID_COMMAND_MESSAGE);
        }

        return new ParseResult(true, null);
    }
}
