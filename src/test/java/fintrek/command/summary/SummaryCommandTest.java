package fintrek.command.summary;

import fintrek.command.registry.CommandResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import fintrek.misc.MessageDisplayer;
import fintrek.util.TestUtils;

/**
 * Unit tests for the {@code SummaryCommand} class.
 * Ensures that the summary information about the expenses is correctly displayed.
 */
public class SummaryCommandTest {
    /**
     * Clear all existing expenses in RegularExpenseManager and RecurringExpenseManager
     * and adds set list of expenses before each test.
     */
    @BeforeEach
    public void setUp() {
        TestUtils.regularService.clearExpenses();
        TestUtils.recurringService.clearExpenses();
    }

    /**
     * Tests summary command execution in general with empty expense lists.
     * Verifies that executing the summary command on an empty list of expenses
     * returns a success result with the appropriate empty list message.
     *
     * @param isRecurring Whether to test with recurring expenses (true) or regular expenses (false)
     */
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void testSummaryCommand_generalExpensesEmptyList_success(boolean isRecurring) {
        SummaryCommand summaryCommand = new SummaryCommand(isRecurring);
        CommandResult result = summaryCommand.execute("");
        String expectedMessage = String.format(MessageDisplayer.LIST_SUMMARY_SUCCESS_MESSAGE_TEMPLATE,
                MessageDisplayer.EMPTY_LIST_MESSAGE);

        TestUtils.assertCommandSuccess(result, MessageDisplayer.ASSERT_EMPTY_LIST);
        TestUtils.assertCommandMessage(result, MessageDisplayer.ASSERT_EMPTY_LIST, expectedMessage);
    }

    /**
     * Tests summary command execution for a specific category with empty expense lists.
     * Verifies that attempting to get a summary for a specific category when the list is empty
     * returns an error result with the appropriate category not found message.
     *
     * @param isRecurring Whether to test with recurring expenses (true) or regular expenses (false)
     * @param input The category name to search for in the summary
     */
    @ParameterizedTest
    @CsvSource({
        "true, food",
        "true, transport",
        "true, shopping",
        "true, foo",
        "false, food",
        "false, transport",
        "false, shopping",
        "false, foo"
    })
    public void testSummaryCommand_singleCategoryExpensesEmptyList_returnsError(boolean isRecurring, String input) {
        SummaryCommand summaryCommand = new SummaryCommand(isRecurring);
        CommandResult result = summaryCommand.execute(input);
        String expectedMessage = MessageDisplayer.ERROR_LOADING_SUMMARY + MessageDisplayer.CATEGORY_NOT_FOUND;

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandErrorMessage(result, input, expectedMessage);
    }

    /**
     * Tests summary command execution with list of predefined expenses.
     * Verifies that executing the summary command on a populated list of expenses
     * returns a success result with the appropriate summary of all category totals.
     *
     * @param isRecurring Whether to test with recurring expenses (true) or regular expenses (false)
     */
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void testSummaryCommand_generalExpensesFilledList_success(boolean isRecurring) {
        if (isRecurring) {
            TestUtils.addConstantRecurringExpenses();
        } else {
            TestUtils.addConstantExpenses();
        }

        SummaryCommand summaryCommand = new SummaryCommand(isRecurring);
        CommandResult result = summaryCommand.execute("");

        String expectedMessage;
        if (isRecurring) {
            expectedMessage = String.format(MessageDisplayer.LIST_SUMMARY_SUCCESS_MESSAGE_TEMPLATE,
                    TestUtils.recurringReporter.listAllCategoryTotals());
        } else {
            expectedMessage = String.format(MessageDisplayer.LIST_SUMMARY_SUCCESS_MESSAGE_TEMPLATE,
                    TestUtils.regularReporter.listAllCategoryTotals());
        }

        TestUtils.assertCommandSuccess(result, MessageDisplayer.ASSERT_FILLED_LIST);
        TestUtils.assertCommandMessage(result, MessageDisplayer.ASSERT_FILLED_LIST, expectedMessage);
    }

    /**
     * Tests summary command execution for a valid specific category with list of predefined expenses.
     * Verifies that requesting a summary for a valid category when the list is populated
     * returns a success result with the appropriate category total.
     *
     * @param isRecurring Whether to test with recurring expenses (true) or regular expenses (false)
     * @param input The valid category name to search for in the summary
     */
    @ParameterizedTest
    @CsvSource({
        "true, food",
        "true, transport",
        "true, entertainment",
        "false, food",
        "false, transport",
        "false, entertainment",
    })
    public void testSummaryCommand_singleCategoryExpensesFilledList_validInput(boolean isRecurring,
                                                                                       String input) {
        if (isRecurring) {
            TestUtils.addConstantRecurringExpenses();
        } else {
            TestUtils.addConstantExpenses();
        }

        SummaryCommand summaryCommand = new SummaryCommand(isRecurring);
        CommandResult result = summaryCommand.execute(input);

        String expectedMessage;
        if (isRecurring) {
            expectedMessage = String.format(MessageDisplayer.LIST_SUMMARY_SUCCESS_MESSAGE_TEMPLATE,
                    TestUtils.recurringReporter.listSingleCategoryTotal(input.toUpperCase()));
        } else {
            expectedMessage = String.format(MessageDisplayer.LIST_SUMMARY_SUCCESS_MESSAGE_TEMPLATE,
                    TestUtils.regularReporter.listSingleCategoryTotal(input.toUpperCase()));
        }

        TestUtils.assertCommandSuccess(result, MessageDisplayer.ASSERT_FILLED_LIST);
        TestUtils.assertCommandMessage(result, MessageDisplayer.ASSERT_FILLED_LIST, expectedMessage);
    }

    /**
     * Tests summary command execution for an invalid specific category with list of predefined expenses.
     * Verifies that requesting a summary for an invalid category when the list is populated
     * returns an error result with the appropriate category not found message.
     *
     * @param isRecurring Whether to test with recurring expenses (true) or regular expenses (false)
     * @param input The invalid category name to search for in the summary
     */
    @ParameterizedTest
    @CsvSource({
        "true, foo",
        "true, transp",
        "true, shopping",
        "false, foo",
        "false, transp",
        "false, shopping",
    })
    public void testSummaryCommand_singleCategoryExpensesFilledList_invalidInput(boolean isRecurring,
                                                                                              String input) {
        if (isRecurring) {
            TestUtils.addConstantRecurringExpenses();
        } else {
            TestUtils.addConstantExpenses();
        }

        SummaryCommand summaryCommand = new SummaryCommand(isRecurring);
        CommandResult result = summaryCommand.execute(input);

        String expectedMessage = MessageDisplayer.ERROR_LOADING_SUMMARY + MessageDisplayer.CATEGORY_NOT_FOUND;

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandErrorMessage(result, input, expectedMessage);
    }

    /**
     * Tests the description of summary command.
     * Verifies the command returns the correct description.
     */
    @Test
    public void testSummaryCommand_getDescription_success() {
        SummaryCommand command = new SummaryCommand(false);
        String expectedDescription = """
            Format: /summary [CATEGORY]
            Returns a summary of the total spending in each category.
            Optionally pass a keyword to show the total spending and expenses in that category.
            """;

        assertEquals(expectedDescription, command.getDescription(),
                MessageDisplayer.ASSERT_COMMAND_EXPECTED_OUTPUT + MessageDisplayer.ASSERT_GET_DESC);
    }
}
