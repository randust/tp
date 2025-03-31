package fintrek.expense.service;

import fintrek.expense.core.Expense;
import fintrek.expense.core.ExpenseOperation;
import fintrek.misc.MessageDisplayer;

import java.util.ArrayList;
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

    //@@venicephua
    public double getTotal() {
        return manager.getAll().stream()
                .mapToDouble(Expense::getAmount)
                .sum();
    }

    //@@edwardrl101
    public double getAverage() {
        int count = manager.getLength();
        return count == 0 ? 0 : getTotal() / count;
    }

    //@@venicephua
    public Map<String, Double> getTotalByCategory() {
        return manager.getAll().stream()
                .collect(Collectors.groupingBy(
                        Expense::getCategory,
                        Collectors.summingDouble(Expense::getAmount)
                ));
    }

    //@@venicephua
    public List<Expense> getExpensesByCategory(String category) {
        if (category == null) {
            throw new IllegalArgumentException(MessageDisplayer.NULL_CATEGORY_MESSAGE);
        }
        return manager.getAll().stream()
                .filter(e -> e.getCategory().equals(category))
                .collect(Collectors.toList());
    }

    //@@venicephua
    public String getHighestCategory() {
        return getTotalByCategory().entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(e -> String.format(MessageDisplayer.HIGHEST_CAT_AMT_FORMAT, e.getKey(), e.getValue()))
                .orElse(MessageDisplayer.EMPTY_LIST_MESSAGE);
    }

    //@@szeyingg - helper method for building an expense list string
    private String listExpenseBuilder(List<Expense> expenseList) {
        StringBuilder list = new StringBuilder();
        int i = 1;
        for (Expense expense : expenseList) {
            assert expense != null : MessageDisplayer.NULL_EXPENSE_ERROR;
            list.append(String.format(MessageDisplayer.LIST_EXPENSE_FORMAT, i++, expense));
        }
        return list.toString();
    }

    //@@randust
    public String listExpenses() {
        List<Expense> expenses = manager.getAll();
        if (expenses.isEmpty()) {
            return manager.isRecurring()
                    ? MessageDisplayer.EMPTY_RECURRING_LIST_MESSAGE
                    : MessageDisplayer.EMPTY_LIST_MESSAGE;
        }
        return listExpenseBuilder(expenses);
    }


    //@@venicephua
    public String listAllCategoryTotals() {
        Map<String, Double> categoryTotals = getTotalByCategory();
        if (categoryTotals.isEmpty()) {
            return MessageDisplayer.EMPTY_LIST_MESSAGE;
        }
        StringBuilder list = new StringBuilder();
        List<Map.Entry<String, Double>> sortedCategories = new ArrayList<>(categoryTotals.entrySet());
        sortedCategories.sort(Map.Entry.comparingByKey());
        for (Map.Entry<String, Double> entry : sortedCategories) {
            String category = entry.getKey();
            double amount = entry.getValue();
            assert amount >= 0 : MessageDisplayer.INVALID_AMOUNT;
            list.append(String.format(MessageDisplayer.CAT_AMT_FORMAT, category, amount));
        }
        String highestCategory = getHighestCategory();
        double grandTotal = getTotal();
        list.append(String.format(MessageDisplayer.HIGHEST_CAT_FORMAT,
                MessageDisplayer.SUMMARY_HIGHEST_SPEND, highestCategory));
        list.append(String.format(MessageDisplayer.GRAND_TOTAL_FORMAT,
                MessageDisplayer.SUMMARY_GRAND_TOTAL, grandTotal));
        return list.toString();
    }

    //@@venicephua
    public String listSingleCategoryTotal(String category) {
        Map<String, Double> categoryTotals = getTotalByCategory();
        if (!categoryTotals.containsKey(category)) {
            return MessageDisplayer.CATEGORY_NOT_FOUND;
        }
        StringBuilder list = new StringBuilder();
        double amount = categoryTotals.get(category);
        assert amount >= 0 : MessageDisplayer.INVALID_AMOUNT;
        list.append(String.format(MessageDisplayer.CAT_AMT_FORMAT, category, amount));
        List<Expense> categoryExpenses = getExpensesByCategory(category);
        list.append(listExpenseBuilder(categoryExpenses));
        return list.toString();
    }
}
