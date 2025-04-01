//@@Charly2312
package fintrek.command.add;

import fintrek.command.registry.CommandResult;
import fintrek.expense.ExpenseManager;
import fintrek.expense.core.RecurringExpenseManager;
import fintrek.misc.MessageDisplayer;
import fintrek.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class AddRecurringCommandTest {
    /**
     * Clear all existing expenses in ExpenseManager and adds set list of expenses before each test.
     */
    @BeforeEach
    public void setUp() {
        ExpenseManager.clearRecurringExpenses();
    }

    @ParameterizedTest
    @ValueSource(strings = {"$1 /c transport", " ", "$2.5", "$", "bus $", "bus $ /c transport", "bus $1 /c", "         "})
    public void testAddRecurringCommand_commandWithWrongFormat_fail(String input) {
        AddRecurringCommand addCommand = new AddRecurringCommand();
        CommandResult result = addCommand.execute(input);

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandMessage(result, input, MessageDisplayer.INVALID_ADD_RECURRING_FORMAT_MESSAGE);
    }

    @ParameterizedTest
    @ValueSource(strings = {"invalid", "1.2.3", "-1", "2."})
    public void testAddRecurringCommand_invalidAmount_fail(String inputAmount) {
        AddRecurringCommand addCommand = new AddRecurringCommand();
        String input = "bus $" + inputAmount + "/c transport" + "01-01-2025";
        CommandResult result = addCommand.execute(input);

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandMessage(result, input, MessageDisplayer.INVALID_AMT_MESSAGE);
    }

    @ParameterizedTest
    @ValueSource(strings = {"20", "0.99", "45.67", "1.00", "1.0000"})
    public void testAddRecurringCommand_validAmount_success(String inputAmount) {
        AddRecurringCommand addCommand = new AddRecurringCommand();
        String input = "bus $" + inputAmount + " /c transport" + "01-01-2025";
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
    @ValueSource(strings = {"bus $1 01-01-2001", "bus$1 01-01-2001", "bus $ 1 01-01-2001"})
    public void testAddRecurringCommand_twoValidUncategorizedInputs_success(String input) {
        AddRecurringCommand addCommand = new AddRecurringCommand();
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
    public void testAddRecurringCommand_twoInvalidNoDateInputs_fail(String input) {
        AddRecurringCommand addCommand = new AddRecurringCommand();
        CommandResult result = addCommand.execute(input);

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandMessage(result, input, MessageDisplayer.INVALID_ADD_RECURRING_FORMAT_MESSAGE);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1-1-2025", "01-1-2025", "1-01-2025", "2025-1-1", "2025-01-01"})
    public void testAddRecurringCommand_twoInvalidWrongDateFormatInputs_fail(String inputDate) {
        AddRecurringCommand addCommand = new AddRecurringCommand();
        String input = "bus $" + "1" + " /c transport" + inputDate;
        CommandResult result = addCommand.execute(input);

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandMessage(result, input, MessageDisplayer.INVALID_ADD_RECURRING_FORMAT_MESSAGE);
    }
}
