package fintrek.expense.service;

import fintrek.expense.core.Expense;
import fintrek.expense.core.ExpenseOperation;
import fintrek.misc.MessageDisplayer;

import java.util.List;

public class ExpenseService {
    private final ExpenseOperation manager;

    public ExpenseService(ExpenseOperation manager) {
        if (manager == null) {
            throw new IllegalArgumentException(MessageDisplayer.NULL_EXPENSE_ERROR);
        }
        this.manager = manager;
    }

    public void addExpense(Expense expense) {
        manager.add(expense);
    }

    public Expense getExpense(int index) {
        return manager.get(index);
    }

    public Expense removeExpense(int index) {
        return manager.remove(index);
    }

    public List<Expense> getAllExpenses() {
        return manager.getAll();
    }

    public void clearExpenses() {
        manager.clear();
    }

    public int countExpenses() {
        return manager.size();
    }
}
