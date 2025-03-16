package fintrek;

import fintrek.misc.DisplayMessage;

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

    public static void clearExpenses() {
        expenses.clear();
    }
  
    public static String listExpenses() {
        if (expenses.isEmpty()) {
            return DisplayMessage.EMPTY_LIST;
        }
        StringBuilder list = new StringBuilder();
        int i = 1;
        for (Expense expense : expenses) {
            list.append(String.format("%n%d. %s", i++, expense));
        }
        return list.toString();
    }
}
