package fintrek;

import fintrek.misc.MessageDisplayer;
import fintrek.parser.ParseResult;
import fintrek.parser.Parser;

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

        Scanner reader = new Scanner(System.in);
        String userInput = reader.nextLine().trim(); // get user input

        while (!userInput.equals(MessageDisplayer.END_CONVERSATION_MESSAGE)) {
            logger.log(Level.INFO, "going to start processing");
            logger.info("User input received: " + userInput);

            ParseResult result = Parser.parseUserInput(userInput);
            if (!result.isSuccess()) {
                System.out.println(result.errorMessage());
                logger.warning("Parsing failed: " + result.errorMessage());
            } else {
                logger.info("Parsing successful.");
            }

            userInput = reader.nextLine().trim();
        }

        logger.info("FinTrek application shutting down.");
    }
}
