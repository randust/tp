package fintrek.ui;

import fintrek.expense.core.RecurringExpenseManager;
import fintrek.expense.core.RegularExpenseManager;
import fintrek.misc.MessageDisplayer;
import fintrek.parser.CommandRouter;
import fintrek.parser.RouteResult;
import fintrek.util.RecurringExpenseProcessor;
import fintrek.data.DataHandler;
import fintrek.budget.BudgetManager;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles the user interface of the FinTrek application.
 * Responsible for displaying messages to the user, accepting input,
 * processing commands, and showing results.
 */
public class FinTrekUi {
    private static final Logger logger = Logger.getLogger(FinTrekUi.class.getName());
    private final Scanner reader;


    /**
     * Constructs a new FinTrekUI with a Scanner for reading user input.
     */
    public FinTrekUi() {this.reader = new Scanner(System.in);}

    /**
     * Starts the UI, displays welcome messages, loads data,
     * and begins the input processing loop.
     */
    public void start() {
        MessageDisplayer.displayWelcomeMessage();
        loadInitialData();
        runCommandLoop();
    }


    /**
     * Loads data from storage and displays appropriate initial messages.
     * If the user has no loaded monthly budget or expenses, they are
     * prompted with a message to type /help to learn of the commands
     * if they are new
     */
    private void loadInitialData() {
        DataHandler.loadData();
        MessageDisplayer.displayBudgetLandingMessage();
        MessageDisplayer.displayRecurringExpensesLandingMessage();
        processRecurringExpenses();
        MessageDisplayer.displayExpensesLandingMessage();
        if(RegularExpenseManager.getInstance().getLength() == 0 &&
            !BudgetManager.getInstance().isBudgetSet()) {
            System.out.println(MessageDisplayer.CONVERSATION_STARTER);
        }
    }

    /**
     * Processes recurring expenses at application startup.
     */
    private void processRecurringExpenses() {
        RecurringExpenseProcessor.checkAndInsertDueExpenses(
                RecurringExpenseManager.getInstance(), RegularExpenseManager.getInstance());
    }

    /**
     * Runs the main command loop, accepting and processing user input
     * until the exit command is given.
     */
    private void runCommandLoop() {
        System.out.print("> ");
        //System.out.println(MessageDisplayer.ASK_FOR_INPUT);
        //System.out.print(MessageDisplayer.ARROW_FOR_INPUT);
        String userInput = reader.nextLine().trim(); // get user input

        while (!userInput.equals(MessageDisplayer.END_CONVERSATION_MESSAGE)) {
            logger.log(Level.FINE, "Going to start processing");
            logger.log(Level.FINE, "User input received: " + userInput);

            processUserInput(userInput);
            System.out.println();
            System.out.print("> ");
            //System.out.println(MessageDisplayer.ASK_FOR_INPUT);
            //System.out.print(MessageDisplayer.ARROW_FOR_INPUT);
            userInput = reader.nextLine().trim();
        }

        System.out.println(MessageDisplayer.BYE_MESSAGE);
    }

    /**
     * Processes a single user input command, routing it to CommandRouter
     * and displaying the result.
     *
     * @param userInput The command string entered by the user
     */
    private void processUserInput(String userInput) {
        RouteResult result = CommandRouter.routeUserInput(userInput);

        if (result.isSuccess()) {
            System.out.println(result.outputMessage());
            DataHandler.saveData();
        } else {
            System.out.println(result.errorMessage());
            logger.log(Level.FINE, "Parsing failed: " + result.errorMessage());
        }
    }

    /**
     * Closes scanner used by the UI.
     * Should be called when the application is shutting down.
     */
    public void close() {
        reader.close();
    }
}
