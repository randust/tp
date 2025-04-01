package fintrek.command.edit;

public class EditExpenseDescriptor {
    private String description;
    private Double amount;
    private String category;

    public boolean hasAnyField() {
        return description != null || amount != null || category != null;
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
}
