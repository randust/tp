package fintrek.misc;

/**
 * Utility class containing predefined messages for user interactions and command usage.
 */
public class MessageDisplayer {

    // General Messages
    public static final String WELCOME_MESSAGE = "Hi there, welcome to FinTrek! What can I do for you?";
    public static final String CONVERSATION_STARTER = "If you are new, please type /help to learn all the functions";
    public static final String END_CONVERSATION_MESSAGE = "bye";

    // Error Messages
    public static final String INVALID_AMOUNT = "Amount must be positive";
    public static final String INVALID_AMT_MESSAGE = "Please enter a valid amount";
    public static final String INVALID_IDX_MESSAGE = "Please enter a valid index";
    public static final String INVALID_IDX_FORMAT_MESSAGE = "Invalid index format. Please enter a valid index.";
    public static final String IDX_OUT_OF_BOUND_MESSAGE = "Index out of bound. Please enter a valid index.";
    public static final String IDX_EMPTY_MESSAGE = "Index cannot be empty";
    public static final String ERROR_CALCULATING_TOTAL_EXPENSES = "Error calculating total expenses: ";
    public static final String ERROR_CALCULATING_AVERAGE_EXPENSES = "Error calculating average expenses: ";
    public static final String NO_COMMAND_MESSAGE =
            "Please enter a command starting with '/'. Type '/help' for more information.";
    public static final String INVALID_COMMAND_MESSAGE =
            "Please enter a valid command. Type '/help' for more information.";
    public static final String ARG_EMPTY_MESSAGE_TEMPLATE = "Argument of '/%s' command cannot be empty";

    public static final String EMPTY_LIST_MESSAGE = "No expenses found";
    public static final String INVALID_ADD_FORMAT_MESSAGE = "Invalid input: Description and amount cannot be empty";
    public static final String NULL_EXPENSE_ERROR = "Expense cannot be null";
    public static final String EMPTY_RECURRING_LIST_MESSAGE = "No recurring expenses found";
    public static final String EMPTY_DATE_MESSAGE = "No date for recurring expense found";
    public static final String WRONG_DATE_FORMAT_MESSAGE = "Please enter the date in the right format";
    public static final String ERROR_LOADING_SUMMARY = "Error loading summary: ";
    public static final String CATEGORY_NOT_FOUND = "Category not found";
    public static final String NULL_CATEGORY_MESSAGE = "Category cannot be null";

    // Success Messages
    public static final String ADD_SUCCESS_MESSAGE_TEMPLATE = "Expense added successfully: %s";
    public static final String DELETE_SUCCESS_MESSAGE_TEMPLATE =
            "Expense %s deleted successfully. Remaining expenses: %d";
    public static final String DELETE_RECURRING_SUCCESS_MESSAGE_TEMPLATE =
            "Expense %s deleted successfully. Remaining recurring expenses: %d";
    public static final String TOTAL_SUCCESS_MESSAGE_TEMPLATE = "Total expenses: %.2f";
    public static final String AVERAGE_SUCCESS_MESSAGE_TEMPLATE = "Average expenses: %.2f";
    public static final String LIST_SUCCESS_MESSAGE_TEMPLATE = "List of expenses: %s";
    public static final String LIST_RECURRING_SUCCESS_MESSAGE_TEMPLATE = "List of recurring expenses: %s";
    public static final String CANNOT_BE_NULL_MESSAGE_TEMPLATE = "%s cannot be null";
    public static final String ADD_RECURRING_SUCCESS_MESSAGE_TEMPLATE = "Recurring expense added successfully: %s";
    public static final String LIST_SUMMARY_SUCCESS_MESSAGE_TEMPLATE = "Summary of expenses: %s";
    public static final String SUMMARY_HIGHEST_SPENDING = "HIGHEST SPENDING";
    public static final String SUMMARY_GRAND_TOTAL = "GRAND TOTAL";

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

}
