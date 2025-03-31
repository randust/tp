package fintrek.command.registry;

import fintrek.command.Command;
import fintrek.command.add.AddCommand;
import fintrek.command.delete.DeleteCommand;
import fintrek.command.edit.EditCommand;
import fintrek.command.help.HelpCommand;
import fintrek.command.list.ListCommand;
import fintrek.command.summary.AverageCommand;
import fintrek.command.summary.SummaryCommand;
import fintrek.command.summary.TotalCommand;

import java.util.HashMap;
import java.util.Map;

public class CommandRegistrar {
    public static Map<String, Command> registerAll() {
        Map<String, Command> commands = new HashMap<>();

        // Regular commands
        commands.put("add", new AddCommand(false));
        commands.put("delete", new DeleteCommand(false));
        commands.put("edit", new EditCommand(false));
        commands.put("list", new ListCommand(false));
        commands.put("total", new TotalCommand(false));
        commands.put("average", new AverageCommand(false));
        commands.put("summary", new SummaryCommand(false));

        // Recurring commands
        commands.put("recurring", new AddCommand(true));
        commands.put("delete-recurring", new DeleteCommand(true));
        commands.put("edit-recurring", new EditCommand(true));
        commands.put("list-recurring", new ListCommand(true));
        commands.put("total-recurring", new TotalCommand(true));
        commands.put("average-recurring", new AverageCommand(true));
        commands.put("summary-recurring", new SummaryCommand(true));

        // Misc
        commands.put("help", new HelpCommand(false));

        return commands;
    }
}
