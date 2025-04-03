package fintrek.command.add;

import fintrek.command.registry.CommandResult;
import fintrek.expense.core.RegularExpenseManager;
import fintrek.expense.service.ExpenseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import fintrek.util.ExpenseManager;
import fintrek.misc.MessageDisplayer;
import fintrek.util.TestUtils;

import static fintrek.AppServices.REGULAR_SERVICE;

public class AddCommandTest {
    /**
     * Clear all existing expenses in ExpenseManager and adds set list of expenses before each test.
     */
    public static final String COMMAND_NAME = "add";
    private ExpenseService service;

    @BeforeEach
    public void setUp() {
        RegularExpenseManager.getInstance().clear();
        service = REGULAR_SERVICE;
        TestUtils.addConstantExpenses();
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "                               "})
    public void testAddCommandEmptyDescription(String input) {
        AddCommand addCommand = new AddCommand(false);
        CommandResult result = addCommand.execute(input);

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandMessage(result, input,
                MessageDisplayer.EMPTY_DESC_AND_AMT_MESSAGE);
    }

    /**
     * Tests if AddCommand returns an error for various invalid input formats
     *
     * @param input invalid inputs for the expense
     */
    @ParameterizedTest
    @ValueSource(strings = {
            "$1 /c transport", "$2.5", "$", "bus $", "bus $ /c transport", "bus $1 /c",
            "food $5 /c cat $d 0303-31-31", "food $3 /d 3131-3131-3131", "coffee $5 /d /d +65-1234-5678",
            "food $5 /c uncat /d today"})
    public void testAddCommandInvalidFormat(String input) {
        AddCommand addCommand = new AddCommand(false);
        CommandResult result = addCommand.execute(input);

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandMessage(result, input,
                String.format(MessageDisplayer.INVALID_FORMAT_MESSAGE_TEMPLATE, COMMAND_NAME));
    }

    /**
     * Tests the AddCommand for invalid expense amounts
     *
     * @param inputAmount invalid expense amounts
     */
    @ParameterizedTest
    @ValueSource(strings = {"invalid", "1.2.3", "-1", "2."})
    public void testAddCommandInvalidAmount(String inputAmount) {
        AddCommand addCommand = new AddCommand(false);
        String input = "bus $" + inputAmount + "/c transport";
        CommandResult result = addCommand.execute(input);

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandMessage(result, input, MessageDisplayer.INVALID_AMT_MESSAGE);
    }

    /**
     * Tests the AddCommand for various forms of valid amounts
     *
     * @param inputAmount the amount of the expense to be added
     */
    @ParameterizedTest
    @ValueSource(strings = {"20", "0.99", "45.67", "1.0"})
    public void testAddCommandValidAmount(String inputAmount) {
        AddCommand addCommand = new AddCommand(false);
        int initialSize = service.countExpenses();
        String input = "bus $" + inputAmount + " /c transport";
        CommandResult result = addCommand.execute(input);

        TestUtils.assertCommandSuccess(result, input);
        TestUtils.assertListSizeIncreased(initialSize, input);
        TestUtils.assertCorrectDesc(initialSize, input, "bus");
        TestUtils.assertCorrectAmount(initialSize, input, Double.parseDouble(inputAmount));
        TestUtils.assertCorrectCategory(initialSize, input, "TRANSPORT");
    }

    /**
     * Tests if AddCommand is able to deal with complications regarding whitespaces
     *
     * @param input valid inputs consisting of issues with the whitespaces
     */
    @ParameterizedTest
    @ValueSource(strings = {"bus $1", "bus$1", "bus $ 1"})
    public void testAddCommandTwoValidInputs(String input) {
        AddCommand addCommand = new AddCommand(false);
        int initialSize = service.countExpenses();
        CommandResult result = addCommand.execute(input);

        TestUtils.assertCommandSuccess(result, input);
        TestUtils.assertListSizeIncreased(initialSize, input);
        TestUtils.assertCorrectDesc(initialSize, input, "bus");
        TestUtils.assertCorrectAmount(initialSize, input, 1);
        TestUtils.assertCorrectCategory(initialSize, input, "UNCATEGORIZED");
    }

    /**
     * Tests if AddCommand is able to deal with complications regarding whitespaces
     *
     * @param input valid inputs consisting of issues with the whitespaces
     */
    @ParameterizedTest
    @ValueSource(strings = {"bus $1 /c transport", "bus $ 1 /c transport"})
    public void testAddCommandThreeValidInputs(String input) {
        AddCommand addCommand = new AddCommand(false);
        int initialSize = service.countExpenses();
        CommandResult result = addCommand.execute(input);

        TestUtils.assertCommandSuccess(result, input);
        TestUtils.assertListSizeIncreased(initialSize, input);
        TestUtils.assertCorrectDesc(initialSize, input, "bus");
        TestUtils.assertCorrectAmount(initialSize, input, 1);
        TestUtils.assertCorrectCategory(initialSize, input, "TRANSPORT");
    }

    /**
     * Tests AddCommand for error handling when given invalid dates
     *
     * @param input invalid inputs which are invalid dates
     */
    @ParameterizedTest
    @ValueSource(strings = {"/d 31-13-2025", "/d 31-31-3131", "/d 12-20-2020"})
    public void testAddCommandInvalidDateFormats(String input) {
        AddCommand addCommand = new AddCommand(false);
        String argumentTested = "Food $5 /c Beverages " + input;
        CommandResult result = addCommand.execute(argumentTested);

        TestUtils.assertCommandFailure(result, argumentTested);
        TestUtils.assertCommandMessage(result, input, MessageDisplayer.INVALID_DATE_MESSAGE);
    }
}
