package fintrek.command.edit;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EditExpenseDescriptor {
    private String description;
    private Double amount;
    private String category;
    private LocalDate date;

    public boolean hasAnyField() {
        return description != null || amount != null || category != null || date != null;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description.trim();
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(String amountStr) {
        this.amount = Double.parseDouble(amountStr);
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category.trim();
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(String dateStr) {
        this.date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd-MM-yyyy")); // assumes yyyy-MM-dd format
    }
}
