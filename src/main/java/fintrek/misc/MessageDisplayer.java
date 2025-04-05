package fintrek.misc;

import fintrek.budget.BudgetManager;
import fintrek.expense.core.RecurringExpenseManager;
import fintrek.expense.core.RegularExpenseManager;
import fintrek.expense.service.AppServices;

/**
 * Utility class containing predefined messages for user interactions and command usage.
 */
public class MessageDisplayer {

    // General Messages
    public static final String WELCOME_MESSAGE = "Hi there, welcome to FinTrek! What can I do for you?";
    public static final String CONVERSATION_STARTER = "If you are new, please type /help to learn all the functions";
    public static final String END_CONVERSATION_MESSAGE = "bye";
    public static final String LANDING_MESSAGE_NONEMPTY_LIST = "This is your current list of expenses: %s";
    public static final String LANDING_MESSAGE_EMPTY_LIST = "You currently have no expenses. Add some now!";
    public static final String EXECUTING_COMMAND_MESSAGE = "Executing command: ";
    public static final String LANDING_MESSAGE_BUDGET_FOUND = "Your current monthly budget is $%.2f";
    public static final String LANDING_MESSAGE_BUDGET_NOT_FOUND = "You have not set a monthly budget yet. Set one now!";
    public static final String LANDING_MESSAGE_NONEMPTY_RECURRING_MSG =
            "Found some recurring expenses: %s";
    public static final String LANDING_MESSAGE_ADDING_RECURRING_MSG =
            "If they are due, adding them to the main list of expenses now.";
    public static final String LANDING_MESSAGE_EMPTY_RECURRING_MSG=
            "Found no recurring expenses.";
    public static final String LINE_SEPARATOR = "\n";

    // Error Messages
    public static final String INVALID_AMOUNT = "Amount must be positive";
    public static final String INVALID_LOAD_BUDGET_AMOUNT_MESSAGE =
            "Invalid Amount in Monthly Budget. Skipping the loading process for monthly budget.";
    public static final String INVALID_FORMAT_MESSAGE_TEMPLATE =
            "Invalid format. Please key in '/help %s' for more information";
    public static final String INVALID_DATE_MESSAGE =
            "Invalid date format. Please enter a valid date in the form \"dd-MM-yyyy\"";
    public static final String INVALID_DATE_DATA_MESSAGE =
            "Invalid data. The given date is not in the correct format.";
    public static final String INVALID_DATA_FORMAT_MESSAGE =
            "Invalid data format. Skipping over this line.";
    public static final String INVALID_AMT_MESSAGE = "Please enter a valid amount";
    public static final String INVALID_AMT_DATA_MESSAGE = "Expense with invalid amount found.";
    public static final String INVALID_IDX_MESSAGE = "Please enter a valid index";
    public static final String INVALID_IDX_FORMAT_MESSAGE = "Invalid index format. Please enter a valid index.";
    public static final String IDX_OUT_OF_BOUND_MESSAGE = "Index out of bound. Please enter a valid index.";
    public static final String IDX_EMPTY_MESSAGE = "Index cannot be empty";
    public static final String ERROR_CALCULATING_TOTAL_EXPENSES = "Error calculating total expenses: ";
    public static final String TOTAL_EXCEEDS_LIMIT = "Total expenses exceed $10 billion.";
    public static final String ERROR_CALCULATING_AVERAGE_EXPENSES = "Error calculating average expenses: ";
    public static final String NO_COMMAND_MESSAGE =
            "Please enter a command starting with '/'. Type '/help' for more information.";
    public static final String INVALID_COMMAND_MESSAGE =
            "Please enter a valid command. Type '/help' for more information.";
    public static final String ARG_EMPTY_MESSAGE_TEMPLATE = "Argument of '/%s' command cannot be empty";
    public static final String FILE_LOAD_ERROR_MESSAGE = "Error loading file %s: ";
    public static final String FILE_CREATION_ERROR_MESSAGE = "Error creating file %s: ";
    public static final String ERROR_SAVING_DATA_MESSAGE = "Error saving data: ";
    public static final String CATEGORY_ALREADY_EXISTS = "Category already exists";
    public static final String INVALID_CATEGORY_MESSAGE =
            "Invalid Category. Use '/add-category %s' to add as new category.";

    public static final String EDIT_FORMAT_HINT =
            "Invalid format. Usage: /edit [INDEX] [/d DESC] [/$ AMOUNT] [/c CATEGORY] [/dt DATE in DD-MM-YYYY]";
    public static final String EDIT_NO_FIELD_PROVIDED_MSG =
            "Please provide at least one field to edit using /d, /$, /c or /dt.";


    public static final String EMPTY_LIST_MESSAGE = "No expenses found";
    public static final String EMPTY_DESC_AND_AMT_MESSAGE = "Invalid input: Description and amount cannot be empty";
    public static final String INVALID_ADD_RECURRING_FORMAT_MESSAGE = "Invalid input: Description, amount or date " +
            "is in the wrong format or cannot be empty";
    public static final String EMPTY_DATA_MESSAGE = "Invalid data. The current line is empty.";
    public static final String EMPTY_DESC_DATA_MESSAGE = "Invalid data. The current expense has no description.";
    public static final String EMPTY_AMOUNT_DATA_MESSAGE = "Invalid data. The current expense has no amount.";
    public static final String EMPTY_CATEGORY_DATA_MESSAGE = "Invalid data. Category is null.";
    public static final String EMPTY_DATE_DATA_MESSAGE = "Invalid data. The current expense is not dated.";
    public static final String NULL_EXPENSE_ERROR = "Expense cannot be null";
    public static final String EMPTY_RECURRING_LIST_MESSAGE = "No recurring expenses found";
    public static final String EMPTY_DATE_MESSAGE = "No date for recurring expense found";
    public static final String WRONG_DATE_FORMAT_MESSAGE = "Please enter the date in the right format";
    public static final String ERROR_LOADING_SUMMARY = "Error loading summary: ";
    public static final String CATEGORY_NOT_FOUND = "Category not found. ";
    public static final String NULL_CATEGORY_MESSAGE = "Category cannot be null";
    public static final String EXCEEDED_BUDGET_MESSAGE =
            "WARNING: You have exceeded your monthly budget of $%.2f by $%.2f";
    public static final String ALMOST_EXCEEDED_BUDGET_MESSAGE =
            "WARNING: You are $%.2f short of reaching your monthly budget of $%.2f";
    public static final double MAX_AMOUNT = 10000000000D;

    // Success Messages
    public static final String ADD_SUCCESS_MESSAGE_TEMPLATE = "Expense added successfully: %s";
    public static final String ADD_CATEGORY_SUCCESS_MESSAGE_TEMPLATE = "Category added successfully: %s";
    public static final String SET_BUDGET_SUCCESS_MESSAGE_TEMPLATE =
            "Monthly budget successfully set to $%.2f";
    public static final String DELETE_SUCCESS_MESSAGE_TEMPLATE =
            "Expense %s deleted successfully. Remaining expenses: %d";
    public static final String DELETE_RECURRING_SUCCESS_MESSAGE_TEMPLATE =
            "Expense %s deleted successfully. Remaining recurring expenses: %d";
    public static final String EDIT_SUCCESS_MESSAGE_FORMAT = "Expense at index %d updated successfully:\n%s";
    public static final String TOTAL_SUCCESS_MESSAGE_TEMPLATE = "Total expenses: %.2f";
    public static final String TOTAL_RECURRING_SUCCESS_MESSAGE_TEMPLATE = "Total expenses: %.2f";
    public static final String AVERAGE_SUCCESS_MESSAGE_TEMPLATE = "Average expenses: %.2f";
    public static final String AVERAGE_RECURRING_SUCCESS_MESSAGE_TEMPLATE = "Average recurring expenses: %.2f";
    public static final String LIST_SUCCESS_MESSAGE_TEMPLATE = "List of expenses: %s";
    public static final String LIST_EXPENSE_FORMAT = "%n%d. %s";
    public static final String LIST_RECURRING_SUCCESS_MESSAGE_TEMPLATE = "List of recurring expenses: %s";
    public static final String CANNOT_BE_NULL_MESSAGE_TEMPLATE = "%s cannot be null";
    public static final String ADD_RECURRING_SUCCESS_MESSAGE_TEMPLATE = "Recurring expense added successfully: %s";
    public static final String LIST_SUMMARY_SUCCESS_MESSAGE_TEMPLATE = "Summary of expenses: %s";
    public static final String LIST_SUMMARY_RECURRING_SUCCESS_MESSAGE_TEMPLATE = "Summary of recurring expenses: %s";
    public static final String HIGHEST_CAT_AMT_FORMAT = "%s ($%.2f)";
    public static final String HIGHEST_CAT_FORMAT = "\n\n%-17s: %s";
    public static final String SUMMARY_HIGHEST_SPEND = "HIGHEST SPENDING";
    public static final String CAT_AMT_FORMAT = "\n%-17s: $%.2f";
    public static final String GRAND_TOTAL_FORMAT = "\n%-17s: $%.2f";
    public static final String SUMMARY_GRAND_TOTAL = "GRAND TOTAL";
    public static final String SORT_SUCCESS_MESSAGE_TEMPLATE = "Expenses sorted by %s (%s): %s";
    public static final String SORT_RECURR_SUCCESS_MESSAGE_TEMPLATE = "Recurring expenses sorted by %s (%s): %s";
    public static final String INVALID_SORT_FIELD = "Invalid sort field.";
    public static final String INVALID_SORT_DIRECTION = "Invalid sort direction.";

    // Assertion Messages
    public static final String ASSERT_FAILURE_PREFIX = "Parsing should fail for: ";
    public static final String ASSERT_EXPECTED_ERROR = "Expected error message mismatch for: ";
    public static final String ASSERT_SUCCESS_PREFIX = "Expected successful parsing for input: ";
    public static final String ASSERT_NULL_ERROR = "Expected no error message for input: ";

    public static final String ASSERT_COMMAND_SUCCESS_PREFIX = "Expected successful command execution for input: ";
    public static final String ASSERT_COMMAND_FAILURE_PREFIX = "Command execution should fail for input: ";
    public static final String ASSERT_COMMAND_EXPECTED_OUTPUT = "Expected output mismatch for: ";
    public static final String ASSERT_COMMAND_LIST_LENGTH_FAILURE = "Expected list length mismatch for input: ";
    public static final String ASSERT_COMMAND_DESC_FAILURE = "Added expense has unexpected description for input: ";
    public static final String ASSERT_COMMAND_AMT_FAILURE = "Added expense has unexpected amount for input: ";
    public static final String ASSERT_COMMAND_CATEGORY_FAILURE = "Added expense has unexpected category for input: ";

    public static final String ASSERT_GENERAL_HELP_MESSAGE = "General help message";
    public static final String ASSERT_FILLED_LIST = "Filled list";
    public static final String ASSERT_EMPTY_LIST = "Empty list";
    public static final String ASSERT_GET_DESC = "Get description";

    // HELP Messages
    public static final String HELP_UNKNOWN_TOPIC = "Unknown HELP topic.";
    public static final String HELP_AVAILABLE_COMMANDS = "Available commands:\n";

    // Format Messages

    public static final String SUMMARY_FORMAT_MESSAGE = """
            Format: /summary
            Lists all expenses grouped by category.
            Example: /summary returns all expenses categorized.
            """;

    public static final String POPPING_EXPENSE_AT_INDEX_MESSAGE = "Popping expense at index: ";
    public static final String NO_DESCRIPTION_AVAILABLE_MESSAGE = "No description available.";
    public static final String REQUIRES_STRUCTURED_PARSER_MESSAGE = "This command requires a structured parser.";
    public static final String STRUCTURED_EXECUTION_NOT_SUPPORTED_MESSAGE = "Structured execution not supported.";

    /**
     * Displays the welcome message to the user.
     */
    public static void displayWelcomeMessage() {
        System.out.println(MessageDisplayer.WELCOME_MESSAGE);
    }

    public static void displayExpensesLandingMessage() {
        if(RegularExpenseManager.getInstance().getLength() > 0) {
            System.out.println(String.format(
                    MessageDisplayer.LANDING_MESSAGE_NONEMPTY_LIST,
                    AppServices.REGULAR_REPORTER.listExpenses()) +
                    MessageDisplayer.LINE_SEPARATOR);
        } else {
            System.out.println(MessageDisplayer.LANDING_MESSAGE_EMPTY_LIST +
                    MessageDisplayer.LINE_SEPARATOR);
        }
    }

    public static void displayBudgetLandingMessage() {
        if(!BudgetManager.getInstance().isBudgetSet()) {
            System.out.println(MessageDisplayer.LANDING_MESSAGE_BUDGET_NOT_FOUND +
                    MessageDisplayer.LINE_SEPARATOR);
        } else {
            System.out.println(String.format(
                    MessageDisplayer.LANDING_MESSAGE_BUDGET_FOUND,
                    BudgetManager.getInstance().getBudget())
                    + MessageDisplayer.LINE_SEPARATOR);
        }
    }

    public static void displayRecurringExpensesLandingMessage() {
        if(RecurringExpenseManager.getInstance().getLength() > 0) {
            System.out.println(String.format(
                    MessageDisplayer.LANDING_MESSAGE_NONEMPTY_RECURRING_MSG,
                    AppServices.RECURRING_REPORTER.listExpenses()) +
                    MessageDisplayer.LINE_SEPARATOR);
            System.out.println(MessageDisplayer.LANDING_MESSAGE_ADDING_RECURRING_MSG +
                    MessageDisplayer.LINE_SEPARATOR);
        } else {
            System.out.println(MessageDisplayer.LANDING_MESSAGE_EMPTY_RECURRING_MSG +
                    MessageDisplayer.LINE_SEPARATOR);
        }
    }

}
