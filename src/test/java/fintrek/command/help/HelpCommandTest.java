package fintrek.command.help;

import fintrek.command.registry.CommandRegistry;
import fintrek.command.registry.CommandResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import fintrek.misc.MessageDisplayer;

/**
 * Unit tests for the {@code HelpCommand} class.
 * Ensures that the help information is correctly displayed.
 */
public class HelpCommandTest {
    /**
     * Tests the help command with general input.
     * Ensures that empty or whitespace inputs return the general help description.
     * @param input The input to test the general help command.
     */
    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    public void testHelpCommand_general_success(String input) {
        HelpCommand helpCommand = new HelpCommand(false);
        CommandResult result = helpCommand.execute(input);
        String expectedMessage = CommandRegistry.getAllCommandDescriptions();

        assertTrue(result.isSuccess(), MessageDisplayer.ASSERT_COMMAND_SUCCESS_PREFIX + "'" + input + "'");
        assertEquals(expectedMessage, result.message(), MessageDisplayer.ASSERT_COMMAND_EXPECTED_OUTPUT
                + MessageDisplayer.ASSERT_GENERAL_HELP_MESSAGE);
    }

    /**
     * Tests the help command for the "add" command.
     * Ensures that the inputs variations for "add" return the "add" command description.
     * @param input The input to test the help command for "add".
     */
    @ParameterizedTest
    @ValueSource(strings = {"add", "ADD", " add ", "adddd", "/add"})
    public void testHelpCommand_add_success(String input) {
        HelpCommand helpCommand = new HelpCommand(false);
        CommandResult result = helpCommand.execute(input);
        String expectedMessage = CommandRegistry.getCommand("add").getDescription();

        assertTrue(result.isSuccess(), MessageDisplayer.ASSERT_COMMAND_SUCCESS_PREFIX + "'" + input + "'");
        assertEquals(expectedMessage, result.message(),
                MessageDisplayer.ASSERT_COMMAND_EXPECTED_OUTPUT + "'" + input + "'");
    }

    /**
     * Tests the help command for the "delete" command.
     * Ensures that the inputs variations for "delete" return the "delete" command description.
     * @param input The input to test the help command for "delete".
     */
    @ParameterizedTest
    @ValueSource(strings = {"delete", "DELETE", " delete ", "deleteee", "/delete"})
    public void testHelpCommand_delete_success(String input) {
        HelpCommand helpCommand = new HelpCommand(false);
        CommandResult result = helpCommand.execute(input);
        String expectedMessage = CommandRegistry.getCommand("delete").getDescription();

        assertTrue(result.isSuccess(), MessageDisplayer.ASSERT_COMMAND_SUCCESS_PREFIX + "'" + input + "'");
        assertEquals(expectedMessage, result.message(),
                MessageDisplayer.ASSERT_COMMAND_EXPECTED_OUTPUT + "'" + input + "'");
    }

    /**
     * Tests the help command for the "total" command.
     * Ensures that the inputs variations for "total" return the "total" command description.
     * @param input The input to test the help command for "total".
     */
    @ParameterizedTest
    @ValueSource(strings = {"total", "TOTAL", " total ", "totalll", "/total"})
    public void testHelpCommand_total_success(String input) {
        HelpCommand helpCommand = new HelpCommand(false);
        CommandResult result = helpCommand.execute(input);
        String expectedMessage = CommandRegistry.getCommand("total").getDescription();

        assertTrue(result.isSuccess());
        assertEquals(expectedMessage, result.message(),
                MessageDisplayer.ASSERT_COMMAND_EXPECTED_OUTPUT + "'" + input + "'");
    }

    /**
     * Tests the help command for the "average" command.
     * Ensures that the inputs variations for "average" return the "average" command description.
     * @param input The input to test the help command for "average".
     */
    @ParameterizedTest
    @ValueSource(strings = {"average", "AVERAGE", " average ", "averageee", "/average"})
    public void testHelpCommand_average_success(String input) {
        HelpCommand helpCommand = new HelpCommand(false);
        CommandResult result = helpCommand.execute(input);
        String expectedMessage = CommandRegistry.getCommand("average").getDescription();

        assertTrue(result.isSuccess());
        assertEquals(expectedMessage, result.message(),
                MessageDisplayer.ASSERT_COMMAND_EXPECTED_OUTPUT + "'" + input + "'");
    }

    /**
     * Tests the help command for the "summary" command.
     * Ensures that the inputs variations for "summary" return the "summary" command description.
     * @param input The input to test the help command for "summary".
     */
    @ParameterizedTest
    @ValueSource(strings = {"summary", "SUMMARY", " summary ", "summaryyy", "/summary"})
    public void testHelpCommand_summary_success(String input) {
        HelpCommand helpCommand = new HelpCommand(false);
        CommandResult result = helpCommand.execute(input);
        String expectedMessage = CommandRegistry.getCommand("summary").getDescription();

        assertTrue(result.isSuccess());
        assertEquals(expectedMessage, result.message(),
                MessageDisplayer.ASSERT_COMMAND_EXPECTED_OUTPUT + "'" + input + "'");
    }

    /**
     * Tests the help command for invalid commands.
     * Ensures that unrecognized commands returns an error.
     * @param input The invalid command input.
     */
    @ParameterizedTest
    @ValueSource(strings = {"hello", "delet", "help"})
    public void testHelpCommand_unknownTopic_returnsError(String input) {
        HelpCommand helpCommand = new HelpCommand(false);
        CommandResult result = helpCommand.execute(input);

        assertFalse(result.isSuccess(), MessageDisplayer.ASSERT_COMMAND_FAILURE_PREFIX + "'" + input + "'");
        assertEquals(MessageDisplayer.HELP_UNKNOWN_TOPIC, result.message(),
                MessageDisplayer.ASSERT_EXPECTED_ERROR + "'" + input + "'");
    }

    /**
     * Tests the description of help command.
     * Ensures the command returns the correct description.
     */
    @Test
    public void testHelpCommand_getDescription_success() {
        HelpCommand command = new HelpCommand(false);
        String expectedDescription = """
            Format: /help [COMMAND]
            Displays help message for all commands. Optionally pass a keyword to show usage for a specific command.
            """;

        assertEquals(expectedDescription, command.getDescription(),
                MessageDisplayer.ASSERT_COMMAND_EXPECTED_OUTPUT + MessageDisplayer.ASSERT_GET_DESC);
    }
}
