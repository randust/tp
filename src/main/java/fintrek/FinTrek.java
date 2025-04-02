package fintrek;

import fintrek.expense.core.RecurringExpenseManager;
import fintrek.expense.core.RegularExpenseManager;
import fintrek.misc.MessageDisplayer;
import fintrek.parser.CommandRouter;
import fintrek.parser.RouteResult;
import fintrek.util.RecurringExpenseProcessor;
import fintrek.data.DataHandler;

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
        DataHandler.loadData();
        if(RegularExpenseManager.getInstance().getLength() > 0) {
            System.out.println(String.format(
                    MessageDisplayer.LANDING_MESSAGE_NONEMPTY_LIST,
                    AppServices.REGULAR_REPORTER.listExpenses()));
        } else {
            System.out.println(MessageDisplayer.LANDING_MESSAGE_EMPTY_LIST);
            System.out.println(MessageDisplayer.CONVERSATION_STARTER);
        }


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
                DataHandler.saveData();
            } else {
                System.out.println(result.errorMessage());
                logger.warning("Parsing failed: " + result.errorMessage());
            }

            userInput = reader.nextLine().trim();
        }

        logger.info("FinTrek application shutting down.");
    }
}
