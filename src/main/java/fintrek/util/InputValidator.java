package fintrek.util;

import fintrek.expense.core.CategoryManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Utility class for validating user input related to expenses.
 */
public class InputValidator {

    /**
     * Checks if input string is empty or contains whitespace only.
     *
     * @param input the input string to check
     * @return {@code true} if the input is null or blank, {@code false} otherwise
     */
    public static boolean isNullOrBlank(String input) {
        return input == null || input.isBlank();
    }

    /**
     * Validates whether the input string represents a positive double.
     *
     * @param input the input string to validate
     * @return {@code true} if the input is a valid positive double, {@code false} otherwise
     */
    public static boolean isValidPositiveDouble(String input) {
        return input.matches("\\d+(\\.\\d+)?") && Double.parseDouble(input.trim()) > 0;
    }

    /**
     * Validates whether the input string represents a positive integer.
     *
     * @param input the input string to validate
     * @return {@code true} if the input is a valid positive integer, {@code false} otherwise
     */
    public static boolean isValidPositiveInteger(String input) {
        return input.matches("\\d+") && Integer.parseInt(input.trim()) > 0;
    }

    /**
     * Checks if a given integer falls within the specified range.
     *
     * @param value the integer value to check
     * @param lowerBound the lower bound (inclusive)
     * @param upperBound the upper bound (inclusive)
     * @return {@code true} if the value is within the range, {@code false} otherwise
     */
    public static boolean isInValidIntRange(int value, int lowerBound, int upperBound) {
        return value >= lowerBound && value <= upperBound;
    }

    /**
     * Checks if a given double falls within the specified range
     *
     * @param value the double value to check
     * @param lowerBound the lower bound (exclusive)
     * @param upperBound the upper bound (inclusive)
     * @return {@code true} if the value is within the range, {@code false} otherwise
     */
    public static boolean isInValidDoubleRange(double value, double lowerBound, double upperBound) {
        return value > lowerBound && value <= upperBound;
    }

    /**
     * Validates that a string has a length from 1 to 100 characters (inclusive).
     *
     * @param input the string to validate
     * @return {@code true} if the string length is valid, {@code false} otherwise
     */
    public static boolean isValidStringLength(String input) {
        return !input.isEmpty() && input.length() <= 100;
    }

    /**
     * Checks whether the input string for the date is of the valid format of "dd-MM-yyyy"
     * @param input the input string for the date
     * @return a {@code Boolean} value indicating whether the format is valid
     */
    public static boolean isValidDate(String input) {
        try {
            LocalDate.parse(input, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Checks whether the input string is a valid amount input.
     *
     * @param input the string to validate
     * @return {@code true} if the input is in a valid amount format, {@code false} otherwise
     */
    public static boolean isValidAmountInput(String input) {
        String amountFormat = "\\d+(\\.\\d+)?";

        return input.matches(amountFormat);
    }

    /**
     * Validates whether the input string is a recognized category by {@link CategoryManager}.
     *
     * @param input the category string to validate
     * @return {@code true} if the category is valid, {@code false} otherwise
     */
    public static boolean isValidCategory(String input) {
        return CategoryManager.isValid(input);
    }

    /**
     * Checks if the input string contains any whitespace characters.
     *
     * @param input the string to check
     * @return {@code true} if the input contains whitespace, {@code false} otherwise
     */
    public static boolean containsWhiteSpace(String input) {
        return !input.matches("^\\S+$");
    }
}
