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
}
