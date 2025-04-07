package fintrek.expense.core;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class is responsible for managing expense categories by maintaining a predefined set
 * of default categories and a modifiable set of custom categories in-memory using a hash set.
 * It supports operations such as adding new custom categories and clearing the list of custom
 * categories.
 */
public class CategoryManager {
    // Pre-defined set of default categories.
    private static final Set<String> defaultCategories = new HashSet<>(Arrays.asList(
            "FOOD", "TRANSPORT", "HEALTH", "ENTERTAINMENT", "UTILITIES", "GIFTS", "UNCATEGORIZED"
    ));

    // Initially empty.
    private static final Set<String> customCategories = new HashSet<>();

    /**
     * Verifies if the custom category to be added is valid, that is, it does not already exist
     * in the list of default categories.
     * @param input the user input containing the custom category to be added.
     * @return a {@code boolean} value indicating whether the user input is valid.
     */
    public static boolean isValid(String input) {
        String inputUp = input.toUpperCase();
        return defaultCategories.contains(inputUp) || customCategories.contains(inputUp);
    }

    /**
     * Adds a new custom category into the list of custom categories in uppercase.
     * @param newCategory the new category to be added.
     */
    public static void addCustomCategory(String newCategory) {
        customCategories.add(newCategory.toUpperCase());
    }

    /**
     * Indicates whether the user has custom categories, that is, the set
     * of custom categories is not empty.
     * @return a boolean value indicating if the user has custom categories.
     */
    public static boolean hasCustomCategories() {
        return !customCategories.isEmpty();
    }

    /**
     * Erases all custom categories from the list of custom categories.
     */
    public static void clearCustomCategories() {
        customCategories.clear();
    }

    /**
     * Returns a comma-separated String equivalent of the given category set,
     * sorted alphabetically.
     *
     * @param categories the set of category strings to be formatted.
     * @return a sorted string of categories joined by commas.
     */
    public static String getCategoriesString(Set<String> categories) {
        return categories.stream()
                .sorted()
                .collect(Collectors.joining(", "));
    }

    /**
     * Returns a comma-separated {@code String} of all custom categories,
     * sorted alphabetically.
     *
     * @return a sorted string of custom categories joined by commas.
     */
    public static String getCustomCategoriesAsString() {
        return getCategoriesString(customCategories);
    }

    /**
     * Returns a comma-separated {@code String} of all default categories,
     * sorted alphabetically.
     *
     * @return a sorted string of default categories joined by commas.
     */
    public static String getDefaultCategoriesAsString() {
        return getCategoriesString(defaultCategories);
    }
}
