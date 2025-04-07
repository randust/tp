package fintrek.util;

import fintrek.command.Command;
import fintrek.command.registry.CommandResult;

/**
 * Utility Class to test command registration and execution.
 */
public class TestCommandUtils {

    /**
     * A fake implementation of abstract {@code Command} class for testing.
     */
    public static class FakeCommand extends Command {
        private final String name;

        /**
         * Constructs a new {@code FakeCommand}
         * @param name Name of the fake command
         * @param isRecurring Whether this fake command is recurring or not
         */
        public FakeCommand(String name, boolean isRecurring) {
            super(isRecurring);
            this.name = name;
        }

        /**
         * Simulates execution of the command.
         * @param arguments Argument passed to the command
         * @return a {@code CommandResult} indicating success and containing a
         *         message with the command name and arguments.
         */
        @Override
        public CommandResult execute(String arguments) {
            return new CommandResult(true, name + " executed with args: " + arguments);
        }
    }

    /**
     * Registers a list of fake commands that are non-recurring into {@code TestCommandRegistry}.
     * Each command is added as an instance of {@code FakeCommand}.
     * @param commandNames Array of command names to register
     */
    public static void registerFakeCommands(String... commandNames) {
        for (String name : commandNames) {
            TestCommandRegistry.register(name, new FakeCommand(name, false));
        }
    }
}
