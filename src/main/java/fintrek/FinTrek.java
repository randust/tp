package fintrek;
import fintrek.misc.MessageDisplayer;
import fintrek.parser.ParseResult;
import fintrek.parser.Parser;

import java.util.Scanner;

public class FinTrek {
    /**
     * Main entry-point for the java.fintrek.FinTrek application.
     */
    public static void main(String[] args) {
        System.out.println(MessageDisplayer.WELCOME_MESSAGE);
        System.out.println(MessageDisplayer.CONVERSATION_STARTER);

        Scanner reader = new Scanner(System.in);
        String userInput = reader.nextLine().trim(); // get user input
        while (!userInput.equals(MessageDisplayer.END_CONVERSATION_MESSAGE)) {
            ParseResult result = Parser.parseUserInput(userInput);
            if (!result.isSuccess()) {
                System.out.println(result.errorMessage());
            }
            userInput = reader.nextLine().trim();
        }
    }
}
