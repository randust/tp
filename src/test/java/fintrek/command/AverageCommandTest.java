package fintrek.command;

import fintrek.TestUtils;
import fintrek.misc.MessageDisplayer;
import org.junit.jupiter.api.BeforeEach;

import fintrek.expense.ExpenseManager;
import org.junit.jupiter.api.Test;

public class AverageCommandTest {
    /**
     * Clear all existing expenses in ExpenseManager before each test.
     */
    @BeforeEach
    public void setUp() {
        ExpenseManager.clearExpenses();
    }

    @Test
    public void testAverageCommandEmptyList() {
        AverageCommand averageCommand = new AverageCommand();
        CommandResult result = averageCommand.execute("");
        String expectedMessage = String.format(MessageDisplayer.AVERAGE_SUCCESS_MESSAGE_TEMPLATE, 0.0);

        TestUtils.assertCommandSuccess(result, MessageDisplayer.ASSERT_EMPTY_LIST);
        TestUtils.assertCommandMessage(result, MessageDisplayer.ASSERT_EMPTY_LIST, expectedMessage);
    }

    @Test
    public void testAverageCommandFilledList() {
        TestUtils.addConstantExpenses();
        AverageCommand averageCommand = new AverageCommand();
        CommandResult result = averageCommand.execute("");
        double expectedAverage = ExpenseManager.getAverageExpenses();
        String expectedMessage = String.format(MessageDisplayer.AVERAGE_SUCCESS_MESSAGE_TEMPLATE, expectedAverage);

        TestUtils.assertCommandSuccess(result, MessageDisplayer.ASSERT_FILLED_LIST);
        TestUtils.assertCommandMessage(result, MessageDisplayer.ASSERT_FILLED_LIST, expectedMessage);
    }
}
