package fintrek.command.add;

import java.time.LocalDate;

/**
 * Represents the result of parsing an add command.
 *
 * <p>This record bundles the description, amount, category, and date of the expense to add.</p>
 *
 * <p>Used internally by {@link AddCommand} after parsing the user's input.</p>
 *
 * @param desc the description of expense to be added
 * @param amount the amount of expense to be added
 * @param category the category of expense to be added
 * @param date the date the expense occurred
 */
public record AddParseResult(String desc, double amount, String category, LocalDate date) {
}
