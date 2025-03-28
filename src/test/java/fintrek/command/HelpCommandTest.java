package fintrek.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import fintrek.misc.MessageDisplayer;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class HelpCommandTest {
    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    public void testHelpCommandGeneral(String input) {
        HelpCommand helpCommand = new HelpCommand();
        CommandResult result = helpCommand.execute(input);

        assertTrue(result.isSuccess());
        assertEquals(CommandRegistry.getAllCommandDescriptions(), result.message());
    }

    @ParameterizedTest
    @ValueSource(strings = {"add", "ADD", " add ", "adddd", "/add"})
    public void testHelpCommandAdd(String input) {
        HelpCommand helpCommand = new HelpCommand();
        CommandResult result = helpCommand.execute(input);

        assertTrue(result.isSuccess());
        assertEquals(CommandRegistry.getCommand("add").getDescription(), result.message());
    }

    @ParameterizedTest
    @ValueSource(strings = {"delete", "DELETE", " delete ", "deleteee", "/delete"})
    public void testHelpCommandDelete(String input) {
        HelpCommand helpCommand = new HelpCommand();
        CommandResult result = helpCommand.execute(input);

        assertTrue(result.isSuccess());
        assertEquals(CommandRegistry.getCommand("delete").getDescription(), result.message());
    }

    @ParameterizedTest
    @ValueSource(strings = {"total", "TOTAL", " total ", "totalll", "/total"})
    public void testHelpCommandTotal(String input) {
        HelpCommand helpCommand = new HelpCommand();
        CommandResult result = helpCommand.execute(input);

        assertTrue(result.isSuccess());
        assertEquals(CommandRegistry.getCommand("total").getDescription(), result.message());
    }

    @ParameterizedTest
    @ValueSource(strings = {"average", "AVERAGE", " average ", "averageee", "/average"})
    public void testHelpCommandAverage(String input) {
        HelpCommand helpCommand = new HelpCommand();
        CommandResult result = helpCommand.execute(input);

        assertTrue(result.isSuccess());
        assertEquals(CommandRegistry.getCommand("average").getDescription(), result.message());
    }

    @ParameterizedTest
    @ValueSource(strings = {"hello", "delet", "help"})
    public void testHelpCommandUnknownTopic(String input) {
        HelpCommand helpCommand = new HelpCommand();
        CommandResult result = helpCommand.execute(input);

        assertTrue(result.isSuccess());
        assertEquals(MessageDisplayer.HELP_UNKNOWN_TOPIC, result.message());
    }
}
