package fintrek.budget;

public class BudgetManager {
    // Singleton Instance
    private static final BudgetManager instance = new BudgetManager();
    private double monthlyBudget;

    private BudgetManager() {
        this.monthlyBudget = 0.0; // Default budget is 0
    }

    public static BudgetManager getInstance() {
        return instance;
    }

    public double getBudget() {
        return monthlyBudget;
    }

    public void setBudget(double amount) {
        this.monthlyBudget = amount;
    }

    public boolean isBudgetSet() {
        return monthlyBudget > 0;
    }

    @Override
    public String toString() {
        return "Monthly Budget: $" + monthlyBudget;
    }
}
