package fintrek.command.list;

import fintrek.command.Command;
import fintrek.command.registry.CommandInfo;
import fintrek.command.registry.CommandResult;
import fintrek.expense.core.CategoryManager;
import fintrek.misc.MessageDisplayer;

@CommandInfo(
        recurringFormat = "Format: /list-category",
        regularFormat = "Format: /list-category",
        description = "Lists all valid categories",
        recurringExample = "",
        regularExample = ""
)

public class ListCategoryCommand extends Command {
    private static final String COMMAND_NAME = "list-category";

    public ListCategoryCommand(boolean isRecurring) {
        super(isRecurring);
    }

    @Override
    public CommandResult execute(String arguments) {
        String defaultList = CategoryManager.getDefaultCategoriesAsString();
        String customList = CategoryManager.getCustomCategoriesAsString();

        String message = String.format(MessageDisplayer.LIST_CATEGORIES_MESSAGE_TEMPLATE, defaultList, customList);
        return new CommandResult(true, message);
    }
}