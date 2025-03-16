package fintrek;

import fintrek.misc.DisplayMessage;

public class Expense {
    private final String description;
    private final double amount;
    private final String category;

    public Expense(String description, double amount, String category) {
        if (amount < 0) {
            throw new IllegalArgumentException(DisplayMessage.INVALID_AMOUNT);
        }
        this.description = description;
        this.amount = amount;
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return description + " | $" + amount + " | " + category;
    }
}
