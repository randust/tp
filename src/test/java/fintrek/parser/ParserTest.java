package fintrek.parser;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        RouteResult result = CommandRouter.routeUserInput(input);

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
        RouteResult result = CommandRouter.routeUserInput(input);

        assertFalse(result.isSuccess(), MessageDisplayer.ASSERT_FAILURE_PREFIX + "'" + input + "'");
        assertEquals(MessageDisplayer.INVALID_COMMAND_MESSAGE, result.errorMessage(),
                MessageDisplayer.ASSERT_EXPECTED_ERROR + "'" + input + "'");
    }

    /**
     * Tests that known valid command inputs are successfully parsed by the parser.
     * The test ensures that the parser returns a successful ParseResult (isSuccess = true)
     * and no error message (errorMessage = null) for each command in the list.
     */
    @ParameterizedTest
    @ValueSource(strings = {"/help", "/add", "/delete", "/total"})
    void parseKnownCommand_returnsTrue(String input) {
        RouteResult result = CommandRouter.routeUserInput(input);

        // Assert that parsing is successful
        assertTrue(result.isSuccess(), MessageDisplayer.ASSERT_SUCCESS_PREFIX + "'" + input + "'");

        // Assert that there is no error message
        assertNull(result.errorMessage(), MessageDisplayer.ASSERT_NULL_ERROR + "'" + input + "'");
    }

}
