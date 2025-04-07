package fintrek.budget.core;


/**
 * Singleton manager for handling monthly budgets.
 *
 * This class provides the operations to get and set the current monthly budget.
 */
public class BudgetManager {

    /** The singleton instance of this manager. */
    private static final BudgetManager instance = new BudgetManager();
    private double monthlyBudget;

    /** Private constructor to prevent instantiation and to enforce Singleton pattern */
    private BudgetManager() {
        this.monthlyBudget = 0.0; // Default budget is 0
    }

    /**
     * Returns the singleton instance of this manager.
     *
     * @return the shared instance of {@code BudgetManager}
     */
    public static BudgetManager getInstance() {
        return instance;
    }

    /**
     * Returns the current monthly budget.
     * @return a {@code double} object containing the current monthly budget.
     */
    public double getBudget() {
        return monthlyBudget;
    }

    /**
     * Sets the monthly budget to the desired amount.
     * @param amount the desired monthly budget
     */
    public void setBudget(double amount) {
        this.monthlyBudget = amount;
    }

    /**
     * Indicates whether monthly budget has been set by checking if it is greater than 0.
     * @return a {@code boolean} value indicating whether budget has been set.
     */
    public boolean isBudgetSet() {
        return monthlyBudget > 0;
    }

    /**
     * Overrides the toString() method in Java and returns the String equivalent
     * of the monthly budget.
     * @return a {@code String} equivalent object of the monthly budget.
     */
    @Override
    public String toString() {
        return "Monthly Budget: $" + monthlyBudget;
    }
}
