package fintrek.command.list;

import fintrek.command.add.AddCategoryCommand;
import fintrek.command.registry.CommandResult;
import fintrek.expense.core.CategoryManager;
import fintrek.misc.MessageDisplayer;
import fintrek.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ListCategoryCommandTest {

    /**
     * Clear all existing custom categories in CategoryManager and adds one new custom category before each test
     */
    @BeforeEach
    public void setUp() {
        CategoryManager.clearCustomCategories();
    }

    /**
     * Tests ListCategoryCommand for default list only
     * Verifies correct default list and empty custom list displayed
     */
    @Test
    public void testListCategoryCommand_noCustom_success() {
        ListCategoryCommand listCategoryCommand = new ListCategoryCommand(false);
        CommandResult result = listCategoryCommand.execute("");

        String defaultList = "TRANSPORT, ENTERTAINMENT, UTILITIES, UNCATEGORIZED, GIFTS, HEALTH, FOOD";
        String message = String.format(MessageDisplayer.LIST_CATEGORIES_MESSAGE_TEMPLATE, defaultList, "");

        TestUtils.assertCommandSuccess(result, MessageDisplayer.ASSERT_DEFAULT_CATEGORIES);
        TestUtils.assertCommandMessage(result, MessageDisplayer.ASSERT_DEFAULT_CATEGORIES, message);
    }

    /**
     * Tests ListCategoryCommand for default list only
     * Verifies correct default list and empty custom list displayed
     */
    @Test
    public void testListCategoryCommand_withCustom_success() {
        ListCategoryCommand listCategoryCommand = new ListCategoryCommand(false);
        CategoryManager.addCustomCategory("new");
        CommandResult result = listCategoryCommand.execute("");

        String defaultList = "TRANSPORT, ENTERTAINMENT, UTILITIES, UNCATEGORIZED, GIFTS, HEALTH, FOOD";
        String message = String.format(MessageDisplayer.LIST_CATEGORIES_MESSAGE_TEMPLATE, defaultList, "NEW");

        TestUtils.assertCommandSuccess(result, MessageDisplayer.ASSERT_CUSTOM_CATEGORIES);
        TestUtils.assertCommandMessage(result, MessageDisplayer.ASSERT_CUSTOM_CATEGORIES, message);
    }
}
