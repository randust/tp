package fintrek.command.edit;

import fintrek.util.TestUtils;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fintrek.command.registry.CommandResult;
import fintrek.expense.core.Expense;
import fintrek.expense.service.AppServices;
import fintrek.expense.service.ExpenseService;

public class EditCommandTest {

    private final ExpenseService service = AppServices.REGULAR_SERVICE;


    @BeforeEach
    public void setUp() {
        service.clearExpenses();
    }

    @AfterEach
    public void tearDown() {
        service.clearExpenses();
    }

    @Test
    public void testEditSuccessAllFields() {
        service.addExpense(new Expense(TestUtils.VALID_DESCRIPTION, TestUtils.VALID_AMOUNT, TestUtils.VALID_CATEGORY,
                TestUtils.VALID_DATE));
        EditCommand editCommand = new EditCommand(false);

        String input = TestUtils.VALID_INDEX + " /d " + TestUtils.UPDATED_DESCRIPTION_FULL + " /$ " +
                TestUtils.UPDATED_AMOUNT + " /c " + TestUtils.UPDATED_CATEGORY + " /dt " +
                TestUtils.UPDATED_DATE.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        CommandResult result = editCommand.execute(input);

        TestUtils.assertCommandSuccess(result, input);
        TestUtils.assertCorrectDesc(0, input, TestUtils.UPDATED_DESCRIPTION_FULL);
        TestUtils.assertCorrectAmount(0, input, TestUtils.UPDATED_AMOUNT);
        TestUtils.assertCorrectCategory(0, input, TestUtils.UPDATED_CATEGORY.toUpperCase());
        assertEquals(TestUtils.UPDATED_DATE, service.getExpense(0).getDate());
    }

    @Test
    public void testEditSuccessPartialUpdate() {
        service.addExpense(new Expense(TestUtils.VALID_DESCRIPTION, TestUtils.VALID_AMOUNT, TestUtils.VALID_CATEGORY,
                TestUtils.VALID_DATE));
        EditCommand editCommand = new EditCommand(false);

        String input = TestUtils.VALID_INDEX + " /d " + TestUtils.UPDATED_DESCRIPTION;
        CommandResult result = editCommand.execute(input);

        TestUtils.assertCommandSuccess(result, input);
        TestUtils.assertCorrectDesc(0, input, TestUtils.UPDATED_DESCRIPTION);
        TestUtils.assertCorrectAmount(0, input, TestUtils.VALID_AMOUNT);
        TestUtils.assertCorrectCategory(0, input, TestUtils.VALID_CATEGORY.toUpperCase());
        assertEquals(TestUtils.VALID_DATE, service.getExpense(0).getDate());
    }

    @Test
    public void testEditEmptyInput() {
        EditCommand editCommand = new EditCommand(false);
        String input = "";
        CommandResult result = editCommand.execute(input);
        TestUtils.assertCommandFailure(result, input);
    }

    @Test
    public void testEditInvalidFormat() {
        service.addExpense(new Expense(TestUtils.VALID_DESCRIPTION, TestUtils.VALID_AMOUNT, TestUtils.VALID_CATEGORY,
                TestUtils.VALID_DATE));
        EditCommand editCommand = new EditCommand(false);
        String input = "invalid input format";
        CommandResult result = editCommand.execute(input);
        TestUtils.assertCommandFailure(result, input);
    }

    @Test
    public void testEditNoFieldsProvided() {
        service.addExpense(new Expense(TestUtils.VALID_DESCRIPTION, TestUtils.VALID_AMOUNT, TestUtils.VALID_CATEGORY,
                TestUtils.VALID_DATE));
        EditCommand editCommand = new EditCommand(false);
        String input = String.valueOf(TestUtils.VALID_INDEX);
        CommandResult result = editCommand.execute(input);
        TestUtils.assertCommandFailure(result, input);
    }

    @Test
    public void testEditInvalidAmount() {
        service.addExpense(new Expense(TestUtils.VALID_DESCRIPTION, TestUtils.VALID_AMOUNT, TestUtils.VALID_CATEGORY,
                TestUtils.VALID_DATE));
        EditCommand editCommand = new EditCommand(false);
        String input = TestUtils.VALID_INDEX + " /d " + TestUtils.UPDATED_DESCRIPTION + " /$ " +
                TestUtils.INVALID_AMOUNT_INPUT;
        CommandResult result = editCommand.execute(input);
        TestUtils.assertCommandFailure(result, input);
    }

    @Test
    public void testEditInvalidDate() {
        service.addExpense(new Expense(TestUtils.VALID_DESCRIPTION, TestUtils.VALID_AMOUNT, TestUtils.VALID_CATEGORY,
                TestUtils.VALID_DATE));
        EditCommand editCommand = new EditCommand(false);
        String input = TestUtils.VALID_INDEX + " /d " + TestUtils.UPDATED_DESCRIPTION + " /dt " +
                TestUtils.INVALID_DATE_INPUT;
        CommandResult result = editCommand.execute(input);
        TestUtils.assertCommandFailure(result, input);
    }

    @Test
    public void testEditIndexOutOfBound() {
        EditCommand editCommand = new EditCommand(false);
        String input = "5 /d something"; // No expense added yet
        CommandResult result = editCommand.execute(input);
        TestUtils.assertCommandFailure(result, input);
    }

    @Test
    public void testEditTrimsInputFields() {
        service.addExpense(new Expense(TestUtils.VALID_DESCRIPTION, TestUtils.VALID_AMOUNT, TestUtils.VALID_CATEGORY,
                TestUtils.VALID_DATE));
        EditCommand editCommand = new EditCommand(false);
        String input = TestUtils.VALID_INDEX + " /d   " + TestUtils.UPDATED_DESCRIPTION + "   /$  " +
                TestUtils.UPDATED_AMOUNT + "  ";
        CommandResult result = editCommand.execute(input);
        TestUtils.assertCommandSuccess(result, input);
        TestUtils.assertCorrectDesc(0, input, TestUtils.UPDATED_DESCRIPTION);
        TestUtils.assertCorrectAmount(0, input, TestUtils.UPDATED_AMOUNT);
    }

    @Test
    public void testEditNegativeIndex() {
        EditCommand editCommand = new EditCommand(false);
        String input = "-1 /d invalid";
        CommandResult result = editCommand.execute(input);
        TestUtils.assertCommandFailure(result, input);
    }

    @Test
    public void testEditSuccessNoDescription() {
        service.addExpense(new Expense(TestUtils.VALID_DESCRIPTION, TestUtils.VALID_AMOUNT, TestUtils.VALID_CATEGORY,
                TestUtils.VALID_DATE));
        EditCommand editCommand = new EditCommand(false);
        String input = TestUtils.VALID_INDEX + " /$ " + TestUtils.UPDATED_AMOUNT;
        CommandResult result = editCommand.execute(input);
        TestUtils.assertCommandSuccess(result, input);
        TestUtils.assertCorrectAmount(0, input, TestUtils.UPDATED_AMOUNT);
    }

    @Test
    public void testEditRecurringExpense() {
        TestUtils.addConstantRecurringExpenses();
        EditCommand editCommand = new EditCommand(true);
        String input = "1 /d RecurringTest";
        CommandResult result = editCommand.execute(input);
        TestUtils.assertCommandSuccess(result, input);
        TestUtils.assertCorrectRecurringDesc(0, input, "RecurringTest");
    }
}
