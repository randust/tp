package fintrek.expense.service;

import fintrek.expense.core.Expense;
import fintrek.expense.core.ExpenseOperation;
import fintrek.misc.MessageDisplayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class holds all the functions that will report a result to the user
 *
 * Some examples include total, average and get highest
 */
public class ExpenseReporter {
    private final ExpenseOperation manager;

    public ExpenseReporter(ExpenseOperation manager) {
        if (manager == null) {
            throw new IllegalArgumentException(MessageDisplayer.NULL_EXPENSE_ERROR);
        }
        this.manager = manager;
    }

    //@@author venicephua
    public double getTotal() {
        double total = manager.getAll().stream()
                .mapToDouble(Expense::getAmount)
                .sum();
        if (total > MessageDisplayer.MAX_AMOUNT) {
            return -1;
        }
        return total;
    }

    //@@author edwardrl101

    /**
     * Obtains the total expense in a particular month of a year
     * @param year the desired year in the form "yyyy"
     * @param month the desired month in the form "MM" where MM is between 1 and 12 inclusive
     * @return the total expense in a particular month of a year
     */
    public double getTotalByMonthOfYear(int year, int month) {
        return manager.getAll().stream()
                .filter(expense -> expense.getDate().getYear() == year
                        && expense.getDate().getMonthValue() == month) // Filter by year and month
                .collect(Collectors.summingDouble(Expense::getAmount)); // Sum the amounts of the filtered expenses
    }

    public double getAverage() {
        int count = manager.getLength();
        return count == 0 ? 0 : getTotal() / count;
    }

    //@@author szeyingg - helper method for building an expense list string
    public String listExpenseBuilder(List<Expense> expenseList) {
        StringBuilder list = new StringBuilder();
        int i = 1;
        for (Expense expense : expenseList) {
            assert expense != null : MessageDisplayer.NULL_EXPENSE_ERROR;
            list.append(String.format(MessageDisplayer.LIST_EXPENSE_FORMAT, i++, expense));
        }
        return list.toString();
    }

    public String listExpenses() {
        List<Expense> expenses = manager.getAll();
        if (expenses.isEmpty()) {
            return manager.isRecurring()
                    ? MessageDisplayer.EMPTY_RECURRING_LIST_MESSAGE
                    : MessageDisplayer.EMPTY_LIST_MESSAGE;
        }
        return listExpenseBuilder(expenses);
    }

    //@@author venicephua
    /**
     * Retrieves the total amount spent for each expense category.
     *
     * @return A map where keys are category names and values are the total amounts spent.
     * */
    public Map<String, Double> getTotalByCategory() {
        return manager.getAll().stream()
                .collect(Collectors.groupingBy(
                        Expense::getCategory,
                        Collectors.summingDouble(Expense::getAmount)
                ));
    }

    /**
     * Retrieves a list of expenses belonging to a specified category.
     *
     * @param category The category name to filter expenses by.
     * @return A list of expenses in the specified category.
     * @throws IllegalArgumentException if the category is null.
     */
    public List<Expense> getExpensesByCategory(String category) {
        if (category == null) {
            throw new IllegalArgumentException(MessageDisplayer.NULL_CATEGORY_MESSAGE);
        }
        return manager.getAll().stream()
                .filter(e -> e.getCategory().equals(category))
                .collect(Collectors.toList());
    }

    /**
     * Determines the category with the highest total expense.
     *
     * @return A formatted string indicating the highest spending category and the total amount spent.
     *         Returns a predefined message if there are no expenses.
     */
    public String getHighestCategory(Map<String, Double> categoryTotals) {
        return categoryTotals.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(e -> String.format(MessageDisplayer.HIGHEST_CAT_AMT_FORMAT, e.getKey(), e.getValue()))
                .orElse(MessageDisplayer.EMPTY_LIST_MESSAGE);
    }

    /**
     * Lists total expenses for all categories in a formatted string.
     *
     * @param categoryTotals The map where keys are category names and values are the total amounts spent.
     * @return A formatted string displaying category-wise totals, the highest spending category, and the grand total.
     *         Returns a predefined message if there are no expenses.
     */
    public String listAllCategoryTotals(Map<String, Double> categoryTotals) {
        StringBuilder list = new StringBuilder();
        List<Map.Entry<String, Double>> sortedCategories = new ArrayList<>(categoryTotals.entrySet());
        sortedCategories.sort(Map.Entry.comparingByKey());
        for (Map.Entry<String, Double> entry : sortedCategories) {
            String category = entry.getKey();
            double amount = entry.getValue();
            assert amount >= 0 : MessageDisplayer.INVALID_AMOUNT;
            list.append(String.format(MessageDisplayer.CAT_AMT_FORMAT, category, amount));
        }
        String highestCategory = getHighestCategory(categoryTotals);
        double grandTotal = getTotal();
        list.append(String.format(MessageDisplayer.HIGHEST_CAT_FORMAT,
                MessageDisplayer.SUMMARY_HIGHEST_SPEND, highestCategory));
        list.append(String.format(MessageDisplayer.GRAND_TOTAL_FORMAT,
                MessageDisplayer.SUMMARY_GRAND_TOTAL, grandTotal));
        return list.toString();
    }

    /**
     * Lists the total expense for a single category in a formatted string.
     *
     * @param categoryTotals The map where keys are category names and values are the total amounts spent.
     * @param category The category name to retrieve the total expense for.
     * @return A formatted string displaying the total amount spent for the specified category,
     *         along with details of individual expenses in that category.
     *         Returns a predefined message if the category is not found.
     */
    public String listSingleCategoryTotal(Map<String, Double> categoryTotals, String category) {
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
