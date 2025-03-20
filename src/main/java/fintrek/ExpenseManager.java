package fintrek;

import fintrek.misc.MessageDisplayer;

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
            assert expense.getAmount() >= 0 : MessageDisplayer.INVALID_AMOUNT;
            totalExpenses += expense.getAmount();
        }
        return totalExpenses;
    }

    public static double getAverageExpenses() {
        double totalExpenses = getTotalExpenses();
        int numExpenses = getLength();
        if (numExpenses == 0) {
            return 0;
        }
        assert numExpenses > 0 : MessageDisplayer.EMPTY_LIST_MESSAGE;
        return totalExpenses / numExpenses;
    }

    public static void clearExpenses() {
        expenses.clear();
    }

    public static String listExpenses() {
        if (expenses.isEmpty()) {
            return MessageDisplayer.EMPTY_LIST_MESSAGE;
        }
        StringBuilder list = new StringBuilder();
        int i = 1;
        for (Expense expense : expenses) {
            list.append(String.format("%n%d. %s", i++, expense));
        }
        return list.toString();
    }
}
