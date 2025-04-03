package fintrek.command.registry;

import fintrek.command.Command;
import fintrek.misc.MessageDisplayer;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

/**
 * Central registry for all available commands in the system.
 *
 * <p>This class maintains a mapping of command names (strings) to their corresponding
 * {@link Command} instances. It provides utilities for retrieving, checking, and listing
 * registered commands. All commands are registered via {@link CommandRegistrar} on initialization.</p>
 */
public class CommandRegistry {
    private static final Map<String, Command> commands = CommandRegistrar.registerAll();

    /**
     * Retrieves the {@link Command} instance associated with the given name.
     *
     * @param name the name of the command (case-insensitive)
     * @return the corresponding {@link Command}, or {@code null} if not found
     * @throws AssertionError if the provided name is {@code null}
     */
    public static Command getCommand(String name) {
        assert name != null : String.format(MessageDisplayer.CANNOT_BE_NULL_MESSAGE_TEMPLATE, "Command name");
        return commands.get(name.toLowerCase());
    }

    /**
     * Checks whether a command with the given name is registered.
     *
     * @param name the name of the command to check (case-insensitive)
     * @return true if the command exists, false otherwise
     * @throws AssertionError if the provided name is {@code null}
     */
    public static boolean hasCommand(String name) {
        assert name != null : String.format(MessageDisplayer.CANNOT_BE_NULL_MESSAGE_TEMPLATE, "Command name");
        return commands.containsKey(name.toLowerCase());
    }

    /**
     * Returns a formatted string containing all registered command names and their descriptions.
     *
     * <p>This is used primarily for generating help text.</p>
     *
     * @return a newline-separated list of command descriptions
     */
    public static String getAllCommandDescriptions() {
        List<String> descriptions = new ArrayList<>();
        for (Map.Entry<String, Command> entry : commands.entrySet()) {
            descriptions.add("/" + entry.getKey() + ": " + entry.getValue().getDescription());
        }
        descriptions.sort(String::compareTo);
        return String.join("\n", descriptions);
    }
}
