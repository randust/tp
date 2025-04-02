package fintrek.command.edit;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * A mutable descriptor class that holds optional updated fields for an
 * {@link fintrek.expense.core.Expense}.
 *
 * <p>This class is used by the {@link EditCommand} to represent user-provided
 * updates to an expense. Each field may be {@code null}, which indicates that the
 * corresponding property should remain unchanged.</p>
 *
 * <p>Supports trimming and parsing of user input values into their appropriate types,
 * such as {@code double} and {@code LocalDate}.</p>
 */
public class EditExpenseDescriptor {
    private String description;
    private Double amount;
    private String category;
    private LocalDate date;

    /**
     * Returns true if at least one field in the descriptor is non-null.
     *
     * @return true if any field is set, false otherwise
     */
    public boolean hasAnyField() {
        return description != null || amount != null || category != null || date != null;
    }

    /**
     * Gets the updated description, if any.
     *
     * @return the description or {@code null} if not set
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the updated description, trimming leading and trailing whitespace.
     *
     * @param description the new description as a string
     */
    public void setDescription(String description) {
        this.description = description.trim();
    }

    /**
     * Gets the updated amount, if any.
     *
     * @return the amount or {@code null} if not set
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * Sets the updated amount by parsing the given string as a double.
     *
     * @param amountStr the new amount as a string
     * @throws NumberFormatException if the string is not a valid double
     */
    public void setAmount(String amountStr) {
        this.amount = Double.parseDouble(amountStr);
    }

    /**
     * Gets the updated category, if any.
     *
     * @return the category or {@code null} if not set
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the updated category, trimming leading and trailing whitespace.
     *
     * @param category the new category as a string
     */
    public void setCategory(String category) {
        this.category = category.trim();
    }

    /**
     * Gets the updated date, if any.
     *
     * @return the date or {@code null} if not set
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Sets the updated date by parsing the given string using the format "dd-MM-yyyy".
     *
     * @param dateStr the new date as a string
     * @throws java.time.format.DateTimeParseException if the string is not a valid date
     */
    public void setDate(String dateStr) {
        this.date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }
}
