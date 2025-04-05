package fintrek.expense.core;

import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;

public class Category {
    private static final String[] defaultCategories =
        {"FOOD", "TRANSPORT", "HEALTH", "ENTERTAINMENT", "UTILITIES", "GIFTS", "UNCATEGORIZED"};
    private static final Set<String> customCategories = new HashSet<>(Arrays.asList(defaultCategories));

    public static boolean isValid(String input) {
        for (String category : defaultCategories) {
            if (category.equals(input.toUpperCase())) {
                return true;
            }
        }
        return customCategories.contains(input.toUpperCase());
    }

    public static void addCustomCategory(String newCategory) {
        customCategories.add(newCategory.toUpperCase());
    }
}
