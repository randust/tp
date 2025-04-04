//@@Charly2312
package fintrek.command.add;

import fintrek.command.registry.CommandResult;
import fintrek.expense.core.Expense;
import fintrek.util.ExpenseManager;
import fintrek.misc.MessageDisplayer;
import fintrek.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

public class AddRecurringCommandTest {
    /**
     * Clear all existing expenses in ExpenseManager and adds set list of expenses before each test.
     */
    @BeforeEach
    public void setUp() {
        ExpenseManager.clearRecurringExpenses();
    }

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

    @ParameterizedTest
    @ValueSource(strings = {
        "", "  "
    })
    public void testAddRecurringCommand_commandWithEmptyFormat_fail(String input) {
        AddCommand addCommand = new AddCommand(true);
        CommandResult result = addCommand.execute(input);

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandMessage(result, input,
                String.format(MessageDisplayer.EMPTY_DESC_AND_AMT_MESSAGE));
    }

    @ParameterizedTest
    @ValueSource(strings = {"invalid", "1.2.3", "-1", "2."})
    public void testAddRecurringCommand_invalidAmount_fail(String inputAmount) {
        AddCommand addCommand = new AddCommand(true);
        String input = "bus $" + inputAmount + "/c transport" + "/d 01-01-2025";
        CommandResult result = addCommand.execute(input);

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandMessage(result, input, MessageDisplayer.INVALID_AMT_MESSAGE);
    }

    @ParameterizedTest
    @ValueSource(strings = {"20", "0.99", "45.67", "1.00", "1.0000"})
    public void testAddRecurringCommand_validAmount_success(String inputAmount) {
        AddCommand addCommand = new AddCommand(true);
        String input = "bus $" + inputAmount + " /c transport" + "/d 01-01-2025";
        CommandResult result = addCommand.execute(input);
        int size = ExpenseManager.checkRecurringExpenseSize();
        int index = size - 1;

        TestUtils.assertCommandSuccess(result, input);
        TestUtils.assertCorrectRecurringListSize(size, input);
        TestUtils.assertCorrectRecurringDesc(index, input, "bus");
        TestUtils.assertCorrectRecurringAmount(index, input, Double.parseDouble(inputAmount));
        TestUtils.assertCorrectRecurringCategory(index, input, "TRANSPORT");
    }

    @ParameterizedTest
    @ValueSource(strings = {"bus $1 /d 01-01-2001", "bus$1 /d 01-01-2001", "bus $ 1"})
    public void testAddRecurringCommand_twoValidUncategorizedInputs_success(String input) {
        AddCommand addCommand = new AddCommand(true);
        CommandResult result = addCommand.execute(input);
        int size = ExpenseManager.checkRecurringExpenseSize();
        int index = size - 1;

        TestUtils.assertCommandSuccess(result, input);
        TestUtils.assertCorrectRecurringListSize(size, input);
        TestUtils.assertCorrectRecurringDesc(index, input, "bus");
        TestUtils.assertCorrectRecurringAmount(index, input, 1);
        TestUtils.assertCorrectRecurringCategory(index, input, "UNCATEGORIZED");
    }

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

    @ParameterizedTest
    @ValueSource(strings = {"1-1-2025", "01-1-2025", "1-01-2025", "2025-1-1", "2025-01-01"})
    public void testAddRecurringCommand_twoInvalidWrongDateFormatInputs_fail(String inputDate) {
        AddCommand addCommand = new AddCommand(true);
        String input = "bus $" + "1" + " /c transport /d " + inputDate;
        CommandResult result = addCommand.execute(input);

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandMessage(result, input,
                String.format(MessageDisplayer.INVALID_FORMAT_MESSAGE_TEMPLATE, "add"));
    }
}
