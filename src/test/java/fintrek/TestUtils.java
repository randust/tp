package fintrek;

import java.util.List;

public class TestUtils {
    /**
     * Adds predefined expenses to ExpenseManager for consistent test scenarios.
     */
    public static void addConstantExpenses() {
        List<Expense> expenses = List.of(
                new Expense("lunch", 5.50, "food"),
                new Expense("taxi", 11.20, "transport"),
                new Expense("dinner", 9.80, "food"),
                new Expense("ice cream", 2.50, "food"),
                new Expense("train", 1.66, "transport"),
                new Expense("concert", 256, "entertainment")
        );
        expenses.forEach(ExpenseManager::addExpense);
    }
}