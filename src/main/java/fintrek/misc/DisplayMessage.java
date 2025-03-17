package fintrek.misc;

/**
 * Utility class containing predefined messages for user interactions and command usage.
 */
public class DisplayMessage {

    // General Messages
    public static final String WELCOME_MESSAGE = "Hi there, welcome to FinTrek! What can I do for you?";
    public static final String CONVERSATION_STARTER = "If you are new, please type /help to learn all the functions";
    public static final String END_CONVERSATION_MESSAGE = "bye";

    // Error Messages
    public static final String INVALID_AMOUNT = "Amount must be positive";
    public static final String INVALID_NUM_MESSAGE = "Please enter a valid number";
    public static final String ERROR_CALCULATING_TOTAL_EXPENSES = "Error calculating total expenses: ";
    public static final String ERROR_CALCULATING_AVERAGE_EXPENSES = "Error calculating average expenses: ";
    public static final String NO_COMMAND_MESSAGE =
            "Please enter a command starting with '/'. Type '/help' for more information.";
    public static final String INVALID_COMMAND_MESSAGE =
            "Please enter a valid command. Type '/help' for more information.";
    public static final String ARG_EMPTY_MESSAGE_TEMPLATE = "Argument of '/%s' command cannot be empty";
    public static final String EMPTY_LIST = "No expenses found";
    public static final String MISSING_DESCRIPTION = "Description cannot be empty";

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
    public static final String ADD_FORMAT_MESSAGE = """
            Format: /add [DESCRIPTION] $[AMOUNT]
            AMOUNT must be a positive number greater than 0
            Example: /add concert tickets $35.80 -
            """ + " adds an expense with description 'concert tickets' with the amount $35.80.";


    public static final String DELETE_FORMAT_MESSAGE = """
            Format: /delete [INDEX]
            INDEX must be a positive integer > 0
            Example: /delete 2 - deletes the expense with index number 2 on the list.
            """;

    public static final String TOTAL_FORMAT_MESSAGE = """
            Format: /total
            Returns sum of all expenses in the list, but will return 0 if the list is empty.
            Example: For a list of expenses: TransportExpense1, TransportExpense2, FoodExpense1
            /total returns (TransportExpense1 + TransportExpense2 + FoodExpense1).
            """;

    public static final String AVERAGE_FORMAT_MESSAGE = """
            Format: /average
            Returns average of all expenses in list, but will return 0 if the list is empty.
            Example: For a list of expenses: TransportExpense1, TransportExpense2, FoodExpense1
            /average returns (TransportExpense1 + TransportExpense2 + FoodExpense1) / 3.
            """;

    public static final String SUMMARY_FORMAT_MESSAGE = """
            Format: /summary
            Lists all expenses grouped by category.
            Example: /summary returns all expenses categorized.
            """;

    /**
     * Returns all available features as a single formatted string.
     *
     * @return A string containing all feature descriptions.
     */
    public static String getAllFeaturesMessage() {
        return HELP_AVAILABLE_COMMANDS + "\n" +
                ADD_FORMAT_MESSAGE + "\n" +
                DELETE_FORMAT_MESSAGE + "\n" +
                TOTAL_FORMAT_MESSAGE + "\n" +
                AVERAGE_FORMAT_MESSAGE + "\n" +
                SUMMARY_FORMAT_MESSAGE;
    }
}
