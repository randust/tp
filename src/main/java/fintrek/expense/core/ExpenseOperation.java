package fintrek.expense.core;

import java.util.List;

/**
 * Defines the basic operations supported by an expense collection.
 *
 * <p>This interface abstracts operations over both regular and recurring expenses,
 * allowing command logic to interact with them uniformly. Implementations may vary
 * in how data is stored or persisted, but must support core CRUD operations,
 * indexing, and retrieval.</p>
 */
public interface ExpenseOperation {

    /**
     * Adds a new expense to the collection.
     *
     * @param expense the expense to add
     */
    void add(Expense expense);

    /**
     * Retrieves the expense at the specified index.
     *
     * @param index the zero-based index of the expense
     * @return the expense at the specified index
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    Expense get(int index);

    /**
     * Removes and returns the expense at the specified index.
     *
     * @param index the zero-based index of the expense
     * @return the removed expense
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    Expense remove(int index);

    /**
     * Returns the number of expenses currently in the collection.
     *
     * @return the size of the collection
     */
    int getLength();

    /**
     * Returns an unmodifiable list of all expenses in the collection.
     *
     * @return a list of all expenses
     */
    List<Expense> getAll();

    /**
     * Removes all expenses from the collection.
     */
    void clear();

    /**
     * Inserts an expense at the specified index, shifting subsequent expenses to the right.
     *
     * @param index the index at which to insert the expense
     * @param expense the expense to insert
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    void insertAt(int index, Expense expense);

    /**
     * Indicates whether this operation instance is for recurring expenses.
     *
     * @return true if the expenses are recurring, false if regular
     */
    boolean isRecurring();
}
