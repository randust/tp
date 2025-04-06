package fintrek.expense.core;

import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;

public class CategoryManager {
    private static final Set<String> defaultCategories = new HashSet<>(Arrays.asList(
            "FOOD", "TRANSPORT", "HEALTH", "ENTERTAINMENT", "UTILITIES", "GIFTS", "UNCATEGORIZED"
    ));

    private static final Set<String> customCategories = new HashSet<>();

    public static boolean isValid(String input) {
        String inputUp = input.toUpperCase();
        return defaultCategories.contains(inputUp) || customCategories.contains(inputUp);
    }

    public static void addCustomCategory(String newCategory) {
        customCategories.add(newCategory.toUpperCase());
    }

    public static String getCustomCategoriesAsString() {
        return String.join(",", customCategories);
    }

    public static boolean hasCustomCategories() {
        return !customCategories.isEmpty();
    }

    public static void clearCustomCategories() {
        customCategories.clear();
    }
}
