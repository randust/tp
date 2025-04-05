package fintrek.expense.service;

import fintrek.expense.core.Expense;
import fintrek.expense.core.ExpenseOperation;
import fintrek.misc.MessageDisplayer;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service layer for managing expenses through an underlying {@link ExpenseOperation} implementation.
 *
 * <p>This class provides high-level methods for manipulating expense data, abstracting away
 * the internal details of whether the data is regular or recurring. It includes utility methods
 * for adding, removing, retrieving, and counting expenses, and logs relevant operations.</p>
 */
public class ExpenseService {
    private static final Logger logger = Logger.getLogger(ExpenseService.class.getName());
    private final ExpenseOperation manager;

    /**
     * Constructs an {@code ExpenseService} backed by the given manager.
     *
     * @param manager an implementation of {@link ExpenseOperation}
     * @throws IllegalArgumentException if {@code manager} is null
     */
    public ExpenseService(ExpenseOperation manager) {
        if (manager == null) {
            throw new IllegalArgumentException(MessageDisplayer.NULL_EXPENSE_ERROR);
        }
        this.manager = manager;
    }

    /**
     * Adds an expense to the underlying manager.
     *
     * @param expense the expense to add
     */
    public void addExpense(Expense expense) {
        manager.add(expense);
    }

    /**
     * Inserts an expense at the specified index.
     *
     * @param index the position to insert the expense at (0-based)
     * @param expense the expense to insert
     */
    public void insertExpenseAt(int index, Expense expense) {
        manager.insertAt(index, expense);
    }

    /**
     * Retrieves the expense at the given index.
     *
     * @param index the index of the expense to retrieve (0-based)
     * @return the expense at the specified index
     */
    public Expense getExpense(int index) {
        return manager.get(index);
    }

    /**
     * Removes and returns the expense at the specified index.
     *
     * @param index the index of the expense to remove (0-based)
     * @return the removed expense
     */
    public Expense removeExpense(int index) {
        logger.log(Level.WARNING, MessageDisplayer.POPPING_EXPENSE_AT_INDEX_MESSAGE + index);
        return manager.remove(index);
    }

    /**
     * Alias for {@link #removeExpense(int)}. Removes and returns the expense at the specified index.
     *
     * @param index the index of the expense to remove (0-based)
     * @return the removed expense
     */
    public Expense popExpense(int index) {
        logger.log(Level.WARNING, MessageDisplayer.POPPING_EXPENSE_AT_INDEX_MESSAGE + index);
        return manager.remove(index);
    }

    /**
     * Returns a list of all expenses currently stored.
     *
     * @return a list of all expenses
     */
    public List<Expense> getAllExpenses() {
        return manager.getAll();
    }

    /**
     * Clears all expenses from the collection.
     */
    public void clearExpenses() {
        manager.clear();
    }

    /**
     * Returns the number of expenses in the collection.
     *
     * @return the count of stored expenses
     */
    public int countExpenses() {
        return manager.getLength();
    }
}
