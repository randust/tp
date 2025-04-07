//@@Charly2312
package fintrek.command.add;

import fintrek.command.registry.CommandResult;
import fintrek.expense.core.Expense;
import fintrek.expense.core.RecurringExpenseManager;
import fintrek.misc.MessageDisplayer;
import fintrek.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

public class AddRecurringCommandTest {
    private static final RecurringExpenseManager recurringExpenseManager =
            RecurringExpenseManager.getInstance();
    /**
     * Clear all existing recurring expenses in the list before each test
     */
    @BeforeEach
    public void setUp() {
        RecurringExpenseManager.getInstance().clear();
    }

    /**
     * Tests if {@code AddRecurringCommand} returns an error for various invalid input formats
     *
     * @param input invalid inputs for the expense
     */
    @ParameterizedTest
    @ValueSource(strings = {
        "$1 /c transport", "$2.5", "$", "bus $",
        "bus $ /c transport", "bus $1 /c",
    })
    public void testAddRecurringCommand_commandWithWrongFormat_fail(String input) {
        AddCommand addCommand = new AddCommand(true);
        CommandResult result = addCommand.execute(input);

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandMessage(result, input,
                String.format(MessageDisplayer.INVALID_FORMAT_MESSAGE_TEMPLATE, "add"));
    }

    /**
     * Tests if {@code AddRecurringCommand} returns error messages for empty inputs or
     * inputs simply consisting of whitespaces
     * @param input empty inputs or inputs consisting of only whitespaces
     */
    @ParameterizedTest
    @ValueSource(strings = {
        "", "  "
    })
    public void testAddRecurringCommand_commandWithEmptyFormat_fail(String input) {
        AddCommand addCommand = new AddCommand(true);
        CommandResult result = addCommand.execute(input);

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandMessage(result, input,
                String.format(MessageDisplayer.ARG_EMPTY_MESSAGE_TEMPLATE, "add"));
    }

    /**
     * Tests {@code AddRecurringCommand} for invalid expense amounts
     *
     * @param inputAmount invalid expense amounts
     */
    @ParameterizedTest
    @ValueSource(strings = {"invalid", "1.2.3", "-1", "2."})
    public void testAddRecurringCommand_invalidAmount_fail(String inputAmount) {
        AddCommand addCommand = new AddCommand(true);
        String input = "bus $" + inputAmount + "/c transport" + "/dt 01-01-2025";
        CommandResult result = addCommand.execute(input);

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandMessage(result, input, MessageDisplayer.INVALID_AMT_MESSAGE);
    }

    /**
     * Tests the {@code AddRecurringCommand} for various forms of valid amounts
     *
     * @param inputAmount the amount of the expense to be added
     */
    @ParameterizedTest
    @ValueSource(strings = {"20", "0.99", "45.67", "1.00", "1.0000"})
    public void testAddRecurringCommand_validAmount_success(String inputAmount) {
        AddCommand addCommand = new AddCommand(true);
        String input = "bus $" + inputAmount + " /c transport" + "/dt 01-01-2025";
        CommandResult result = addCommand.execute(input);
        int size = recurringExpenseManager.getLength();
        int index = size - 1;

        TestUtils.assertCommandSuccess(result, input);
        TestUtils.assertCorrectRecurringListSize(size, input);
        TestUtils.assertCorrectRecurringDesc(index, input, "bus");
        TestUtils.assertCorrectRecurringAmount(index, input, Double.parseDouble(inputAmount));
        TestUtils.assertCorrectRecurringCategory(index, input, "TRANSPORT");
    }

    /**
     * Tests if {@code AddRecurringCommand} is able to deal with valid inputs with no category
     *
     * @param input valid inputs consisting of only description, amount, date
     */
    @ParameterizedTest
    @ValueSource(strings = {"bus $1 /dt 01-01-2001", "bus$1 /dt 01-01-2001", "bus $ 1"})
    public void testAddRecurringCommand_twoValidUncategorizedInputs_success(String input) {
        AddCommand addCommand = new AddCommand(true);
        CommandResult result = addCommand.execute(input);
        int size = recurringExpenseManager.getLength();
        int index = size - 1;

        TestUtils.assertCommandSuccess(result, input);
        TestUtils.assertCorrectRecurringListSize(size, input);
        TestUtils.assertCorrectRecurringDesc(index, input, "bus");
        TestUtils.assertCorrectRecurringAmount(index, input, 1);
        TestUtils.assertCorrectRecurringCategory(index, input, "UNCATEGORIZED");
    }

    /**
     * Tests {@code AddRecurringCommand} is successful for valid inputs with no date
     *
     * @param input valid inputs consisting of only description, amount, category
     */
    @ParameterizedTest
    @ValueSource(strings = {"bus $1 /c transport", "bus $ 1 /c transport"})
    public void testAddRecurringCommand_twoValidNoDateInputs_success(String input) {
        AddCommand addCommand = new AddCommand(true);
        CommandResult result = addCommand.execute(input);

        Expense newExpense = new Expense("bus", 1.00, "transport",
                LocalDate.now());

        TestUtils.assertCommandSuccess(result, input);
        TestUtils.assertCommandMessage(result, input,
                String.format(MessageDisplayer.ADD_RECURRING_SUCCESS_MESSAGE_TEMPLATE,
                        newExpense));
    }

    /**
     * Tests {@code AddRecurringCommand} for error handling when given invalid dates
     *
     * @param inputDate invalid inputs which are invalid dates
     */
    @ParameterizedTest
    @ValueSource(strings = {"1-1-2025", "01-1-2025", "1-01-2025", "2025-1-1", "2025-01-01"})
    public void testAddRecurringCommand_twoInvalidWrongDateFormatInputs_fail(String inputDate) {
        AddCommand addCommand = new AddCommand(true);
        String input = "bus $" + "1" + " /c transport /dt " + inputDate;
        CommandResult result = addCommand.execute(input);

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandMessage(result, input,
                String.format(MessageDisplayer.INVALID_DATE_MESSAGE));
    }
}
