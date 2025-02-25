package fintrek;

public class Parser {
    public static void parseUserInput(String userInput){
        String[] tokens = userInput.split("\\s+", 2); // split into [ACTION, ARGUMENTS]
        String commandStr = tokens[0];
        if (!commandStr.startsWith("/")) {
            //TODO: print error message
            return;
        }
        try {
            String arguments = tokens[1]; //TODO: if there are commands that does not have subsequent argument, we need to handle separately
            Command command = Command.valueOf(commandStr.substring(1).toUpperCase());
            command.execute(arguments);
        } catch (IllegalArgumentException e) {
            //TODO: print error message (there is no such command)
        }
    }
}
