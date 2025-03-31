package fintrek.expense.core;

import fintrek.misc.MessageDisplayer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Manages regular (non-recurring) expenses.
 */
public class RegularExpenseManager implements ExpenseOperation {
    private static final List<Expense> expenses = new ArrayList<>();
    private static final Logger logger = Logger.getLogger(RegularExpenseManager.class.getName());

    @Override
    public void add(Expense expense) {
        assert expense != null : MessageDisplayer.NULL_EXPENSE_ERROR;
        logger.info("Adding regular expense: " + expense);
        expenses.add(expense);
    }

    @Override
    public Expense get(int index) {
        assert index >= 0 && index < expenses.size() : MessageDisplayer.INVALID_IDX_MESSAGE;
        return expenses.get(index);
    }

    @Override
    public Expense remove(int index) {
        assert index >= 0 && index < expenses.size() : MessageDisplayer.INVALID_IDX_MESSAGE;
        Expense removed = expenses.remove(index);
        logger.info("Removed regular expense at index " + index + ": " + removed);
        return removed;
    }

    @Override
    public int size() {
        return expenses.size();
    }

    @Override
    public List<Expense> getAll() {
        return new ArrayList<>(expenses); // defensive copy
    }

    @Override
    public void clear() {
        expenses.clear();
        logger.info("Cleared all regular expenses.");
    }

    public void insertAt(int index, Expense expense) {
        assert index >= 0 && index <= expenses.size() : MessageDisplayer.INVALID_IDX_MESSAGE;
        logger.info("Inserting regular expense at index " + index + ": " + expense);
        expenses.add(index, expense);
    }
}
