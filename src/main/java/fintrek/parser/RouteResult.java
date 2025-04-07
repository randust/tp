package fintrek.parser;

/**
 * A generic result wrapper for route operations.
 *
 * <p>This class represents the outcome of routing a user command.
 * It encapsulates whether the routing was successful and provides
 * a message corresponding to the result.
 *
 * <p>If successful, {@code outputMessage()} returns the success message.
 * If failed, {@code errorMessage()} returns the error message.
 */
public class RouteResult {
    /**
     * Indicates whether the routing operation was successful.
     */
    private final boolean success;

    /**
     * The message associated with the operation. This is either the output
     * message (if successful) or the error message (if failed).
     */
    private final String message;

    /**
     * Constructs a new RouteResult with the given success status and message.
     *
     * @param success {@code true} if the routing operation succeeded; {@code false} otherwise.
     * @param message The message to associate with the result. This is an output message on success,
     *                or an error message on failure.
     */
    public RouteResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    /**
     * Returns whether the routing was successful.
     *
     * @return {@code true} if the routing succeeded; {@code false} otherwise.
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Returns the error message if the routing failed.
     *
     * @return The error message if the operation failed; {@code null} if it succeeded.
     */
    public String errorMessage() {
        return !success ? message : null;
    }

    /**
     * Returns the output message if the routing succeeded.
     *
     * @return The output message if the operation succeeded; {@code null} if it failed.
     */
    public String outputMessage() {
        return success ? message : null;
    }
}
