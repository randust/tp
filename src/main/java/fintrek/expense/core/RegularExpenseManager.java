package fintrek.expense.core;

import fintrek.misc.MessageDisplayer;
import fintrek.util.InputValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Singleton manager for handling regular (non-recurring) expenses.
 *
 * <p>This class implements the {@link ExpenseOperation} interface and stores
 * expenses in-memory using a dynamic list. It provides operations to add, remove,
 * retrieve, and clear expenses, while logging all state-changing actions.</p>
 */
public class RegularExpenseManager implements ExpenseOperation {
    private static final Logger logger = Logger.getLogger(RegularExpenseManager.class.getName());

    /** The smallest valid index for accessing the list. */
    private static final int INDEX_LOWER_BOUND = 0;

    /** The singleton instance of this manager. */
    private static final RegularExpenseManager instance = new RegularExpenseManager();

    /** The internal list storing regular expenses. */
    private final List<Expense> expenses = new ArrayList<>();

    /** Private constructor to enforce singleton pattern. */
    private RegularExpenseManager() {}

    /**
     * Returns the singleton instance of this manager.
     *
     * @return the shared instance of {@code RegularExpenseManager}
     */
    public static RegularExpenseManager getInstance() {
        return instance;
    }

    /**
     * Indicates that this manager handles regular (not recurring) expenses.
     *
     * @return {@code false}, since this manager is for regular expenses
     */
    @Override
    public boolean isRecurring() {
        return false;
    }

    /**
     * Adds a new expense to the list.
     *
     * @param expense the expense to add (must not be null)
     */
    @Override
    public void add(Expense expense) {
        assert expense != null : MessageDisplayer.NULL_EXPENSE_ERROR;
        logger.log(Level.FINE, MessageDisplayer.ADDING_REGULAR_EXPENSE_MESSAGE + expense);
        expenses.add(expense);
    }

    /**
     * Retrieves the expense at the specified index.
     *
     * @param index the index to retrieve (0-based)
     * @return the expense at the given index
     * @throws AssertionError if the index is out of bounds
     */
    @Override
    public Expense get(int index) {
        assert InputValidator.isInValidIntRange(index, INDEX_LOWER_BOUND, expenses.size() - 1)
                : MessageDisplayer.INVALID_IDX_MESSAGE;
        return expenses.get(index);
    }

    /**
     * Removes and returns the expense at the specified index.
     *
     * @param index the index of the expense to remove (0-based)
     * @return the removed expense
     * @throws AssertionError if the index is out of bounds
     */
    @Override
    public Expense remove(int index) {
        assert InputValidator.isInValidIntRange(index, INDEX_LOWER_BOUND, expenses.size() - 1)
                : MessageDisplayer.INVALID_IDX_MESSAGE;
        Expense removed = expenses.remove(index);
        logger.log(Level.FINE, String.format(MessageDisplayer.REMOVED_EXPENSE_MESSAGE_TEMPLATE, index, removed));
        return removed;
    }

    /**
     * Returns the number of expenses currently stored.
     *
     * @return the size of the expense list
     */
    @Override
    public int getLength() {
        return expenses.size();
    }

    /**
     * Returns a defensive copy of all expenses.
     *
     * @return a list of all current expenses
     */
    @Override
    public List<Expense> getAll() {
        return new ArrayList<>(expenses);
    }

    /**
     * Removes all expenses from the list.
     */
    @Override
    public void clear() {
        expenses.clear();
        logger.log(Level.FINE, MessageDisplayer.CLEARED_ALL_REGULAR_EXPENSES_MESSAGE);
    }

    /**
     * Inserts an expense at the given index, shifting subsequent items to the right.
     *
     * @param index the index at which to insert the expense (0-based)
     * @param expense the expense to insert (must not be null)
     * @throws AssertionError if index is out of bounds
     */
    @Override
    public void insertAt(int index, Expense expense) {
        assert InputValidator.isInValidIntRange(index, INDEX_LOWER_BOUND, expenses.size())
                : MessageDisplayer.INVALID_IDX_MESSAGE;
        logger.log(Level.FINE, String.format(MessageDisplayer.INSERTING_EXPENSE_MESSAGE_TEMPLATE, index, expense));
        expenses.add(index, expense);
    }

}
