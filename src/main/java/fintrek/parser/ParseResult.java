package fintrek.parser;

/**
 * Generic result wrapper for parse operations.
 */
public class ParseResult<T> {
    private final boolean success;
    private final T result;
    private final String error;

    private ParseResult(boolean success, T result, String error) {
        this.success = success;
        this.result = result;
        this.error = error;
    }

    public static <T> ParseResult<T> success(T result) {
        return new ParseResult<>(true, result, null);
    }

    public static <T> ParseResult<T> failure(String errorMessage) {
        return new ParseResult<>(false, null, errorMessage);
    }

    public boolean isSuccess() {
        return success;
    }

    public T getResult() {
        return result;
    }

    public String getError() {
        return error;
    }
}
