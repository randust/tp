package fintrek.parser;

/**
 * A generic wrapper class that encapsulates the result of a parse operation.
 *
 * <p>This class represents either a successful parse, containing the parsed
 * result of type {@code T}, or a failure, containing an error message.
 *
 * <p>Use {@code ParseResult.success(result)} to create a successful result,
 * and {@code ParseResult.failure(errorMessage)} to create a failed result.
 *
 * @param <T> The type of the object returned upon a successful parse.
 */
public class ParseResult<T> {
    /**
     * Indicates whether the parse was successful.
     */
    private final boolean success;

    /**
     * The result of the parse if successful; otherwise {@code null}.
     */
    private final T result;

    /**
     * The error message if the parse failed; otherwise {@code null}.
     */
    private final String error;

    /**
     * Constructs a ParseResult with the specified success state, result, and error message.
     *
     * @param success Whether the parse was successful.
     * @param result The parsed result, if successful.
     * @param error The error message, if failed.
     */
    private ParseResult(boolean success, T result, String error) {
        this.success = success;
        this.result = result;
        this.error = error;
    }

    /**
     * Creates a successful ParseResult containing the given result.
     *
     * @param result The result of the successful parse.
     * @param <T> The type of the result.
     * @return A successful {@code ParseResult} instance.
     */
    public static <T> ParseResult<T> success(T result) {
        return new ParseResult<>(true, result, null);
    }

    /**
     * Creates a failed ParseResult with the given error message.
     *
     * @param errorMessage The error message describing why the parse failed.
     * @param <T> The type of the expected result.
     * @return A failed {@code ParseResult} instance.
     */
    public static <T> ParseResult<T> failure(String errorMessage) {
        return new ParseResult<>(false, null, errorMessage);
    }

    /**
     * Checks if the parse was successful.
     *
     * @return {@code true} if the parse succeeded; {@code false} otherwise.
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Returns the result of a successful parse.
     *
     * @return The parsed result, or {@code null} if the parse failed.
     */
    public T getResult() {
        return result;
    }

    /**
     * Returns the error message if the parse failed.
     *
     * @return The error message, or {@code null} if the parse was successful.
     */
    public String getError() {
        return error;
    }
}
