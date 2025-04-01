package fintrek.command.add;

import fintrek.command.registry.CommandResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import fintrek.expense.ExpenseManager;
import fintrek.misc.MessageDisplayer;
import fintrek.util.TestUtils;

public class AddCommandTest {
    /**
     * Clear all existing expenses in ExpenseManager and adds set list of expenses before each test.
     */
    @BeforeEach
    public void setUp() {
        ExpenseManager.clearExpenses();
        TestUtils.addConstantExpenses();
    }

    @ParameterizedTest
    @ValueSource(strings = {"$1 /c transport", "", "$2.5", "$", "bus $", "bus $ /c transport", "bus $1 /c", "   "})
    public void testAddCommandInvalidFormat(String input) {
        AddCommand addCommand = new AddCommand(false);
        CommandResult result = addCommand.execute(input);

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandMessage(result, input, MessageDisplayer.INVALID_ADD_FORMAT_MESSAGE);
    }

    @ParameterizedTest
    @ValueSource(strings = {"invalid", "1.2.3", "-1", "2."})
    public void testAddCommandInvalidAmount(String inputAmount) {
        AddCommand addCommand = new AddCommand(false);
        String input = "bus $" + inputAmount + "/c transport";
        CommandResult result = addCommand.execute(input);

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandMessage(result, input, MessageDisplayer.INVALID_AMT_MESSAGE);
    }

    @ParameterizedTest
    @ValueSource(strings = {"20", "0.99", "45.67", "1.0"})
    public void testAddCommandValidAmount(String inputAmount) {
        AddCommand addCommand = new AddCommand(false);
        int initialSize = ExpenseManager.getLength();
        String input = "bus $" + inputAmount + " /c transport";
        CommandResult result = addCommand.execute(input);

        TestUtils.assertCommandSuccess(result, input);
        TestUtils.assertListSizeIncreased(initialSize, input);
        TestUtils.assertCorrectDesc(initialSize, input, "bus");
        TestUtils.assertCorrectAmount(initialSize, input, Double.parseDouble(inputAmount));
        TestUtils.assertCorrectCategory(initialSize, input, "TRANSPORT");
    }

    @ParameterizedTest
    @ValueSource(strings = {"bus $1", "bus$1", "bus $ 1"})
    public void testAddCommandTwoValidInputs(String input) {
        AddCommand addCommand = new AddCommand(false);
        int initialSize = ExpenseManager.getLength();
        CommandResult result = addCommand.execute(input);

        TestUtils.assertCommandSuccess(result, input);
        TestUtils.assertListSizeIncreased(initialSize, input);
        TestUtils.assertCorrectDesc(initialSize, input, "bus");
        TestUtils.assertCorrectAmount(initialSize, input, 1);
        TestUtils.assertCorrectCategory(initialSize, input, "UNCATEGORIZED");
    }

    @ParameterizedTest
    @ValueSource(strings = {"bus $1 /c transport", "bus $ 1 /c transport"})
    public void testAddCommandThreeValidInputs(String input) {
        AddCommand addCommand = new AddCommand(false);
        int initialSize = ExpenseManager.getLength();
        CommandResult result = addCommand.execute(input);

        TestUtils.assertCommandSuccess(result, input);
        TestUtils.assertListSizeIncreased(initialSize, input);
        TestUtils.assertCorrectDesc(initialSize, input, "bus");
        TestUtils.assertCorrectAmount(initialSize, input, 1);
        TestUtils.assertCorrectCategory(initialSize, input, "TRANSPORT");
    }
}
