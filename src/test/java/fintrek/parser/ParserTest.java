package fintrek.parser;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import fintrek.misc.MessageDisplayer;

/**
 * Unit tests for the {@code Parser} class.
 * Ensures that user input is correctly parsed and validated.
 */
public class ParserTest {
    /**
     * Ensures that inputs missing a leading slash ('/') are recognized as incorrect.
     *
     * @param input The invalid user input.
     */
    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "\n  \n", "\t", "WithoutSlashInput WithoutSlashInput", "add", "help"})
    void parseWithoutSlashInput_returnsError(String input) {
        ParseResult result = Parser.parseUserInput(input);

        assertFalse(result.isSuccess(), MessageDisplayer.ASSERT_FAILURE_PREFIX + "'" + input + "'");
        assertEquals(MessageDisplayer.NO_COMMAND_MESSAGE, result.errorMessage(),
                MessageDisplayer.ASSERT_EXPECTED_ERROR + "'" + input + "'");
    }
    /**
     * Ensures that unrecognized commands return an error.
     *
     * @param input The invalid command input.
     */

    @ParameterizedTest
    @ValueSource(strings = {"/hello", "/addddd", "/", "/   ", "/ add"})
    void parseUnknownCommand_returnsError(String input) {
        ParseResult result = Parser.parseUserInput(input);

        assertFalse(result.isSuccess(), MessageDisplayer.ASSERT_FAILURE_PREFIX + "'" + input + "'");
        assertEquals(MessageDisplayer.INVALID_COMMAND_MESSAGE, result.errorMessage(),
                MessageDisplayer.ASSERT_EXPECTED_ERROR + "'" + input + "'");
    }
    /**
     * Ensures that commands that require arguments return an error when provided without arguments.
     *
     * @param input The command input missing required arguments.
     * @param commandName The expected command name (used for formatting the error message).
     */
    @ParameterizedTest
    @CsvSource({
        "'/add', add",
        "'/add ', add",
        "'/add\n', add",
        "'/delete', delete",
        "'/delete ', delete",
        "'/delete\n', delete"
    })
    void parseCommandWithoutArguments_returnsError(String input, String commandName) {
        ParseResult result = Parser.parseUserInput(input);

        assertFalse(result.isSuccess(), MessageDisplayer.ASSERT_FAILURE_PREFIX + "'" + input + "'");
        String expectedMessage = String.format(MessageDisplayer.ARG_EMPTY_MESSAGE_TEMPLATE, commandName);
        assertEquals(expectedMessage, result.errorMessage(),
                MessageDisplayer.ASSERT_EXPECTED_ERROR + "'" + input + "'");
    }
}
