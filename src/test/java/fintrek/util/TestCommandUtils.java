package fintrek.util;

import fintrek.command.Command;
import fintrek.command.registry.CommandResult;

public class TestCommandUtils {

    public static class FakeCommand extends Command {
        private final String name;

        public FakeCommand(String name) {
            this.name = name;
        }

        @Override
        public CommandResult execute(String arguments) {
            return new CommandResult(true, name + " executed with args: " + arguments);
        }
    }

    public static void registerFakeCommands(String... commandNames) {
        for (String name : commandNames) {
            TestCommandRegistry.register(name, new FakeCommand(name));
        }
    }

    public static void clearCommands() {
        TestCommandRegistry.clear();
    }
}
