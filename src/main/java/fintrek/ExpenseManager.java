package fintrek;

import java.util.ArrayList;
import java.util.List;

public class ExpenseManager {
    private static final List<Expense> expenses = new ArrayList<>();

    public static Expense getExpense(int index) {
        return expenses.get(index);
    }

    public static int getLength() {
        return expenses.size();
    }

    public static void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public static Expense popExpense(int index) {
        return expenses.remove(index);
    }

    public static double getTotalExpenses() {
        double totalExpenses = 0;
        for (Expense expense : expenses) {
            totalExpenses += expense.getAmount();
        }
        return totalExpenses;
    }

    public static double getAverageExpenses() {
        double totalExpenses = getTotalExpenses();
        int numExpenses = getLength();
        double averageExpense = totalExpenses / numExpenses;
        return averageExpense;
    }

    public static void listExpenses() {
        int i = 1;
        for (Expense expense : expenses) {
            System.out.println(i + ". " + expense);
            i++;
        }
    }
}
