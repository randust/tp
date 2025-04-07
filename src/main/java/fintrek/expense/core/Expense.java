package fintrek.expense.core;

import fintrek.misc.MessageDisplayer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Expense {
    private final String description;
    private final double amount;
    private final String category;
    private LocalDate date;

    /**
     * Constructor for the expense object based on the parameters below
     * @param description is the description of the expense
     * @param amount is the amount spent in this expense
     * @param category is the category of the expense
     * @param date is when the expense is made
     */
    public Expense(String description, double amount, String category, LocalDate date) {
        if (amount <= 0) {
            throw new IllegalArgumentException(MessageDisplayer.INVALID_AMOUNT);
        }
        assert amount > 0 : MessageDisplayer.INVALID_AMOUNT;
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.date = date;
    }

    /**
     * Gets the date for the expense.
     * @return the date for the expense.
     */
    public LocalDate getDate() {
        return date;
    }

    /** Updates the date for the expense */
    public void updateDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Gets the expense description.
     * @return the expense description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the expense category.
     * @return the expense category.
     */
    public String getCategory() {
        return category.toUpperCase();
    }

    /**
     * Gets the expense amount.
     * @return the expense amount.
     */
    public double getAmount() {
        assert amount > 0 : MessageDisplayer.INVALID_AMOUNT;
        return amount;
    }

    /**
     * Overrides the original toString() method in Java.
     * @return a {@code String} equivalent of the expense, in the format as follows,
     * "DESCRIPTION | $AMOUNT | CATEGORY | DATE" where DATE is in the form "dd-MM-yyyy".
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return description + " | $" + String.format("%.2f", amount) + " | "
                + category.toUpperCase() + " | " + date.format(formatter);
    }
}
