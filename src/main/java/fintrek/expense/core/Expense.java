package fintrek.expense.core;

import fintrek.misc.MessageDisplayer;

import java.time.LocalDate;

public class Expense {
    private final String description;
    private final double amount;
    private final String category;
    private LocalDate date;

    public Expense(String description, double amount, String category) {
        if (amount <= 0) {
            throw new IllegalArgumentException(MessageDisplayer.INVALID_AMOUNT);
        }
        assert amount > 0 : MessageDisplayer.INVALID_AMOUNT;
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.date = LocalDate.now();
    }

    public LocalDate getDate() {
        return date;
    }

    public void updateDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category.toUpperCase();
    }

    public double getAmount() {
        assert amount > 0 : MessageDisplayer.INVALID_AMOUNT;
        return amount;
    }

    @Override
    public String toString() {
        return description + " | $" + String.format("%.2f", amount) + " | " + getCategory() + " | " + date;
    }
}
