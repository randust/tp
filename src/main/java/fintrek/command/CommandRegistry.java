package fintrek.command;

import java.util.HashMap;
import java.util.Map;

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
}
