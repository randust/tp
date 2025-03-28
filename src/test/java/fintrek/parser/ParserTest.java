package fintrek.parser;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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
}
