package fintrek.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import fintrek.misc.DisplayMessage;
import org.junit.jupiter.api.Test;

public class CommandTest {
    @Test
    public void testDeleteCommandInvalidInput() {
        ExecutionResult result = Command.DELETE.execute("invalid");
        assertFalse(result.isSuccess());
        assertEquals(DisplayMessage.INVALID_NUM_MESSAGE, result.message());
    }

    @Test
    public void testDeleteCommandOutOfBounds() {
        ExecutionResult result = Command.DELETE.execute("999");  // Assuming ExpenseManager has fewer items
        assertFalse(result.isSuccess());
        assertEquals(DisplayMessage.INVALID_NUM_MESSAGE, result.message());
    }

    @Test
    public void testDeleteCommandValidInput() {
        //TODO: implement after having ADD
    }
}
