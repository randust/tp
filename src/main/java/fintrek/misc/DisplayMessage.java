package fintrek.misc;

public class DisplayMessage {
    public static final String WELCOME_MESSAGE = "Hi there, welcome to FinTrek! What can I do for you?";
    public static final String INVALID_AMOUNT = "Amount cannot be negative";
    public static final String ERROR_CALCULATING_TOTAL_EXPENSES = "Error calculating total expenses: ";
    public static final String DISPLAY_TOTAL_MESSAGE = "Total expenses: ";
    public static final String INVALID_COMMAND_MESSAGE = "Please enter the correct command";
    public static final String CONVERSATION_STARTER = "If you are new, please type /help to learn all the functions";
    public static final String END_CONVERSATION_MESSAGE = "bye";
    public static final String ARG_EMPTY_MESSAGE = "Arguments cannot be empty";
    public static final String INVALID_NUM_MESSAGE = "Please enter a valid number";
    public static final String ADD_EXPENSE = "Expense added: ";

    public static void addFormatPrinter() {
        System.out.println("Format: /add [DESCRIPTION] $[AMOUNT]"); //format of using the specific function
        System.out.println("AMOUNT must be a positive number greater than 0"); //description of format
        System.out.println("Example: /add concert tickets $35.80 - adds an expense with description 'concert tickets' with the amount $'35.80'."); //example
    }

    public static void deleteFormatPrinter() {
        System.out.println("Format: /delete [INDEX]"); //format of using the specific function
        System.out.println("INDEX must be a positive integer > 0"); //description of format
        System.out.println("Example: /delete 2 - deletes the expense with index number 2 on the list"); //example
    }


    public static void totalFormatPrinter() {
        System.out.println("Format to total up all expenses: /total"); //format of using the specific function
        System.out.println("Returns sum of all expense in the list, but will return 0 if the list is empty"); //description of format
        System.out.println("""
                            Example: For a list of expenses: TransportExpense1, TransportExpense2, FoodExpense1\s
                            /total returns (TransportExpense1 + TransportExpense2 + FoodExpense1)
                            """); //example
    }

    public static void averageFormatPrinter() {
        System.out.println("Format: /average"); //format of using the specific function
        System.out.println("Returns average of all expenses in list, but will return 0 if list is empty"); //description of format
        System.out.println("""
                            Example: For a list of expenses: TransportExpense1, TransportExpense2, FoodExpense1
                            /average returns (TransportExpense1 + TransportExpense2 + FoodExpense1) / 3
                            """); //example
    }

    public static void summaryFormatPrinter() {
        System.out.println("Formatt: /summary"); //format of using the specific function
        System.out.println("List out all expenses grouped by each category"); //description of format
        System.out.println("Example: /summary returns all expenses in a list"); //example
    }

    public static void allFeaturesPrinter() {
        addFormatPrinter();
        deleteFormatPrinter();
        totalFormatPrinter();
        averageFormatPrinter();
        summaryFormatPrinter();
    }
}
