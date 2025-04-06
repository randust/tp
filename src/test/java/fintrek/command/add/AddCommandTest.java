package fintrek.command.add;

import fintrek.command.registry.CommandResult;
import fintrek.command.summary.TotalCommand;
import fintrek.expense.core.RegularExpenseManager;
import fintrek.expense.service.ExpenseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import fintrek.misc.MessageDisplayer;
import fintrek.util.TestUtils;

import static fintrek.expense.service.AppServices.REGULAR_SERVICE;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

    /**
     * Tests if the Add Command returns error messages for empty inputs or
     * inputs simply consisting of whitespaces
     * @param input empty inputs or inputs consisting of only whitespaces
     */
    @ParameterizedTest
    @ValueSource(strings = {"", " ", "                               "})
    public void testAddCommandEmptyDescription(String input) {
        AddCommand addCommand = new AddCommand(false);
        CommandResult result = addCommand.execute(input);
        String message = String.format(MessageDisplayer.ARG_EMPTY_MESSAGE_TEMPLATE, COMMAND_NAME);

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandMessage(result, input, message);
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
    @ValueSource(strings = {"/dt 31-13-2025", "/dt 31-31-3131", "/dt 12-20-2020"})
    public void testAddCommandInvalidDateFormats(String input) {
        AddCommand addCommand = new AddCommand(false);
        String argumentTested = "beverage $5 /c food " + input;
        CommandResult result = addCommand.execute(argumentTested);

        TestUtils.assertCommandFailure(result, argumentTested);
        TestUtils.assertCommandMessage(result, input, MessageDisplayer.INVALID_DATE_MESSAGE);
    }

    /**
     * Tests the description of the add command.
     * Ensures the command returns the correct description.
     */
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void testAddCommand_getDescription_success(boolean isRecurring) {
        AddCommand addCommand = new AddCommand(isRecurring);
        String formatString;
        if (isRecurring) {
            formatString = "Format: /add-recurring <DESCRIPTION> $<AMOUNT> [/c <CATEGORY>] [/dt <DATE>]";
        } else {
            formatString = "Format: /add <DESCRIPTION> $<AMOUNT> [/c <CATEGORY>] [/dt <DATE>]";
        }
        String expectedDescription = formatString + "\n" +
                """
                AMOUNT must be a positive number greater than 0
                CATEGORY is an optional argument
                DATE is an optional argument which must be in the form dd-MM-yyyy
                Example: /add concert tickets $35.80 /c LEISURE /d [03-05-2025] -
                        adds an expense with description 'concert tickets' with the amount $35.80,
                        with the category 'LEISURE' and date '03-05-2025'.
                """;

        assertEquals(expectedDescription, addCommand.getDescription(),
                MessageDisplayer.ASSERT_COMMAND_EXPECTED_OUTPUT + MessageDisplayer.ASSERT_GET_DESC);
    }
}
