package fintrek.command.summary;

import fintrek.expense.service.ExpenseReporter;
import fintrek.expense.service.ExpenseService;
import fintrek.util.TestUtils;
import fintrek.command.registry.CommandResult;
import fintrek.misc.MessageDisplayer;
import org.junit.jupiter.api.BeforeEach;

import fintrek.util.ExpenseManager;
import org.junit.jupiter.api.Test;

import static fintrek.AppServices.REGULAR_REPORTER;
import static fintrek.AppServices.REGULAR_SERVICE;

public class AverageCommandTest {
    private ExpenseService service;
    private ExpenseReporter reporter;

    /**
     * Clear all existing expenses in ExpenseManager before each test.
     */
    @BeforeEach
    public void setUp() {
        service = REGULAR_SERVICE;
        reporter = REGULAR_REPORTER;
        service.clearExpenses();
    }

    @Test
    public void testAverageCommandEmptyList() {
        AverageCommand averageCommand = new AverageCommand(false);
        CommandResult result = averageCommand.execute("");
        String expectedMessage = String.format(MessageDisplayer.AVERAGE_SUCCESS_MESSAGE_TEMPLATE, 0.0);

        TestUtils.assertCommandSuccess(result, MessageDisplayer.ASSERT_EMPTY_LIST);
        TestUtils.assertCommandMessage(result, MessageDisplayer.ASSERT_EMPTY_LIST, expectedMessage);
    }

    @Test
    public void testAverageCommandFilledList() {
        TestUtils.addConstantExpenses();
        AverageCommand averageCommand = new AverageCommand(false);
        CommandResult result = averageCommand.execute("");
        double expectedAverage = reporter.getAverage();
        String expectedMessage = String.format(MessageDisplayer.AVERAGE_SUCCESS_MESSAGE_TEMPLATE, expectedAverage);

        TestUtils.assertCommandSuccess(result, MessageDisplayer.ASSERT_FILLED_LIST);
        TestUtils.assertCommandMessage(result, MessageDisplayer.ASSERT_FILLED_LIST, expectedMessage);
    }
}
