package fintrek.expense.core;

import fintrek.misc.MessageDisplayer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Manages regular (non-recurring) expenses.
 */
public class RegularExpenseManager implements ExpenseOperation {
    private static final Logger logger = Logger.getLogger(RegularExpenseManager.class.getName());

    // Singleton instance
    private static final RegularExpenseManager instance = new RegularExpenseManager();

    // Shared list of expenses
    private final List<Expense> expenses = new ArrayList<>();

    private RegularExpenseManager() {}

    public static RegularExpenseManager getInstance() {
        return instance;
    }

    @Override
    public boolean isRecurring() {
        return false;
    }

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
    public int getLength() {
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

    @Override
    public void insertAt(int index, Expense expense) {
        assert index >= 0 && index <= expenses.size() : MessageDisplayer.INVALID_IDX_MESSAGE;
        logger.info("Inserting regular expense at index " + index + ": " + expense);
        expenses.add(index, expense);
    }
}
