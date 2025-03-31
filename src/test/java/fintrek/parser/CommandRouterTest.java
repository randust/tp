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

    @ParameterizedTest
    @ValueSource(strings = {"", "  ", "\n  \n", "\t", "WithoutSlashInput WithoutSlashInput", "add", "help"})
    void parseWithoutSlashInput_returnsError(String input) {
        RouteResult result = CommandRouter.routeUserInput(input);
        assertFalse(result.isSuccess());
        assertEquals(MessageDisplayer.NO_COMMAND_MESSAGE, result.errorMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"/hello", "/addddd", "/", "/   ", "/ add"})
    void parseUnknownCommand_returnsError(String input) {
        RouteResult result = CommandRouter.routeUserInput(input);
        assertFalse(result.isSuccess());
        assertEquals(MessageDisplayer.INVALID_COMMAND_MESSAGE, result.errorMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"/help", "/add", "/delete", "/total"})
    void parseKnownCommand_returnsTrue(String input) {
        RouteResult result = CommandRouter.routeUserInput(input);
        assertTrue(result.isSuccess());
        assertNull(result.errorMessage());
    }
}
