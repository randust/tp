package fintrek.command.edit;

import fintrek.misc.MessageDisplayer;
import fintrek.util.TestUtils;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fintrek.command.registry.CommandResult;
import fintrek.expense.core.Expense;
import fintrek.expense.service.ExpenseService;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class EditCommandTest {

    private final ExpenseService service = TestUtils.regularService;

    /**
     * Clear all existing expenses in regularExpenseManager before each test.
     */
    @BeforeEach
    public void setUp() {
        service.clearExpenses();
    }

    /**
     * Clear all existing expenses in regularExpenseManager after each test.
     */
    @AfterEach
    public void tearDown() {
        service.clearExpenses();
    }

    /**
     * Verifies that edit command is successful when all input fields are valid.
     * Checks that description, amount, category, and date are updated correctly.
     */
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

    /**
     * Verifies that edit command is successful when only some fields are changed.
     * Checks that description is updated correctly when input only contains description.
     */
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

    /**
     * Verifies that invoking the edit command with an empty input
     * prints the appropriate error message.
     */
    @Test
    public void testEditEmptyInput() {
        EditCommand editCommand = new EditCommand(false);
        String input = "";
        CommandResult result = editCommand.execute(input);
        TestUtils.assertCommandFailure(result, input);
    }

    /**
     * Verifies that edit command fails for invalid input formats.
     */
    @Test
    public void testEditInvalidFormat() {
        service.addExpense(new Expense(TestUtils.VALID_DESCRIPTION, TestUtils.VALID_AMOUNT, TestUtils.VALID_CATEGORY,
                TestUtils.VALID_DATE));
        EditCommand editCommand = new EditCommand(false);
        String input = "invalid input format";
        CommandResult result = editCommand.execute(input);
        TestUtils.assertCommandFailure(result, input);
    }

    /**
     * Verifies that invoking the edit command with a valid index, but no
     * edit fields, will print the appropriate error message.
     */
    @Test
    public void testEditNoFieldsProvided() {
        service.addExpense(new Expense(TestUtils.VALID_DESCRIPTION, TestUtils.VALID_AMOUNT, TestUtils.VALID_CATEGORY,
                TestUtils.VALID_DATE));
        EditCommand editCommand = new EditCommand(false);
        String input = String.valueOf(TestUtils.VALID_INDEX);
        CommandResult result = editCommand.execute(input);
        TestUtils.assertCommandFailure(result, input);
    }

    /**
     * Verifies that attempting to edit the amount of an expense
     * into an invalid amount, such as a string "abc", will return an
     * appropriate error message.
     */
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

    /**
     * Verifies that attempting to edit the date of an expense
     * into an invalid date will return the appropriate error message.
     */
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

    /**
     * Verifies that attempting to edit an expense at an index which is out of
     * bounds or beyond the size of the current list will print the
     * appropriate error message.
     */
    @Test
    public void testEditIndexOutOfBound() {
        EditCommand editCommand = new EditCommand(false);
        String input = "5 /d something"; // No expense added yet
        CommandResult result = editCommand.execute(input);
        TestUtils.assertCommandFailure(result, input);
    }

    /**
     * Verifies that edit command is successful for inputs with whitespace
     * and that updated fields has been correctly trimmed.
     */
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

    /**
     * Verifies that invoking the edit command with a negative index fails.
     */
    @Test
    public void testEditNegativeIndex() {
        EditCommand editCommand = new EditCommand(false);
        String input = "-1 /d invalid";
        CommandResult result = editCommand.execute(input);
        TestUtils.assertCommandFailure(result, input);
    }

    /**
     * Verifies that edit command is successful for input with no description.
     * Checks that expense is updated with correct amount.
     */
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

    /**
     * Verifies that edit command also works properly for recurring expenses
     */
    @Test
    public void testEditRecurringExpense() {
        TestUtils.addConstantRecurringExpenses();
        EditCommand editCommand = new EditCommand(true);
        String input = "1 /d RecurringTest";
        CommandResult result = editCommand.execute(input);
        TestUtils.assertCommandSuccess(result, input);
        TestUtils.assertCorrectRecurringDesc(0, input, "RecurringTest");
    }

    /**
     * Tests the description of the edit command.
     * Verifies the command returns the correct description.
     */
    @ParameterizedTest
    @ValueSource (booleans = {true, false})
    public void testEditCommand_getDescription_success(boolean isRecurring) {
        EditCommand editCommand = new EditCommand(isRecurring);
        String formatString;
        String exampleString;
        if (isRecurring) {
            formatString =
                    "Format: /edit-recurring <INDEX> [/d <DESCRIPTION>] [/$ <AMOUNT>] [/c <CATEGORY>] [/dt <DATE>]";
            exampleString = """
                Example: /edit-recurring 2 /d dinner /$ 25 /c Dining /dt 25-12-2024 -
                         edits recurring expense 2 to have a description 'dinner' with the amount $25.00,
                         category 'DINING' and date '25-12-2024'.""";
        } else {
            formatString = "Format: /edit <INDEX> [/d <DESCRIPTION>] [/$ <AMOUNT>] [/c <CATEGORY>] [/dt <DATE>]";
            exampleString = """
                Example: /edit 2 /d dinner /$ 25 /c Dining /dt 25-12-2024 -
                         edits regular expense 2 to have a description 'dinner' with the amount $25.00,
                         category 'DINING' and date '25-12-2024'.""";
        }
        String expectedDescription = formatString + "\n" +
                """
                INDEX is the position of the expense in the list (from /list or /list-recurring).
                DESCRIPTION, AMOUNT, CATEGORY, DATE are optional arguments, but at least one field is required.
                """
                + exampleString;

        assertEquals(expectedDescription, editCommand.getDescription(),
                MessageDisplayer.ASSERT_COMMAND_EXPECTED_OUTPUT + MessageDisplayer.ASSERT_GET_DESC);
    }
}
