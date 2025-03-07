package fintrek;
import fintrek.misc.DisplayMessage;

public class Parser {
    public static void parseUserInput(String userInput){
        String[] tokens = userInput.split("\\s+", 2); // split into [ACTION, ARGUMENTS]
        String commandStr = tokens[0];
        if (!commandStr.startsWith("/")) {
            //TODO: print error message
            return;
        }

        try {
            Command command = Command.valueOf(commandStr.substring(1).toUpperCase());
            String arguments;
            if (!command.emptyArg && (tokens.length < 2 || tokens[1].isEmpty())){
                System.out.println(DisplayMessage.ARG_EMPTY_MSG);
                return;
            } else if (command.emptyArg && (tokens.length < 2 || tokens[1].isEmpty())){
                arguments = null;
            } else {
                arguments = tokens[1];
            }
            command.execute(arguments);
        } catch (IllegalArgumentException e) {
            //TODO: print error message (there is no such command)
        }
    }
}