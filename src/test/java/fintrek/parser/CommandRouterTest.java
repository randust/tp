package fintrek.parser;

import fintrek.misc.MessageDisplayer;
import fintrek.util.TestCommandUtils;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CommandRouterTest {

    @BeforeAll
    void setupTestCommands() {
        TestCommandUtils.registerFakeCommands("help", "add", "delete", "total");
    }

    /**
     * Verifies that attempting to parse a user input without a forward slash returns error.
     * @param input user input without  "/" in the beginning.
     */
    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "\n  \n", "\t", "WithoutSlashInput WithoutSlashInput", "add", "help"})
    void parseWithoutSlashInput_returnsError(String input) {
        RouteResult result = CommandRouter.routeUserInput(input);
        assertFalse(result.isSuccess());
        assertEquals(MessageDisplayer.NO_COMMAND_MESSAGE, result.errorMessage());
    }

    /**
     * Verifies that parsing an input which has an unknown topic after '/' fails and
     * returns the appropriate error message.
     * @param input user input with an unknown or no topic after "/".
     */
    @ParameterizedTest
    @ValueSource(strings = {"/hello", "/addddd", "/", "/   ", "/ add"})
    void parseUnknownCommand_returnsError(String input) {
        RouteResult result = CommandRouter.routeUserInput(input);
        assertFalse(result.isSuccess());
        assertEquals(MessageDisplayer.INVALID_COMMAND_MESSAGE, result.errorMessage());
    }

    /**
     * Verifies that CommandRouter is able to parse a known command after "/" from the user input.
     * @param input user input with a known command after "/", e.g. help, add, etc.
     */
    @ParameterizedTest
    @ValueSource(strings = {"/help", "/add", "/delete", "/total"})
    void parseKnownCommand_returnsTrue(String input) {
        RouteResult result = CommandRouter.routeUserInput(input);
        assertTrue(result.isSuccess());
        assertNull(result.errorMessage());
    }
}
