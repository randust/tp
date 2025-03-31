package fintrek;

import fintrek.expense.core.RecurringExpenseManager;
import fintrek.expense.core.RegularExpenseManager;
import fintrek.misc.MessageDisplayer;
import fintrek.parser.CommandRouter;
import fintrek.parser.RouteResult;
import fintrek.util.RecurringExpenseProcessor;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FinTrek {
    private static final Logger logger = Logger.getLogger(FinTrek.class.getName());

    /**
     * Main entry-point for the java.fintrek.FinTrek application.
     */
    public static void main(String[] args) {
        logger.info("FinTrek application started.");

        System.out.println(MessageDisplayer.WELCOME_MESSAGE);
        System.out.println(MessageDisplayer.CONVERSATION_STARTER);

        //automatically check recurring expenses at the start
        RecurringExpenseProcessor.checkAndInsertDueExpenses(
                RecurringExpenseManager.getInstance(), RegularExpenseManager.getInstance());

        Scanner reader = new Scanner(System.in);
        String userInput = reader.nextLine().trim(); // get user input

        while (!userInput.equals(MessageDisplayer.END_CONVERSATION_MESSAGE)) {
            logger.log(Level.INFO, "going to start processing");
            logger.info("User input received: " + userInput);

            RouteResult result = CommandRouter.routeUserInput(userInput);

            if (result.isSuccess()) {
                System.out.println(result.outputMessage());
            } else {
                System.out.println(result.errorMessage());
                logger.warning("Parsing failed: " + result.errorMessage());
            }

            userInput = reader.nextLine().trim();
        }

        logger.info("FinTrek application shutting down.");
    }
}
