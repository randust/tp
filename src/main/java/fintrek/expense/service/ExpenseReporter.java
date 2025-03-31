package fintrek.expense.service;

import fintrek.expense.core.Expense;
import fintrek.expense.core.ExpenseOperation;
import fintrek.misc.MessageDisplayer;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ExpenseReporter {
    private final ExpenseOperation manager;

    public ExpenseReporter(ExpenseOperation manager) {
        if (manager == null) {
            throw new IllegalArgumentException(MessageDisplayer.NULL_EXPENSE_ERROR);
        }
        this.manager = manager;
    }

    public double getTotal() {
        return manager.getAll().stream()
                .mapToDouble(Expense::getAmount)
                .sum();
    }

    public double getAverage() {
        int count = manager.size();
        return count == 0 ? 0 : getTotal() / count;
    }

    public Map<String, Double> getTotalByCategory() {
        return manager.getAll().stream()
                .collect(Collectors.groupingBy(
                        Expense::getCategory,
                        Collectors.summingDouble(Expense::getAmount)
                ));
    }

    public List<Expense> getExpensesByCategory(String category) {
        if (category == null) {
            throw new IllegalArgumentException(MessageDisplayer.NULL_CATEGORY_MESSAGE);
        }
        return manager.getAll().stream()
                .filter(e -> e.getCategory().equals(category))
                .collect(Collectors.toList());
    }

    public String getHighestCategory() {
        return getTotalByCategory().entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(e -> String.format(MessageDisplayer.HIGHEST_CAT_AMT_FORMAT, e.getKey(), e.getValue()))
                .orElse(MessageDisplayer.EMPTY_LIST_MESSAGE);
    }
}
