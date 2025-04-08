package fintrek.parser;

import fintrek.command.registry.CommandExecutor;
import fintrek.command.registry.CommandResult;
import fintrek.misc.MessageDisplayer;

import static fintrek.util.InputValidator.isNullOrBlank;

public class CommandRouter {

    /**
     * The function is to route the arguments onto the right command
     *                  based on the command extracted from the input
     * @param userInput contains the command and argument which will
     *                  be routed to the parser and respective commands
     * @return a {@code RouteResult} showing whether it is successful
     *                  and the result message, failure / success
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

        try {
            CommandResult result = CommandExecutor.dispatch(commandName, arguments);
            return new RouteResult(result.isSuccess(), result.message());
        } catch (Exception e) {
            return new RouteResult(false, MessageDisplayer.UNEXPECTED_ERROR_WHILE_EXECUTING_COMMAND);
        }
    }
}
