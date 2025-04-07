package fintrek.command.help;

import fintrek.command.registry.CommandRegistry;
import fintrek.command.registry.CommandResult;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

import fintrek.misc.MessageDisplayer;
import fintrek.util.TestUtils;

/**
 * Unit tests for the {@code HelpCommand} class.
 * Ensures that the help information is correctly displayed.
 */
public class HelpCommandTest {
    /**
     * Tests the help command with general input.
     * Verifies that empty or whitespace inputs return the general help description.
     * @param isRecurring Whether to test with recurring expenses (true) or regular expenses (false)
     * @param input The input to test the general help command.
     */
    @ParameterizedTest
    @CsvSource({
        "true,",
        "true, ",
        "false,",
        "false, "
    })
    public void testHelpCommand_general_success(boolean isRecurring, String input) {
        HelpCommand helpCommand = new HelpCommand(isRecurring);
        CommandResult result = helpCommand.execute(input);
        String expectedMessage = CommandRegistry.getAllCommandDescriptions();

        TestUtils.assertCommandSuccess(result, input);
        TestUtils.assertCommandMessage(result, MessageDisplayer.ASSERT_GENERAL_HELP_MESSAGE, expectedMessage);
    }

    /**
     * Tests the help command for the "add" command.
     * Verifies that the inputs variations for "add" return the "add" command description.
     * @param isRecurring Whether to test with recurring expenses (true) or regular expenses (false)
     * @param input The input to test the help command for "add".
     */
    @ParameterizedTest
    @CsvSource({
        "true, add-recurring",
        "true, ADD-RECURRING",
        "true,  add-recurring ",
        "true, add-recurring       ",
        "false, add",
        "false, ADD",
        "false,  add ",
        "false, add       "
    })
    public void testHelpCommand_add_success(boolean isRecurring, String input) {
        HelpCommand helpCommand = new HelpCommand(isRecurring);
        CommandResult result = helpCommand.execute(input);
        String expectedMessage;
        if (isRecurring) {
            expectedMessage = CommandRegistry.getCommand("add-recurring").getDescription();
        } else {
            expectedMessage = CommandRegistry.getCommand("add").getDescription();
        }

        TestUtils.assertCommandSuccess(result, input);
        TestUtils.assertCommandMessage(result, input, expectedMessage);
    }

    /**
     * Tests the help command for the "add" command.
     * Verifies that the inputs variations for "add" return the "add" command description.
     * @param isRecurring Whether to test with recurring expenses (true) or regular expenses (false)
     * @param input The input to test the help command for "add".
     */
    @ParameterizedTest
    @CsvSource({
        "true, add-recurringggg",
        "true, /add-recurring",
        "true, hiadd-recurringhi",
        "false, adddd",
        "false, /add",
        "false, hiaddhi"
    })
    public void testHelpCommand_typoAdd_fail(boolean isRecurring, String input) {
        HelpCommand helpCommand = new HelpCommand(isRecurring);
        CommandResult result = helpCommand.execute(input);

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandMessage(result, input, MessageDisplayer.HELP_UNKNOWN_TOPIC);
    }

    /**
     * Tests the help command for the "delete" command.
     * Verifies that the inputs variations for "delete" return the "delete" command description.
     * @param isRecurring Whether to test with recurring expenses (true) or regular expenses (false)
     * @param input The input to test the help command for "delete".
     */
    @ParameterizedTest
    @CsvSource({
        "true, delete-recurring",
        "true, DELETE-RECURRING",
        "true,  delete-recurring ",
        "true, delete-recurring       ",
        "false, delete",
        "false, DELETE",
        "false,  delete ",
        "false, delete       "
    })
    public void testHelpCommand_delete_success(boolean isRecurring, String input) {
        HelpCommand helpCommand = new HelpCommand(isRecurring);
        CommandResult result = helpCommand.execute(input);
        String expectedMessage;
        if (isRecurring) {
            expectedMessage = CommandRegistry.getCommand("delete-recurring").getDescription();
        } else {
            expectedMessage = CommandRegistry.getCommand("delete").getDescription();
        }
        TestUtils.assertCommandSuccess(result, input);
        TestUtils.assertCommandMessage(result, input, expectedMessage);
    }

    /**
     * Tests the help command for the "delete" command.
     * Verifies that the inputs wrong variations for "delete"
     *              do not return the "delete" command description.
     * @param isRecurring Whether to test with recurring expenses (true) or regular expenses (false)
     * @param input The input to test the help command for "delete".
     */
    @ParameterizedTest
    @CsvSource({
        "true, delete-recurringggg",
        "true, /delete-recurring",
        "true, byedelete-recurringbye",
        "false, deleteee",
        "false, /delete",
        "false, byedeletebye"
    })
    public void testHelpCommand_typoDelete_fail(boolean isRecurring, String input) {
        HelpCommand helpCommand = new HelpCommand(isRecurring);
        CommandResult result = helpCommand.execute(input);

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandMessage(result, input, MessageDisplayer.HELP_UNKNOWN_TOPIC);
    }

    /**
     * Tests the help command for the "total" command.
     * Verifies that the inputs variations for "total" return the "total" command description.
     * @param isRecurring Whether to test with recurring expenses (true) or regular expenses (false)
     * @param input The input to test the help command for "total".
     */
    @ParameterizedTest
    @CsvSource({
        "true, total-recurring",
        "true, TOTAL-RECURRING",
        "true,  total-recurring ",
        "true, total-recurring       ",
        "false, total",
        "false, TOTAL",
        "false,  total ",
        "false, total       "
    })
    public void testHelpCommand_total_success(boolean isRecurring, String input) {
        HelpCommand helpCommand = new HelpCommand(isRecurring);
        CommandResult result = helpCommand.execute(input);
        String expectedMessage;
        if (isRecurring) {
            expectedMessage = CommandRegistry.getCommand("total-recurring").getDescription();
        } else {
            expectedMessage = CommandRegistry.getCommand("total").getDescription();
        }
        TestUtils.assertCommandSuccess(result, input);
        TestUtils.assertCommandMessage(result, input, expectedMessage);
    }

    /**
     * Tests the help command for the "total" command.
     * Verifies that the inputs wrong variations for "total"
     *              do not return the "total" command description.
     * @param isRecurring Whether to test with recurring expenses (true) or regular expenses (false)
     * @param input The input to test the help command for "total".
     */
    @ParameterizedTest
    @CsvSource({
        "true, total-recurringggg",
        "true, /total-recurring",
        "true, hitotal-recurringhi",
        "false, totalll",
        "false, /total",
        "false, hitotalhi"
    })
    public void testHelpCommand_typoTotal_fail(boolean isRecurring, String input) {
        HelpCommand helpCommand = new HelpCommand(isRecurring);
        CommandResult result = helpCommand.execute(input);

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandMessage(result, input, MessageDisplayer.HELP_UNKNOWN_TOPIC);
    }

    /**
     * Tests the help command for the "budget" command.
     * Ensures that the inputs variations for "budget" return the "budget" command description.
     * @param isRecurring Whether to test with recurring expenses (true) or regular expenses (false)
     * @param input The input to test the help command for "budget".
     */
    @ParameterizedTest
    @CsvSource({
        "true, BUDGET",
        "true, BuDgET",
        "true, budget",
        "true, budget      ",
        "false, BUDGET",
        "false, BuDgET",
        "false, budget",
        "false, budget      "
    })
    public void testHelpCommand_budget_success(boolean isRecurring, String input) {
        HelpCommand helpCommand = new HelpCommand(isRecurring);
        CommandResult result = helpCommand.execute(input);
        String expectedMessage = CommandRegistry.getCommand("budget").getDescription();

        TestUtils.assertCommandSuccess(result, input);
        TestUtils.assertCommandMessage(result, input, expectedMessage);
    }

    /**
     * Tests the help command for the "budget" command.
     * Ensures that the inputs wrong variations for "budget"
     *              do not return the "budget" command description.
     * @param input The input to test the help command for "budget".
     */
    @ParameterizedTest
    @CsvSource({
        "true, bbbbudget",
        "true, budgetttt",
        "true, /budget",
        "true, budget?",
        "true, hibudgethi",
        "false, bbbbudget",
        "false, budgetttt",
        "false, /budget",
        "false, budget?",
        "false, hibudgethi"
    })
    public void testHelpCommand_typoBudget_fail(boolean isRecurring, String input) {
        HelpCommand helpCommand = new HelpCommand(isRecurring);
        CommandResult result = helpCommand.execute(input);

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandMessage(result, input, MessageDisplayer.HELP_UNKNOWN_TOPIC);
    }

    /**
     * Tests the help command for the "average" command.
     * Verifies that the inputs variations for "average" return the "average" command description.
     * @param isRecurring Whether to test with recurring expenses (true) or regular expenses (false)
     * @param input The input to test the help command for "average".
     */
    @ParameterizedTest
    @CsvSource({
        "true, average-recurring",
        "true, AVERAGE-RECURRING",
        "true,  average-recurring ",
        "true, average-recurring       ",
        "false, average",
        "false, AVERAGE",
        "false,  average ",
        "false, average       "
    })
    public void testHelpCommand_average_success(boolean isRecurring, String input) {
        HelpCommand helpCommand = new HelpCommand(isRecurring);
        CommandResult result = helpCommand.execute(input);
        String expectedMessage;
        if (isRecurring) {
            expectedMessage = CommandRegistry.getCommand("average-recurring").getDescription();
        } else {
            expectedMessage = CommandRegistry.getCommand("average").getDescription();
        }
        TestUtils.assertCommandSuccess(result, input);
        TestUtils.assertCommandMessage(result, input, expectedMessage);
    }

    /**
     * Tests the help command for the "average" command.
     * Verifies that the inputs wrong variations for "average"
     *              do not return the "average" command description.
     * @param isRecurring Whether to test with recurring expenses (true) or regular expenses (false)
     * @param input The input to test the help command for "average".
     */
    @ParameterizedTest
    @CsvSource({
        "true, average-recurringggg",
        "true, /average-recurring",
        "true, hiaverage-recurringhi",
        "false, averageee",
        "false, /average",
        "false, hiaveragehi",
    })
    public void testHelpCommand_typoAverage_success(boolean isRecurring, String input) {
        HelpCommand helpCommand = new HelpCommand(isRecurring);
        CommandResult result = helpCommand.execute(input);

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandMessage(result, input, MessageDisplayer.HELP_UNKNOWN_TOPIC);
    }

    /**
     * Tests the help command for the "summary" command.
     * Verifies that the inputs variations for "summary" return the "summary" command description.
     * @param isRecurring Whether to test with recurring expenses (true) or regular expenses (false)
     * @param input The input to test the help command for "summary".
     */
    @ParameterizedTest
    @CsvSource({
        "true, summary-recurring",
        "true, SUMMARY-RECURRING",
        "true,  summary-recurring ",
        "true, summary-recurring       ",
        "false, summary",
        "false, SUMMARY",
        "false,  summary ",
        "false, summary       "
    })
    public void testHelpCommand_summary_success(boolean isRecurring, String input) {
        HelpCommand helpCommand = new HelpCommand(isRecurring);
        CommandResult result = helpCommand.execute(input);
        String expectedMessage;
        if (isRecurring) {
            expectedMessage = CommandRegistry.getCommand("summary-recurring").getDescription();
        } else {
            expectedMessage = CommandRegistry.getCommand("summary").getDescription();
        }
        TestUtils.assertCommandSuccess(result, input);
        TestUtils.assertCommandMessage(result, input, expectedMessage);
    }

    /**
     * Tests the help command for the "summary" command.
     * Verifies that the inputs variations for "summary" return the "summary" command description.
     * @param isRecurring Whether to test with recurring expenses (true) or regular expenses (false)
     * @param input The input to test the help command for "summary".
     */
    @ParameterizedTest
    @CsvSource({
        "true, summary-recurringggg",
        "true, /summary-recurring",
        "true, hisummary-recurringhi",
        "false, summaryyy",
        "false, /summary",
        "false, hisummaryhi",
    })
    public void testHelpCommand_typoSummary_success(boolean isRecurring, String input) {
        HelpCommand helpCommand = new HelpCommand(isRecurring);
        CommandResult result = helpCommand.execute(input);

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandMessage(result, input, MessageDisplayer.HELP_UNKNOWN_TOPIC);
    }

    /**
     * Tests the help command for the "edit" command.
     * Verifies that the inputs variations for "edit" return the "edit" command description.
     * @param isRecurring Whether to test with recurring expenses (true) or regular expenses (false)
     * @param input The input to test the help command for "edit".
     */
    @ParameterizedTest
    @CsvSource({
        "true, edit-recurring",
        "true, EDIT-RECURRING",
        "true,  edit-recurring ",
        "true, edit-recurring       ",
        "false, edit",
        "false, EDIT",
        "false,  edit ",
        "false, edit       "
    })
    public void testHelpCommand_edit_success(boolean isRecurring, String input) {
        HelpCommand helpCommand = new HelpCommand(isRecurring);
        CommandResult result = helpCommand.execute(input);
        String expectedMessage;
        if (isRecurring) {
            expectedMessage = CommandRegistry.getCommand("edit-recurring").getDescription();
        } else {
            expectedMessage = CommandRegistry.getCommand("edit").getDescription();
        }
        TestUtils.assertCommandSuccess(result, input);
        TestUtils.assertCommandMessage(result, input, expectedMessage);
    }

    /**
     * Tests the help command for the "edit" command.
     * Verifies that the inputs variations for "edit" return the "edit" command description.
     * @param isRecurring Whether to test with recurring expenses (true) or regular expenses (false)
     * @param input The input to test the help command for "edit".
     */
    @ParameterizedTest
    @CsvSource({
        "true, edit-recurringggg",
        "true, /edit-recurring",
        "true, hiedit-recurringhi",
        "false, edittt",
        "false, /edit",
        "false, hiedithi",
    })
    public void testHelpCommand_typoEdit_success(boolean isRecurring, String input) {
        HelpCommand helpCommand = new HelpCommand(isRecurring);
        CommandResult result = helpCommand.execute(input);

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandMessage(result, input, MessageDisplayer.HELP_UNKNOWN_TOPIC);
    }

    /**
     * Tests the help command for the "list" command.
     * Verifies that the inputs variations for "list" return the "list" command description.
     * @param isRecurring Whether to test with recurring expenses (true) or regular expenses (false)
     * @param input The input to test the help command for "list".
     */
    @ParameterizedTest
    @CsvSource({
        "true, list-recurring",
        "true, LIST-RECURRING",
        "true,  list-recurring ",
        "true, list-recurring       ",
        "false, list",
        "false, LIST",
        "false,  list ",
        "false, list       "
    })
    public void testHelpCommand_list_success(boolean isRecurring, String input) {
        HelpCommand helpCommand = new HelpCommand(isRecurring);
        CommandResult result = helpCommand.execute(input);
        String expectedMessage;
        if (isRecurring) {
            expectedMessage = CommandRegistry.getCommand("list-recurring").getDescription();
        } else {
            expectedMessage = CommandRegistry.getCommand("list").getDescription();
        }
        TestUtils.assertCommandSuccess(result, input);
        TestUtils.assertCommandMessage(result, input, expectedMessage);
    }

    /**
     * Tests the help command for the "list" command.
     * Verifies that the inputs variations for "list" return the "list" command description.
     * @param isRecurring Whether to test with recurring expenses (true) or regular expenses (false)
     * @param input The input to test the help command for "list".
     */
    @ParameterizedTest
    @CsvSource({
        "true, list-recurringggg",
        "true, /list-recurring",
        "true, hilist-recurringhi",
        "false, listtt",
        "false, /list",
        "false, hislisthi",
    })
    public void testHelpCommand_typoList_success(boolean isRecurring, String input) {
        HelpCommand helpCommand = new HelpCommand(isRecurring);
        CommandResult result = helpCommand.execute(input);

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandMessage(result, input, MessageDisplayer.HELP_UNKNOWN_TOPIC);
    }

    /**
     * Tests the help command for the "list-sort" command.
     * Verifies that the inputs variations for "list-sort" return the "list-sort" command description.
     * @param isRecurring Whether to test with recurring expenses (true) or regular expenses (false)
     * @param input The input to test the help command for "list-sort".
     */
    @ParameterizedTest
    @CsvSource({
        "true, list-sort-recurring",
        "true, LIST-SORT-RECURRING",
        "true,  list-sort-recurring ",
        "true, list-sort-recurring       ",
        "false, list-sort",
        "false, LIST-SORT",
        "false,  list-sort ",
        "false, list-sort       "
    })
    public void testHelpCommand_listSort_success(boolean isRecurring, String input) {
        HelpCommand helpCommand = new HelpCommand(isRecurring);
        CommandResult result = helpCommand.execute(input);
        String expectedMessage;
        if (isRecurring) {
            expectedMessage = CommandRegistry.getCommand("list-sort-recurring").getDescription();
        } else {
            expectedMessage = CommandRegistry.getCommand("list-sort").getDescription();
        }
        TestUtils.assertCommandSuccess(result, input);
        TestUtils.assertCommandMessage(result, input, expectedMessage);
    }

    /**
     * Tests the help command for the "list-sort" command.
     * Verifies that the inputs variations for "list-sort" return the "list-sort" command description.
     * @param isRecurring Whether to test with recurring expenses (true) or regular expenses (false)
     * @param input The input to test the help command for "list-sort".
     */
    @ParameterizedTest
    @CsvSource({
        "true, list-sort-recurringggg",
        "true, /list-sort-recurring",
        "true, hilist-sort-recurringhi",
        "false, list-sorttt",
        "false, /list-sort",
        "false, hilist-sorthi",
    })
    public void testHelpCommand_typoListSort_success(boolean isRecurring, String input) {
        HelpCommand helpCommand = new HelpCommand(isRecurring);
        CommandResult result = helpCommand.execute(input);

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandMessage(result, input, MessageDisplayer.HELP_UNKNOWN_TOPIC);
    }

    /**
     * Tests the help command for the "add-category" command.
     * Ensures that the inputs variations for "add-category" return the "add-category" command description.
     * @param isRecurring Whether to test with recurring expenses (true) or regular expenses (false)
     * @param input The input to test the help command for "add-category".
     */
    @ParameterizedTest
    @CsvSource({
        "true, ADD-CATEGORY",
        "true, add-category",
        "true, add-category      ",
        "false, ADD-CATEGORY",
        "false, add-category",
        "false, add-category      "
    })
    public void testHelpCommand_addCategory_success(boolean isRecurring, String input) {
        HelpCommand helpCommand = new HelpCommand(isRecurring);
        CommandResult result = helpCommand.execute(input);
        String expectedMessage = CommandRegistry.getCommand("add-category").getDescription();

        TestUtils.assertCommandSuccess(result, input);
        TestUtils.assertCommandMessage(result, input, expectedMessage);
    }

    /**
     * Tests the help command for the "add-category" command.
     * Ensures that the inputs wrong variations for "add-category"
     *              do not return the "add-category" command description.
     * @param input The input to test the help command for "add-category".
     */
    @ParameterizedTest
    @CsvSource({
        "true, add-categoryyyy",
        "true, /add-category",
        "true, add-category?",
        "true, hiadd-categoryhi",
        "false, add-categoryyyy",
        "false, /add-category",
        "false, add-category?",
        "false, hiadd-categoryhi"
    })
    public void testHelpCommand_typoAddCategory_fail(boolean isRecurring, String input) {
        HelpCommand helpCommand = new HelpCommand(isRecurring);
        CommandResult result = helpCommand.execute(input);

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandMessage(result, input, MessageDisplayer.HELP_UNKNOWN_TOPIC);
    }

    /**
     * Tests the help command for the "list-category" command.
     * Ensures that the inputs variations for "list-category" return the "list-category" command description.
     * @param isRecurring Whether to test with recurring expenses (true) or regular expenses (false)
     * @param input The input to test the help command for "list-category".
     */
    @ParameterizedTest
    @CsvSource({
        "true, LIST-CATEGORY",
        "true, list-category",
        "true, list-category      ",
        "false, LIST-CATEGORY",
        "false, list-category",
        "false, list-category      "
    })
    public void testHelpCommand_listCategory_success(boolean isRecurring, String input) {
        HelpCommand helpCommand = new HelpCommand(isRecurring);
        CommandResult result = helpCommand.execute(input);
        String expectedMessage = CommandRegistry.getCommand("list-category").getDescription();

        TestUtils.assertCommandSuccess(result, input);
        TestUtils.assertCommandMessage(result, input, expectedMessage);
    }

    /**
     * Tests the help command for the "list-category" command.
     * Ensures that the inputs wrong variations for "list-category"
     *              do not return the "list-category" command description.
     * @param input The input to test the help command for "list-category".
     */
    @ParameterizedTest
    @CsvSource({
        "true, list-categoryyyy",
        "true, /list-category",
        "true, list-category?",
        "true, hilist-categoryhi",
        "false, list-categoryyyy",
        "false, /list-category",
        "false, list-category?",
        "false, hilist-categoryhi"
    })
    public void testHelpCommand_typoListCategory_fail(boolean isRecurring, String input) {
        HelpCommand helpCommand = new HelpCommand(isRecurring);
        CommandResult result = helpCommand.execute(input);

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandMessage(result, input, MessageDisplayer.HELP_UNKNOWN_TOPIC);
    }

    /**
     * Tests the help command for invalid commands.
     * Verifies that unrecognized commands returns an error.
     * @param isRecurring Whether to test with recurring expenses (true) or regular expenses (false)
     * @param input The invalid command input.
     */
    @ParameterizedTest
    @CsvSource({
        "true, hello",
        "true, delet",
        "true, brother",
        "true, 321",
        "true, fooaddfoo",
        "true, baraveragebar",
        "false, hello",
        "false, delet",
        "false, brother",
        "false, 321",
        "false, fooaddfoo",
        "false, baraveragebar"
    })
    public void testHelpCommand_unknownTopic_returnsError(boolean isRecurring, String input) {
        HelpCommand helpCommand = new HelpCommand(isRecurring);
        CommandResult result = helpCommand.execute(input);
        String expectedMessage = MessageDisplayer.HELP_UNKNOWN_TOPIC;

        TestUtils.assertCommandFailure(result, input);
        TestUtils.assertCommandErrorMessage(result, input, expectedMessage);
    }

    /**
     * Tests the description of help command.
     * Verifies the command returns the correct description.
     * @param isRecurring Whether to test with recurring expenses (true) or regular expenses (false)
     */
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    public void testHelpCommand_getDescription_success(boolean isRecurring) {
        HelpCommand command = new HelpCommand(isRecurring);
        String expectedDescription = """
            Format: /help [COMMAND]
            Displays help message for all commands.
            Optionally pass a keyword to show usage for a specific command.""";

        assertEquals(expectedDescription, command.getDescription(),
                MessageDisplayer.ASSERT_COMMAND_EXPECTED_OUTPUT + MessageDisplayer.ASSERT_GET_DESC);
    }
}
