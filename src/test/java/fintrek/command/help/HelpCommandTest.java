package fintrek.command.help;

import fintrek.command.registry.CommandRegistry;
import fintrek.command.registry.CommandResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import fintrek.misc.MessageDisplayer;
import fintrek.util.TestUtils;

/**
 * Unit tests for the {@code HelpCommand} class.
 * Ensures that the help information is correctly displayed.
 */
public class HelpCommandTest {
    /**
     * Tests the help command with general input.
     * Verifies that empty or whitespace inputs return the general help description.
     * @param input The input to test the general help command.
     */
    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    public void testHelpCommand_general_success(String input) {
        HelpCommand helpCommand = new HelpCommand(false);
        CommandResult result = helpCommand.execute(input);
        String expectedMessage = CommandRegistry.getAllCommandDescriptions();

        TestUtils.assertCommandSuccess(result, input);
        TestUtils.assertCommandMessage(result, MessageDisplayer.ASSERT_GENERAL_HELP_MESSAGE, expectedMessage);
    }

    /**
     * Tests the help command for the "add" command.
     * Verifies that the inputs variations for "add" return the "add" command description.
     * @param input The input to test the help command for "add".
     */
    @ParameterizedTest
    @ValueSource(strings = {"add", "ADD", " add ", "add       "})
    public void testHelpCommand_add_success(String input) {
        HelpCommand helpCommand = new HelpCommand(false);
        CommandResult result = helpCommand.execute(input);
        String expectedMessage = CommandRegistry.getCommand("add").getDescription();

        TestUtils.assertCommandSuccess(result, input);
        TestUtils.assertCommandMessage(result, input, expectedMessage);
    }

    /**
     * Tests the help command for the "add" command.
     * Verifies that the inputs variations for "add" return the "add" command description.
     * @param input The input to test the help command for "add".
     */
    @ParameterizedTest
    @ValueSource(strings = {"adddd", "/add", "hiaddhi"})
    public void testHelpCommand_typoAdd_fail(String input) {
        HelpCommand helpCommand = new HelpCommand(false);
        CommandResult result = helpCommand.execute(input);

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandMessage(result, input, MessageDisplayer.HELP_UNKNOWN_TOPIC);
    }

    /**
     * Tests the help command for the "delete" command.
     * Verifies that the inputs variations for "delete" return the "delete" command description.
     * @param input The input to test the help command for "delete".
     */
    @ParameterizedTest
    @ValueSource(strings = {"delete", "DELETE", " delete ", "delete      "})
    public void testHelpCommand_delete_success(String input) {
        HelpCommand helpCommand = new HelpCommand(false);
        CommandResult result = helpCommand.execute(input);
        String expectedMessage = CommandRegistry.getCommand("delete").getDescription();

        TestUtils.assertCommandSuccess(result, input);
        TestUtils.assertCommandMessage(result, input, expectedMessage);
    }

    /**
     * Tests the help command for the "delete" command.
     * Verifies that the inputs wrong variations for "delete"
     *              do not return the "delete" command description.
     * @param input The input to test the help command for "delete".
     */
    @ParameterizedTest
    @ValueSource(strings = {"deleteee", "/delete", "byedeletebye"})
    public void testHelpCommand_typoDelete_fail(String input) {
        HelpCommand helpCommand = new HelpCommand(false);
        CommandResult result = helpCommand.execute(input);

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandMessage(result, input, MessageDisplayer.HELP_UNKNOWN_TOPIC);
    }

    /**
     * Tests the help command for the "total" command.
     * Verifies that the inputs variations for "total" return the "total" command description.
     * @param input The input to test the help command for "total".
     */
    @ParameterizedTest
    @ValueSource(strings = {"total", "TOTAL", " total ", "total       "})
    public void testHelpCommand_total_success(String input) {
        HelpCommand helpCommand = new HelpCommand(false);
        CommandResult result = helpCommand.execute(input);
        String expectedMessage = CommandRegistry.getCommand("total").getDescription();

        TestUtils.assertCommandSuccess(result, input);
        TestUtils.assertCommandMessage(result, input, expectedMessage);
    }

    /**
     * Tests the help command for the "total" command.
     * Verifies that the inputs wrong variations for "total"
     *              do not return the "total" command description.
     * @param input The input to test the help command for "total".
     */
    @ParameterizedTest
    @ValueSource(strings = {"totalll", "/total", "hitotalhi"})
    public void testHelpCommand_typoTotal_fail(String input) {
        HelpCommand helpCommand = new HelpCommand(false);
        CommandResult result = helpCommand.execute(input);

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandMessage(result, input, MessageDisplayer.HELP_UNKNOWN_TOPIC);
    }

    /**
     * Tests the help command for the "budget" command.
     * Ensures that the inputs variations for "budget" return the "budget" command description.
     * @param input The input to test the help command for "budget".
     */
    @ParameterizedTest
    @ValueSource(strings = {"BUDGET", "BuDgET", "budget", "budget      "})
    public void testHelpCommand_budget_success(String input) {
        HelpCommand helpCommand = new HelpCommand(false);
        CommandResult result = helpCommand.execute(input);
        String expectedMessage = CommandRegistry.getCommand("budget").getDescription();

        TestUtils.assertCommandSuccess(result, input);
        TestUtils.assertCommandMessage(result, input, expectedMessage);
    }

    /**
     * Tests the help command for the "budget" command.
     * Ensures that the inputs wrong variations for "budget"
     *              do not return the "budget" command description.
     * @param input The input to test the help command for "budget".
     */
    @ParameterizedTest
    @ValueSource(strings = {"bbbbudget", "budgetttt", "/budget", "budget?", "hibudgethi"})
    public void testHelpCommand_typoBudget_fail(String input) {
        HelpCommand helpCommand = new HelpCommand(false);
        CommandResult result = helpCommand.execute(input);

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandMessage(result, input, MessageDisplayer.HELP_UNKNOWN_TOPIC);
    }

    /**
     * Tests the help command for the "average" command.
     * Verifies that the inputs variations for "average" return the "average" command description.
     * @param input The input to test the help command for "average".
     */
    @ParameterizedTest
    @ValueSource(strings = {"average", "AVERAGE", " average ", "average      "})
    public void testHelpCommand_average_success(String input) {
        HelpCommand helpCommand = new HelpCommand(false);
        CommandResult result = helpCommand.execute(input);
        String expectedMessage = CommandRegistry.getCommand("average").getDescription();

        TestUtils.assertCommandSuccess(result, input);
        TestUtils.assertCommandMessage(result, input, expectedMessage);
    }

    /**
     * Tests the help command for the "average" command.
     * Verifies that the inputs wrong variations for "average"
     *              do not return the "average" command description.
     * @param input The input to test the help command for "average".
     */
    @ParameterizedTest
    @ValueSource(strings = {"averageee", "/average", "hiaverage"})
    public void testHelpCommand_typoAverage_success(String input) {
        HelpCommand helpCommand = new HelpCommand(false);
        CommandResult result = helpCommand.execute(input);

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandMessage(result, input, MessageDisplayer.HELP_UNKNOWN_TOPIC);
    }

    /**
     * Tests the help command for the "summary" command.
     * Verifies that the inputs variations for "summary" return the "summary" command description.
     * @param input The input to test the help command for "summary".
     */
    @ParameterizedTest
    @ValueSource(strings = {"summary", "SUMMARY", " summary ", "summary     "})
    public void testHelpCommand_summary_success(String input) {
        HelpCommand helpCommand = new HelpCommand(false);
        CommandResult result = helpCommand.execute(input);
        String expectedMessage = CommandRegistry.getCommand("summary").getDescription();

        TestUtils.assertCommandSuccess(result, input);
        TestUtils.assertCommandMessage(result, input, expectedMessage);
    }

    /**
     * Tests the help command for the "summary" command.
     * Verifies that the inputs variations for "summary" return the "summary" command description.
     * @param input The input to test the help command for "summary".
     */
    @ParameterizedTest
    @ValueSource(strings = {"summaryyy", "/summary", "hisummaryhi"})
    public void testHelpCommand_typoSummary_success(String input) {
        HelpCommand helpCommand = new HelpCommand(false);
        CommandResult result = helpCommand.execute(input);

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandMessage(result, input, MessageDisplayer.HELP_UNKNOWN_TOPIC);
    }

    /**
     * Tests the help command for invalid commands.
     * Verifies that unrecognized commands returns an error.
     * @param input The invalid command input.
     */
    @ParameterizedTest
    @ValueSource(strings = {"hello", "delet", "brother", "321"})
    public void testHelpCommand_unknownTopic_returnsError(String input) {
        HelpCommand helpCommand = new HelpCommand(false);
        CommandResult result = helpCommand.execute(input);
        String expectedMessage = MessageDisplayer.HELP_UNKNOWN_TOPIC;

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandErrorMessage(result, input, expectedMessage);
    }

    /**
     * Tests the description of help command.
     * Verifies the command returns the correct description.
     */
    @Test
    public void testHelpCommand_getDescription_success() {
        HelpCommand command = new HelpCommand(false);
        String expectedDescription = """
            Format: /help [COMMAND]
            Displays help message for all commands.
            Optionally pass a keyword to show usage for a specific command.
            """;

        assertEquals(expectedDescription, command.getDescription(),
                MessageDisplayer.ASSERT_COMMAND_EXPECTED_OUTPUT + MessageDisplayer.ASSERT_GET_DESC);
    }
}
