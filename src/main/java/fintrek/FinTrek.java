package fintrek;
import fintrek.misc.DisplayMessage;
import java.util.Scanner;

public class FinTrek {
    /**
     * Main entry-point for the java.duke.Duke application.
     */
    public static void main(String[] args) {
        System.out.println(DisplayMessage.WELCOME_MESSAGE);
        System.out.println(DisplayMessage.CONVERSATION_STARTER);

        Scanner reader = new Scanner(System.in);
        String userInput = reader.nextLine().trim(); // get user input
        while (!userInput.equals(DisplayMessage.END_CONVERSATION_MESSAGE)) {
            Parser.parseUserInput(userInput);
            userInput = reader.nextLine().trim();
        }
    }
}
