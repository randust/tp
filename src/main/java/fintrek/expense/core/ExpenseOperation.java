package fintrek.expense.core;

import java.util.List;

public interface ExpenseOperation {
    void add(Expense expense);
    Expense get(int index);
    Expense remove(int index);
    int size();
    List<Expense> getAll();
    void clear();
}
