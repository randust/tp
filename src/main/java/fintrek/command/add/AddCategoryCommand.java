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
                AMOUNT must be a positive number greater than 0
                CATEGORY is an optional argument
                DATE is an optional argument which must be in the form dd-MM-yyyy
                Example:
                """
)

//TODO: javadocs & command info
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
        String newCategory = arguments.trim().toUpperCase();
        if (Category.isValidCategory(newCategory)) {
            return new CommandResult(false, MessageDisplayer.CATEGORY_ALREADY_EXISTS);
        } else {
            Category.addCustomCategory(newCategory);
        }

        String message = String.format(MessageDisplayer.ADD_CATEGORY_SUCCESS_MESSAGE_TEMPLATE, newCategory);
        return new CommandResult(true, message);
    }
}