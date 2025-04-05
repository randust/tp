package fintrek.util;

import fintrek.expense.core.Category;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class InputValidator {

    public static boolean isNullOrBlank(String input) {
        return input == null || input.isBlank();
    }

    public static boolean isValidPositiveDouble(String input) {
        return input.matches("\\d+(\\.\\d+)?") && Double.parseDouble(input.trim()) > 0;
    }

    public static boolean isValidPositiveInteger(String input) {
        return input.matches("\\d+") && Integer.parseInt(input.trim()) > 0;
    }

    public static boolean isInValidIntRange(int value, int lowerBound, int upperBound) {
        return value >= lowerBound && value <= upperBound;
    }

    public static boolean isValidStringLength(String input) {
        return !input.isEmpty() && input.length() <= 100;
    }

    /**
     * Checks whether the input string for the date is of the valid format of "dd-MM-yyyy"
     * @param input the input string for the date
     * @return a {@code Boolean} value indicating whether or not the format is valid
     */
    public static boolean isValidDate(String input) {
        try {
            LocalDate.parse(input, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static boolean isValidAmountInput(String input) {
        String amountFormat = "\\d+(\\.\\d+)?";
        return input.matches(amountFormat);
    }

    public static boolean isValidCategory(String input) {
        return Category.isValid(input);
    }
}
