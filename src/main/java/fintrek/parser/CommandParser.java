package fintrek.parser;

public interface CommandParser<T> {
    T parse(String input) throws IllegalArgumentException;
}
