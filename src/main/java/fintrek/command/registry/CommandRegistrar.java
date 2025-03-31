package fintrek.command.registry;
import fintrek.command.add.AddCommand;
import fintrek.command.add.AddRecurringCommand;
import fintrek.command.summary.AverageCommand;
import fintrek.command.Command;
import fintrek.command.delete.DeleteRecurringCommand;
import fintrek.command.help.HelpCommand;
import fintrek.command.list.ListCommand;
import fintrek.command.list.ListRecurringCommand;
import fintrek.command.summary.SummaryCommand;
import fintrek.command.summary.TotalCommand;
import fintrek.command.delete.DeleteCommand;

import java.util.Map;
import java.util.HashMap;

public class CommandRegistrar {
    public static Map<String, Command> registerAll() {
        Map<String, Command> commands = new HashMap<>();
        commands.put("add", new AddCommand());
        commands.put("delete", new DeleteCommand());
        commands.put("list", new ListCommand());
        commands.put("total", new TotalCommand());
        commands.put("average", new AverageCommand());
        commands.put("help", new HelpCommand());
        commands.put("recurring", new AddRecurringCommand());
        commands.put("delete-recurring", new DeleteRecurringCommand());
        commands.put("list-recurring", new ListRecurringCommand());
        commands.put("summary", new SummaryCommand());
        return commands;
    }
}
