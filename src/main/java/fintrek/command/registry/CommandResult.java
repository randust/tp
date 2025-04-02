package fintrek.command.registry;
/**
 * The {@code CommandResult} record represents the outcome of processing a command,
 * whether it fails during validation (parsing) or encounters an issue during execution.
 *
 * @param isSuccess    {@code true} if the command was successfully processed;
 *                     {@code false} if an error occurred.
 * @param message   a descriptive message providing details on the command's outcome;
 *                  An error messages in case of failure execution or the desired display output if successful.
 */
public record CommandResult(boolean isSuccess, String message) {}
