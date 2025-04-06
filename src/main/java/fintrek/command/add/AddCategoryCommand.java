package fintrek.command.add;

import fintrek.command.Command;
import fintrek.command.registry.CommandInfo;
import fintrek.command.registry.CommandResult;
import fintrek.expense.core.CategoryManager;
import fintrek.misc.MessageDisplayer;
import fintrek.util.InputValidator;

@CommandInfo(
        recurringFormat = "Format: /add-category <CATEGORY>",
        regularFormat = "Format: /add-category <CATEGORY>",
        description = """
                CATEGORY has a character limit of 100, and cannot contain spaces.
                Example: /add-category drinks - adds category 'DRINKS' to list of valid categories.""",
        recurringExample = "",
        regularExample = ""
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

        if (!arguments.trim().matches("^\\S+$")) {
            return new CommandResult(false, MessageDisplayer.CATEGORY_WHITESPACE_ERROR);
        }

        if (!InputValidator.isValidStringLength(arguments)) {
            String message = String.format(MessageDisplayer.STRING_OUT_OF_RANGE_FORMAT_MESSAGE, "Category");
            return new CommandResult(false, message);
        }

        String newCategory = arguments.trim().toUpperCase();
        if (InputValidator.isValidCategory(newCategory)) {
            return new CommandResult(false, MessageDisplayer.CATEGORY_ALREADY_EXISTS);
        }

        CategoryManager.addCustomCategory(newCategory);

        String message = String.format(MessageDisplayer.ADD_CATEGORY_SUCCESS_MESSAGE_TEMPLATE, newCategory);
        return new CommandResult(true, message);
    }
}
