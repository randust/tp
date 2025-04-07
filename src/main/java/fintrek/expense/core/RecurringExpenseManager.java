package fintrek.expense.core;

import fintrek.misc.MessageDisplayer;
import fintrek.util.InputValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Singleton manager for handling recurring expenses.
 *
 * <p>This class implements the {@link ExpenseOperation} interface and stores
 * expenses in-memory using a dynamic list. It provides operations to add, remove,
 * retrieve, and clear expenses, while logging all state-changing actions.</p>
 */
public class RecurringExpenseManager implements ExpenseOperation {
    private static final Logger logger = Logger.getLogger(RecurringExpenseManager.class.getName());

    /** The singleton instance of this manager. */
    private static final RecurringExpenseManager instance = new RecurringExpenseManager();

    /** The internal list storing regular expenses. */
    private final List<Expense> recurringExpenses = new ArrayList<>();

    /** Private constructor to prevent instantiation, or enforce Singleton pattern */
    private RecurringExpenseManager() {}

    /**
     * Returns the singleton instance of this manager.
     *
     * @return the shared instance of {@code RecurringExpenseManager}
     */
    public static RecurringExpenseManager getInstance() {
        return instance;
    }

    /**
     * Indicates that this manager handles recurring expenses.
     *
     * @return {@code true}, since this manager is for recurring expenses
     */
    @Override
    public boolean isRecurring() {
        return true;
    }

    /**
     * Adds a new recurring expense to the list of recurring expenses.
     *
     * @param expense the expense to add (must not be null)
     */
    //@@author Charly2312
    @Override
    public void add(Expense expense) {
        assert expense != null : MessageDisplayer.NULL_EXPENSE_ERROR;
        logger.log(Level.FINE, MessageDisplayer.ADDING_REGULAR_EXPENSE_MESSAGE + expense);
        recurringExpenses.add(expense);
    }

    /**
     * Retrieves the recurring expense at the specified index.
     *
     * @param index the index to retrieve (0-based)
     * @return the expense at the given index
     * @throws AssertionError if the index is out of bounds
     */
    @Override
    public Expense get(int index) {
        assert InputValidator.isInValidIntRange(index, 0, recurringExpenses.size() - 1)
                : MessageDisplayer.INVALID_IDX_MESSAGE;
        return recurringExpenses.get(index);
    }

    /**
     * Removes and returns the recurring expense at the specified index.
     *
     * @param index the index of the expense to remove (0-based)
     * @return the removed expense
     * @throws AssertionError if the index is out of bounds
     */
    @Override
    public Expense remove(int index) {
        assert InputValidator.isInValidIntRange(index, 0, recurringExpenses.size() - 1)
                : MessageDisplayer.INVALID_IDX_MESSAGE;
        Expense removed = recurringExpenses.remove(index);
        logger.log(Level.FINE, String.format(MessageDisplayer.REMOVED_EXPENSE_MESSAGE_TEMPLATE, index, removed));
        return removed;
    }

    /**
     * Returns the number of recurring expenses currently stored.
     *
     * @return the size of the recurring expense list
     */
    @Override
    public int getLength() {
        return recurringExpenses.size();
    }

    /**
     * Returns a defensive copy of all recurring expenses.
     *
     * @return a list of all current recurring expenses
     */
    @Override
    public List<Expense> getAll() {
        return new ArrayList<>(recurringExpenses); // defensive copy
    }

    /**
     * Removes all recurring expenses from the list.
     */
    @Override
    public void clear() {
        recurringExpenses.clear();
        logger.log(Level.FINE, MessageDisplayer.CLEARED_ALL_REGULAR_EXPENSES_MESSAGE);
    }

    /**
     * Inserts a recurring expense at the given index, shifting subsequent items to the right.
     *
     * @param index the index at which to insert the recurring expense (0-based)
     * @param expense the recurring expense to insert (must not be null)
     * @throws AssertionError if index is out of bounds
     */
    @Override
    public void insertAt(int index, Expense expense) {
        assert InputValidator.isInValidIntRange(index, 0, recurringExpenses.size())
                : MessageDisplayer.INVALID_IDX_MESSAGE;
        logger.log(Level.FINE, String.format(MessageDisplayer.INSERTING_EXPENSE_MESSAGE_TEMPLATE, index, expense));
        recurringExpenses.add(index, expense);
    }
}
