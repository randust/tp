package fintrek.command;

public abstract class Command {
    public abstract CommandResult execute(String arguments);
    public abstract String getDescription();
}
