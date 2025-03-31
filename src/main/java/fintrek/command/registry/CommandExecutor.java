package fintrek.command.registry;


import fintrek.command.Command;
import fintrek.misc.MessageDisplayer;

import java.util.logging.Logger;

/**
 * Dispatches parsed user commands to the appropriate handlers.
 */
public class CommandExecutor {
    private static final Logger logger = Logger.getLogger(CommandExecutor.class.getName());

    public static CommandResult dispatch(String commandName, String arguments) {
        if (!CommandRegistry.hasCommand(commandName)) {
            return new CommandResult(false, MessageDisplayer.INVALID_COMMAND_MESSAGE);
        }

        Command command = CommandRegistry.getCommand(commandName);
        logger.info("Executing command: " + commandName);
        return command.execute(arguments);
    }
}
