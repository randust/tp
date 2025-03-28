package fintrek.command;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class CommandRegistry {
    private static final Map<String, Command> commands = new HashMap<>();

    static {
        commands.put("add", new AddCommand());
        commands.put("delete", new DeleteCommand());
        commands.put("list", new ListCommand());
        commands.put("total", new TotalCommand());
        commands.put("average", new AverageCommand());
        commands.put("help", new HelpCommand());
    }

    public static Command getCommand(String name) {
        return commands.get(name.toLowerCase());
    }

    public static boolean hasCommand(String name) {
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
