package fintrek.ui;

import fintrek.expense.core.RecurringExpenseManager;
import fintrek.expense.core.RegularExpenseManager;
import fintrek.expense.service.AppServices;
import fintrek.misc.MessageDisplayer;
import fintrek.parser.CommandRouter;
import fintrek.parser.RouteResult;
import fintrek.util.RecurringExpenseProcessor;
import fintrek.data.DataHandler;
import fintrek.expense.core.BudgetManager;

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
    public FinTrekUi() {
        this.reader = new Scanner(System.in);
    }

    /**
     * Starts the UI, displays welcome messages, loads data,
     * and begins the input processing loop.
     */
    public void start() {
        displayWelcomeMessage();
        loadInitialData();
        processRecurringExpenses();
        runCommandLoop();
    }

    /**
     * Displays the welcome message to the user.
     */
    private void displayWelcomeMessage() {
        System.out.println(MessageDisplayer.WELCOME_MESSAGE);
    }

    /**
     * Loads data from storage and displays appropriate initial messages.
     */
    private void loadInitialData() {
        DataHandler.loadData();
        if(!BudgetManager.getInstance().isBudgetSet()
                && RegularExpenseManager.getInstance().getLength() == 0) {
            System.out.println(MessageDisplayer.LANDING_MESSAGE_BUDGET_NOT_FOUND);
            System.out.println(MessageDisplayer.LANDING_MESSAGE_EMPTY_LIST);
            System.out.println(MessageDisplayer.CONVERSATION_STARTER);
        } else {
            if(BudgetManager.getInstance().isBudgetSet()) {
                System.out.println(String.format(
                        MessageDisplayer.LANDING_MESSAGE_BUDGET_FOUND,
                        BudgetManager.getInstance().getBudget()
                ));
            }
            if(RegularExpenseManager.getInstance().getLength() > 0) {
                System.out.println(String.format(
                        MessageDisplayer.LANDING_MESSAGE_NONEMPTY_LIST,
                        AppServices.REGULAR_REPORTER.listExpenses()));
            }
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
        String userInput = reader.nextLine().trim(); // get user input

        while (!userInput.equals(MessageDisplayer.END_CONVERSATION_MESSAGE)) {
            logger.log(Level.INFO, "Going to start processing");
            logger.info("User input received: " + userInput);

            processUserInput(userInput);
            userInput = reader.nextLine().trim();
        }
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
            logger.warning("Parsing failed: " + result.errorMessage());
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
