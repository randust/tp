package fintrek.command.add;

import fintrek.command.Command;
import fintrek.command.registry.CommandInfo;
import fintrek.command.registry.CommandResult;
import fintrek.expense.core.Category;
import fintrek.misc.MessageDisplayer;
import fintrek.util.InputValidator;

@CommandInfo(
        recurringFormat = "Format: /add-category <CATEGORY>",
        regularFormat = "Format: /add-category <CATEGORY>",
        description = """
                CATEGORY is a string user input
                Example: /add-category drinks - adds DRINKS to list of valid categories
                """
)

public class AddCategoryCommand extends Command {
    private static final String COMMAND_NAME = "add-category";

    public AddCategoryCommand(boolean isRecurring) {
        super(isRecurring);
    }

    @Override
    public CommandResult execute(String arguments) {
        if (InputValidator.isNullOrBlank(arguments)) {
            String message = String.format(MessageDisplayer.CANNOT_BE_NULL_MESSAGE_TEMPLATE, "command");
            return new CommandResult(false, message);
        }
        if (!InputValidator.isValidStringLength(arguments)) {
            String message = String.format(MessageDisplayer.STRING_OUT_OF_RANGE_FORMAT_MESSAGE, "Category");
            return new CommandResult(false, message);
        }

        String newCategory = arguments.trim().toUpperCase();
        if (InputValidator.isValidCategory(newCategory)) {
            return new CommandResult(false, MessageDisplayer.CATEGORY_ALREADY_EXISTS);
        } else {
            Category.addCustomCategory(newCategory);
        }

        String message = String.format(MessageDisplayer.ADD_CATEGORY_SUCCESS_MESSAGE_TEMPLATE, newCategory);
        return new CommandResult(true, message);
    }
}
