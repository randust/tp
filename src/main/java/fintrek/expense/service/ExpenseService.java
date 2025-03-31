package fintrek.expense.service;

import fintrek.expense.core.Expense;
import fintrek.expense.core.ExpenseOperation;
import fintrek.misc.MessageDisplayer;

import java.util.List;
import java.util.logging.Logger;

public class ExpenseService {
    private static final Logger logger = Logger.getLogger(ExpenseService.class.getName());
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

    public void insertExpenseAt(int index, Expense expense) {
        manager.insertAt(index, expense);
    }

    public Expense getExpense(int index) {
        return manager.get(index);
    }

    public Expense removeExpense(int index) {
        return manager.remove(index);
    }

    // (Optional) Convenience alias similar to popExpense in the old manager.
    public Expense popExpense(int index) {
        logger.info("Popping expense at index: " + index);
        return manager.remove(index);
    }

    public List<Expense> getAllExpenses() {
        return manager.getAll();
    }

    public void clearExpenses() {
        manager.clear();
    }

    public int countExpenses() {
        return manager.getLength();
    }

}
