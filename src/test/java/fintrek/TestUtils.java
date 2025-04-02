package fintrek;

import fintrek.util.ExpenseManager;
import fintrek.expense.core.Expense;

import java.time.LocalDate;
import java.util.List;

public class TestUtils {
    /**
     * Adds predefined expenses to ExpenseManager for consistent test scenarios.
     */
    public static void addConstantExpenses() {
        List<Expense> expenses = List.of(
                new Expense("lunch", 5.50, "food", LocalDate.now()),
                new Expense("taxi", 11.20, "transport", LocalDate.now()),
                new Expense("dinner", 9.80, "food", LocalDate.now()),
                new Expense("ice cream", 2.50, "food", LocalDate.now()),
                new Expense("train", 1.66, "transport", LocalDate.now()),
                new Expense("concert", 256, "entertainment", LocalDate.now())
        );
        expenses.forEach(ExpenseManager::addExpense);
    }
}
