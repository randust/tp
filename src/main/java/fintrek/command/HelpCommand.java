//@@author Charly2312
package fintrek.command;

import fintrek.misc.MessageDisplayer;

public class HelpCommand extends Command {

    @Override
    public CommandResult execute(String arguments) {
        String message;
        if (arguments != null && !arguments.isBlank()) {
            String keyword = arguments.toLowerCase();
            if (keyword.contains("add")) {
                message = MessageDisplayer.ADD_FORMAT_MESSAGE;
            } else if (keyword.contains("delete")) {
                message = MessageDisplayer.DELETE_FORMAT_MESSAGE;
            } else if (keyword.contains("total")) {
                message = MessageDisplayer.TOTAL_FORMAT_MESSAGE;
            } else if (keyword.contains("average")) {
                message = MessageDisplayer.AVERAGE_FORMAT_MESSAGE;
            } else if (keyword.contains("summary")) {
                message = MessageDisplayer.SUMMARY_FORMAT_MESSAGE;
            } else {
                message = MessageDisplayer.HELP_UNKNOWN_TOPIC;
            }
        } else {
            message = MessageDisplayer.getAllFeaturesMessage();
        }
        return new CommandResult(true, message);
    }

    @Override
    public String getDescription() {
        return "Provides help information. Optionally pass a keyword: /help [command]";
    }
}
