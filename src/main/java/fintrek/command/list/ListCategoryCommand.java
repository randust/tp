package fintrek.command.list;

import fintrek.command.Command;
import fintrek.command.registry.CommandInfo;
import fintrek.command.registry.CommandResult;
import fintrek.expense.core.CategoryManager;
import fintrek.misc.MessageDisplayer;

@CommandInfo(
        recurringFormat = "Format: /list-category",
        regularFormat = "Format: /list-category",
        description = "Lists all valid categories.",
        recurringExample = "",
        regularExample = ""
)

public class ListCategoryCommand extends Command {
    private static final String COMMAND_NAME = "list-category";

    public ListCategoryCommand(boolean isRecurring) {
        super(isRecurring);
    }

    /**
     * This function allows users to add custom category on top of the default
     *              valid categories
     * @param arguments raw user input passed to the command
     * @return a successful {@code CommandResult} object with the success message, or a
     *              failed {@code CommandResult} object with the error message.
     */
    @Override
    public CommandResult execute(String arguments) {
        String defaultList = CategoryManager.getDefaultCategoriesAsString();
        String customList = CategoryManager.getCustomCategoriesAsString();

        String message = String.format(MessageDisplayer.LIST_CATEGORIES_MESSAGE_TEMPLATE, defaultList, customList);
        return new CommandResult(true, message);
    }
}
