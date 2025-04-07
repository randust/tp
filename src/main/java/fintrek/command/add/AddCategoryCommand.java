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
    private static final String COMMAND_NAME = "Category";


    public AddCategoryCommand(boolean isRecurring) {
        super(isRecurring);
    }

    /**
     * This function allows user to add a new category into the expense list
     *                  to be used now or in the future
     * The arguments are first validated whether they are null or blank. Aferwards, we check if
     *                  it contains white space and is a valid String
     * Upon validation the argument will be trimmed and changed to upper case.
     * @param arguments raw user input passed to the command which contains the
     *                  new category to be added
     * @return a successful {@code CommandResult} object with the success message, or a
     *                  failed {@code CommandResult} object with the error message.
     */
    @Override
    public CommandResult execute(String arguments) {
        if (InputValidator.isNullOrBlank(arguments)) {
            String message = String.format(MessageDisplayer.CANNOT_BE_NULL_MESSAGE_TEMPLATE, COMMAND_NAME);
            return new CommandResult(false, message);
        }

        if (InputValidator.containsWhiteSpace(arguments.trim())) {
            return new CommandResult(false, MessageDisplayer.CATEGORY_WHITESPACE_ERROR);
        }

        if (!InputValidator.isValidStringLength(arguments)) {
            String message = String.format(MessageDisplayer.STRING_OUT_OF_RANGE_FORMAT_MESSAGE, COMMAND_NAME);
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
