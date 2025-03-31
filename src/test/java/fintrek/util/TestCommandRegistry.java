package fintrek.util;

import fintrek.command.Command;
import fintrek.command.registry.CommandRegistry;

/**
 * Utility class to register and clear commands during testing.
 * Not included in production builds.
 */
public class TestCommandRegistry {

    /**
     * Registers a command into the CommandRegistry for testing.
     * @param name Command name (e.g., "add")
     * @param command A Command instance
     */
    public static void register(String name, Command command) {
        // Unsafe but okay in testing: reflection access to the private static map
        try {
            var field = CommandRegistry.class.getDeclaredField("commands");
            field.setAccessible(true);
            @SuppressWarnings("unchecked")
            var map = (java.util.Map<String, Command>) field.get(null);
            map.put(name.toLowerCase(), command);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to inject test command", e);
        }
    }

    /**
     * Clears all commands from the registry. Use cautiously.
     */
    public static void clear() {
        try {
            var field = CommandRegistry.class.getDeclaredField("commands");
            field.setAccessible(true);
            @SuppressWarnings("unchecked")
            var map = (java.util.Map<String, Command>) field.get(null);
            map.clear();
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to clear commands", e);
        }
    }
}
