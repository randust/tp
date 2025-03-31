package fintrek.command.registry;

import fintrek.command.Command;
import fintrek.misc.MessageDisplayer;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class CommandRegistry {
    private static final Map<String, Command> commands = CommandRegistrar.registerAll();

    public static Command getCommand(String name) {
        assert name != null: String.format(MessageDisplayer.CANNOT_BE_NULL_MESSAGE_TEMPLATE, "Command name");
        return commands.get(name.toLowerCase());
    }

    public static boolean hasCommand(String name) {
        assert name != null: String.format(MessageDisplayer.CANNOT_BE_NULL_MESSAGE_TEMPLATE, "Command name");
        return commands.containsKey(name.toLowerCase());
    }

    public static String getAllCommandDescriptions() {
        List<String> descriptions = new ArrayList<>();
        for (Map.Entry<String, Command> entry : commands.entrySet()) {
            descriptions.add("/" + entry.getKey() + ": " + entry.getValue().getDescription());
        }
        return String.join("\n", descriptions);
    }
}
