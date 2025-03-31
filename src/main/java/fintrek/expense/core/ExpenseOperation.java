package fintrek.expense.core;

import java.util.List;

public interface ExpenseOperation {
    void add(Expense expense);
    Expense get(int index);
    Expense remove(int index);
    int getLength();
    List<Expense> getAll();
    void clear();
    void insertAt(int index, Expense expense);
    boolean isRecurring();
}
