package fintrek.parser;

/**
 * Generic interface for parsing user input into structured command arguments.
 *
 * <p>Used by commands that support structured parsing (as opposed to plain string-based input).
 * Implementations are responsible for validating and transforming the raw input into a
 * well-defined type {@code T}, which is then passed to the command for execution.</p>
 *
 * @param <T> the type of the parsed result
 */
public interface CommandParser<T> {

    /**
     * Parses the given raw input string into an instance of type {@code T}.
     *
     * @param input the raw input string (e.g. user command arguments)
     * @return the parsed result of type {@code T}
     * @throws RuntimeException or custom exception if the input is invalid or malformed
     */
    T parse(String input);
}
