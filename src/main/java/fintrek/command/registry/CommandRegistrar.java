package fintrek.command.registry;

import fintrek.command.Command;
import fintrek.command.add.AddCategoryCommand;
import fintrek.command.add.AddCommand;
import fintrek.command.budget.BudgetCommand;
import fintrek.command.list.ListCategoryCommand;
import fintrek.command.delete.DeleteCommand;
import fintrek.command.edit.EditCommand;
import fintrek.command.help.HelpCommand;
import fintrek.command.list.ListCommand;
import fintrek.command.sort.ListSortCommand;
import fintrek.command.summary.AverageCommand;
import fintrek.command.summary.SummaryCommand;
import fintrek.command.summary.TotalCommand;

import java.util.HashMap;
import java.util.Map;

/**
 * Registers all available commands (regular, recurring, and misc) into a command map.
 *
 * <p>This class is responsible for instantiating each {@link Command} subclass and mapping it
 * to its associated command keyword. It is typically invoked once during system initialization
 * to populate the {@link CommandRegistry}.</p>
 */
public class CommandRegistrar {

    /**
     * Register all commands and returns a mapping of command keywords to their corresponding {@link Command} instances.
     *
     * <p>The following categories are registered:</p>
     * <ul>
     *     <li>Regular commands: {@code add}, {@code delete}, {@code edit}, {@code list}, {@code total},
     *     {@code average}, {@code summary}, {@code budget}</li>
     *     <li>Recurring commands: {@code recurring}, {@code delete-recurring}, {@code edit-recurring}, etc.</li>
     *     <li>Misc commands: {@code help}</li>
     * </ul>
     *
     * @return a map from command names to {@link Command} instances
     */
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
        commands.put("budget", new BudgetCommand(false));
        commands.put("list-sort", new ListSortCommand(false));
        commands.put("add-category", new AddCategoryCommand(false));
        commands.put("list-category", new ListCategoryCommand(false));

        // Recurring commands
        commands.put("add-recurring", new AddCommand(true));
        commands.put("delete-recurring", new DeleteCommand(true));
        commands.put("edit-recurring", new EditCommand(true));
        commands.put("list-recurring", new ListCommand(true));
        commands.put("total-recurring", new TotalCommand(true));
        commands.put("average-recurring", new AverageCommand(true));
        commands.put("summary-recurring", new SummaryCommand(true));
        commands.put("list-sort-recurring", new ListSortCommand(true));

        // Misc
        commands.put("help", new HelpCommand(false));

        return commands;
    }
}
