package fintrek.command;

public abstract class Command {
    public abstract CommandResult execute(String arguments);

    public String getDescription() {
        CommandInfo info = this.getClass().getAnnotation(CommandInfo.class);
        return info != null ? info.description() : "No description available.";
    }
}
