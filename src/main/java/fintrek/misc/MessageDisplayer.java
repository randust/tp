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
    public static final String IDX_EMPTY_MESSAGE = "Index cannot be empty";
    public static final String ERROR_CALCULATING_TOTAL_EXPENSES = "Error calculating total expenses: ";
    public static final String ERROR_CALCULATING_AVERAGE_EXPENSES = "Error calculating average expenses: ";
    public static final String NO_COMMAND_MESSAGE =
            "Please enter a command starting with '/'. Type '/help' for more information.";
    public static final String INVALID_COMMAND_MESSAGE =
            "Please enter a valid command. Type '/help' for more information.";
    public static final String ARG_EMPTY_MESSAGE_TEMPLATE = "Argument of '/%s' command cannot be empty";
  
    public static final String EMPTY_LIST_MESSAGE = "No expenses found";
    public static final String MISSING_DESC_AND_AMOUNT_MESSAGE = "Description and amount cannot be empty";
    public static final String NULL_EXPENSE_ERROR = "Expense cannot be null";


    // Success Messages
    public static final String ADD_SUCCESS_MESSAGE_TEMPLATE = "Expense added successfully: %s";
    public static final String DELETE_SUCCESS_MESSAGE_TEMPLATE = "Expense deleted successfully. Remaining expenses: %d";
    public static final String TOTAL_SUCCESS_MESSAGE_TEMPLATE = "Total expenses: %.2f";
    public static final String AVERAGE_SUCCESS_MESSAGE_TEMPLATE = "Average expenses: %.2f";
    public static final String LIST_SUCCESS_MESSAGE_TEMPLATE = "List of expenses: %s";

    // Assertion Messages
    public static final String ASSERT_FAILURE_PREFIX = "Parsing should fail for: ";
    public static final String ASSERT_EXPECTED_ERROR = "Expected error message mismatch for: ";

    // HELP Messages
    public static final String HELP_UNKNOWN_TOPIC = "Unknown HELP topic.";
    public static final String HELP_AVAILABLE_COMMANDS = "Available commands:\n";

    // Format Messages

    public static final String SUMMARY_FORMAT_MESSAGE = """
            Format: /summary
            Lists all expenses grouped by category.
            Example: /summary returns all expenses categorized.
            """;

}
