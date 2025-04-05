package fintrek.parser;

/**
 * Generic result wrapper for route operations.
 */
public class RouteResult {
    private final boolean success;
    private final String message;

    public RouteResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public String errorMessage() {
        return !success ? message : null;
    }

    public String outputMessage() {
        return success ? message : null;
    }
}
