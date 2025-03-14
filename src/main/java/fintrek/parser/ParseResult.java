package fintrek.parser;

/**
 * The {@code ParseResult} record represents the result of parsing a user input.
 * It stores whether the parsing was successful and, if not, an associated error message.
 *
 * @param isSuccess      A boolean indicating whether the parsing was successful.
 * @param errorMessage The error message if parsing failed; otherwise, {@code null}.
 */
public record ParseResult(boolean isSuccess, String errorMessage) {}
