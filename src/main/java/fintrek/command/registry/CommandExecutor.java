package fintrek.command.registry;

import fintrek.command.Command;
import fintrek.misc.MessageDisplayer;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Dispatches parsed user commands to the appropriate handlers.
 *
 * <p>This class is responsible for executing user commands by name,
 * looking them up in the {@link CommandRegistry}, and invoking their logic
 * with the provided arguments.</p>
 *
 * <p>If the command name is unrecognized, it returns an appropriate error message.
 * Execution logs are recorded via {@link java.util.logging.Logger}.</p>
 */
public class CommandExecutor {
    private static final Logger logger = Logger.getLogger(CommandExecutor.class.getName());

    /**
     * Dispatches the command to the appropriate handler based on its name.
     *
     * @param commandName the name of the command (e.g. "add", "delete", "edit")
     * @param arguments the arguments passed to the command
     * @return a {@link CommandResult} indicating the success or failure of the command
     */
    public static CommandResult dispatch(String commandName, String arguments) {
        if (!CommandRegistry.hasCommand(commandName)) {
            return new CommandResult(false, MessageDisplayer.INVALID_COMMAND_MESSAGE);
        }

        Command command = CommandRegistry.getCommand(commandName);
        logger.log(Level.FINE, MessageDisplayer.EXECUTING_COMMAND_MESSAGE + commandName);
        return command.execute(arguments);
    }
}
