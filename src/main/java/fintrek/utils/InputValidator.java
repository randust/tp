package fintrek.utils;

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
}
