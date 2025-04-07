package fintrek.command.add;

import fintrek.command.registry.CommandResult;
import fintrek.expense.core.CategoryManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import fintrek.misc.MessageDisplayer;
import fintrek.util.TestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddCategoryCommandTest {
    /**
     * Clear all existing custom categories in CategoryManager and adds one new custom category before each test
     */
    @BeforeEach
    public void setUp() {
        CategoryManager.clearCustomCategories();
        CategoryManager.addCustomCategory("new");
    }

    /**
     * Tests if the AddCategoryCommand returns error messages for empty inputs or
     * inputs simply consisting of whitespaces
     * @param input empty inputs or inputs consisting of only whitespaces
     */
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "                               "})
    public void testAddCategoryCommand_nullInputs_failure(String input) {
        AddCategoryCommand addCategoryCommand = new AddCategoryCommand(false);
        CommandResult result = addCategoryCommand.execute(input);
        String message = String.format(MessageDisplayer.CANNOT_BE_NULL_MESSAGE_TEMPLATE, "Category");

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandMessage(result, input, message);
    }

    /**
     * Tests if AddCategoryCommand returns an error for inputs which are not single words
     *
     * @param input inputs containing some whitespace
     */
    @ParameterizedTest
    @ValueSource(strings = {"two words", "three word category", """
            new
            line"""})
    public void testAddCategoryCommand_notSingleWord_failure(String input) {
        AddCategoryCommand addCategoryCommand = new AddCategoryCommand(false);
        CommandResult result = addCategoryCommand.execute(input);

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandMessage(result, input, MessageDisplayer.CATEGORY_WHITESPACE_ERROR);
    }

    /**
     * Tests if AddCategoryCommand returns an error for inputs which exceed character limits
     *
     * @param input characters to be concatenated more than 100 times
     */
    @ParameterizedTest
    @ValueSource(strings = {"a", "$"})
    public void testAddCategoryCommand_tooLong_failure(String input) {
        AddCategoryCommand addCategoryCommand = new AddCategoryCommand(false);
        String longInput = input;
        for (int i = 0; i < 200; i++) {
            longInput = longInput + input;
        }
        CommandResult result = addCategoryCommand.execute(longInput);
        String message = String.format(MessageDisplayer.STRING_OUT_OF_RANGE_FORMAT_MESSAGE, "Category");

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandMessage(result, input, message);
    }

    /**
     * Tests if AddCategoryCommand returns an error for already existing categories
     *
     * @param input inputs of already valid categories
     */
    @ParameterizedTest
    @ValueSource(strings = {"food", "transport", "new"})
    public void testAddCategoryCommand_alreadyExists_failure(String input) {
        AddCategoryCommand addCategoryCommand = new AddCategoryCommand(false);
        CommandResult result = addCategoryCommand.execute(input);

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandMessage(result, input, MessageDisplayer.CATEGORY_ALREADY_EXISTS);
    }

    /**
     * Tests if AddCategoryCommand successfully adds valid inputs to list of categories
     *
     * @param input various valid inputs
     */
    @ParameterizedTest
    @ValueSource(strings = {"new2", "$$$$$", "   shopping   ",
        "ultrapseudointernationalmicrotechnosociobiochemicalelectromagnetoantidisestablishmentism"})
    public void testAddCategoryCommand_validInput_success(String input) {
        AddCategoryCommand addCategoryCommand = new AddCategoryCommand(false);
        CategoryManager.clearCustomCategories();
        CommandResult result = addCategoryCommand.execute(input);

        String message = String.format(MessageDisplayer.ADD_CATEGORY_SUCCESS_MESSAGE_TEMPLATE,
                input.trim().toUpperCase());
        assertTrue(CategoryManager.hasCustomCategories());
        TestUtils.assertCommandSuccess(result, input);
        TestUtils.assertCommandMessage(result, input, message);
    }

    /**
     * Tests the description of the add category command.
     * Ensures the command returns the correct description.
     */
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void testAddCategoryCommand_getDescription_success(boolean isRecurring) {
        AddCategoryCommand addCategoryCommand = new AddCategoryCommand(isRecurring);
        String formatString;
        String exampleString;
        if (isRecurring) {
            formatString = "Format: /add-category <CATEGORY>";
            exampleString = "";
        } else {
            formatString = "Format: /add-category <CATEGORY>";
            exampleString = "";
        }
        String expectedDescription = formatString + "\n" +
                """
                CATEGORY has a character limit of 100, and cannot contain spaces.
                Example: /add-category drinks - adds category 'DRINKS' to list of valid categories."""
                + exampleString;

        assertEquals(expectedDescription, addCategoryCommand.getDescription(),
                MessageDisplayer.ASSERT_COMMAND_EXPECTED_OUTPUT + MessageDisplayer.ASSERT_GET_DESC);
    }
}
