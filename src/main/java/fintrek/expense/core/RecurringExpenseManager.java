package fintrek.expense.core;

import fintrek.misc.MessageDisplayer;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Manages recurring expenses only.
 */
public class RecurringExpenseManager implements ExpenseOperation {
    private static final List<Expense> recurringExpenses = new ArrayList<>();
    private static final Logger logger = Logger.getLogger(RecurringExpenseManager.class.getName());

    @Override
    public void add(Expense expense) {
        assert expense != null : MessageDisplayer.NULL_EXPENSE_ERROR;
        logger.info("Adding recurring expense: " + expense);
        recurringExpenses.add(expense);
    }

    @Override
    public Expense get(int index) {
        assert index >= 0 && index < recurringExpenses.size() : MessageDisplayer.INVALID_IDX_MESSAGE;
        return recurringExpenses.get(index);
    }

    @Override
    public Expense remove(int index) {
        assert index >= 0 && index < recurringExpenses.size() : MessageDisplayer.INVALID_IDX_MESSAGE;
        Expense removed = recurringExpenses.remove(index);
        logger.info("Removed recurring expense at index " + index + ": " + removed);
        return removed;
    }

    @Override
    public int size() {
        return recurringExpenses.size();
    }

    @Override
    public List<Expense> getAll() {
        return new ArrayList<>(recurringExpenses); // defensive copy
    }

    @Override
    public void clear() {
        recurringExpenses.clear();
        logger.info("Cleared all recurring expenses.");
    }
}
