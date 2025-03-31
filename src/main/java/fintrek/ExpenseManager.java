package fintrek;

import fintrek.misc.MessageDisplayer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class ExpenseManager {
    private static final List<Expense> expenses = new ArrayList<>();
    private static final List<Expense> recurringExpenses = new ArrayList<>();
    private static final Logger logger = Logger.getLogger(ExpenseManager.class.getName());

    public static Expense getExpense(int index) {
        assert index >= 0 && index < expenses.size() : MessageDisplayer.INVALID_IDX_MESSAGE;
        return expenses.get(index);
    }

    public static int getLength() {
        return expenses.size();
    }

    public static void addExpense(Expense expense) {
        assert expense != null : MessageDisplayer.NULL_EXPENSE_ERROR;
        logger.info("Adding expense: " + expense);
        expenses.add(expense);
    }

    public static Expense popExpense(int index) {
        logger.info("Removing expense at index: " + index);
        assert index >= 0 && index < expenses.size() : MessageDisplayer.INVALID_IDX_MESSAGE;
        Expense removedExpense = expenses.remove(index);
        logger.info("Removed expense: " + removedExpense);
        return removedExpense;
    }

    public static double getTotalExpenses() {
        double totalExpenses = 0;
        for (Expense expense : expenses) {
            assert expense.getAmount() > 0 : MessageDisplayer.INVALID_AMOUNT;
            totalExpenses += expense.getAmount();
        }
        return totalExpenses;
    }

    public static double getAverageExpenses() {
        double totalExpenses = getTotalExpenses();
        int numExpenses = getLength();
        if (numExpenses == 0) {
            return 0;
        }
        assert numExpenses > 0 : MessageDisplayer.EMPTY_LIST_MESSAGE;
        return totalExpenses / numExpenses;
    }

    public static void clearExpenses() {
        expenses.clear();
    }

    //@@author szeyingg
    public static String listExpenseBuilder(List<Expense> expenseList) {
        StringBuilder list = new StringBuilder();
        int i = 1;
        for (Expense expense : expenseList) {
            assert expense != null : MessageDisplayer.NULL_EXPENSE_ERROR;
            list.append(String.format(MessageDisplayer.LIST_EXPENSE_FORMAT, i++, expense));
        }
        return list.toString();
    }

    //@@author szeyingg
    public static String listAllExpenses() {
        if (expenses.isEmpty()) {
            return MessageDisplayer.EMPTY_LIST_MESSAGE;
        }
        return listExpenseBuilder(expenses);
    }

    //@@author Charly2312
    public static String listRecurringExpenses() {
        if (recurringExpenses.isEmpty()) {
            return MessageDisplayer.EMPTY_RECURRING_LIST_MESSAGE;
        }
        return listExpenseBuilder(recurringExpenses);
    }

    //@@author Charly2312
    public static int checkRecurringExpenseSize() {
        return recurringExpenses.size();
    }

    //@@author Charly2312
    public static void checkRecurringExpense() {
        logger.info("Going through recurring expenses list, " +
                "see if any expense matches today's date");

        assert !recurringExpenses.isEmpty() : MessageDisplayer.EMPTY_RECURRING_LIST_MESSAGE;

        int dateToday = LocalDate.now().getDayOfMonth();
        for (Expense expense: recurringExpenses) {
            LocalDate date = expense.getDate();

            //changing LocalDate to the desired format
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            //convert LocalDate to a String
            String formattedDate = date.format(formatter);

            //extract the date of the recurring expense
            int dateOfExpense = Integer.parseInt(formattedDate.substring(0, 1));

            if (dateOfExpense == dateToday) {
                insertRecurringIntoExpenses(expense);
            }
        }
    }

    //@@author Charly2312
    public static void addRecurringExpense(Expense expense) {
        assert expense != null : MessageDisplayer.NULL_EXPENSE_ERROR;
        logger.info("Adding a recurring expense: " + expense);
        recurringExpenses.add(expense);
    }

    //@@author Charly2312
    public static Expense deleteRecurringExpense(int index) {
        logger.info("Removing expense at index: " + index);
        assert index >= 0 && index < recurringExpenses.size() : MessageDisplayer.INVALID_IDX_MESSAGE;
        Expense removedExpense = recurringExpenses.remove(index);
        logger.info("Removed recurring expense: " + removedExpense);
        return removedExpense;
    }

    //@@author Charly2312
    public static void insertRecurringIntoExpenses(Expense expense) {
        assert expense != null : MessageDisplayer.NULL_EXPENSE_ERROR;
        logger.info("Adding a recurring expense into the main expense list: " + expense);
        expenses.add(expense);
    }

    //@@author venicephua
    public static List<Expense> getExpensesByCategory(String category) {
        assert category != null : MessageDisplayer.NULL_CATEGORY_MESSAGE;
        logger.info("Fetching expenses for category: " + category);
        List<Expense> categoryExpense = new ArrayList<>();

        for (Expense expense : expenses) {
            assert expense != null : MessageDisplayer.NULL_EXPENSE_ERROR;
            if (expense.getCategory().equals(category)) {
                categoryExpense.add(expense);
            }
        }
        logger.info("Total expenses found for " + category + ": " + categoryExpense.size());
        return categoryExpense;
    }

    //@@author venicephua
    public static Map<String, Double> getTotalExpensesByCategory() {
        logger.info("Calculating total expenses by category");
        Map<String, Double> categoryTotals = new HashMap<>();

        for (Expense expense : expenses) {
            assert expense != null : MessageDisplayer.NULL_EXPENSE_ERROR;
            String category = expense.getCategory();
            double currentAmount = categoryTotals.getOrDefault(category, 0.00);
            categoryTotals.put(category, currentAmount + expense.getAmount());
        }
        return categoryTotals;
    }

    //@@author venicephua
    public static String getHighestExpenseCategory() {
        logger.info("Determining highest expense category");
        Map<String, Double> categoryTotals = getTotalExpensesByCategory();

        String highestCategory = "";
        double highestAmount = 0;

        for (Map.Entry<String, Double> entry : categoryTotals.entrySet()) {
            if (entry.getValue() > highestAmount) {
                highestCategory = entry.getKey();
                highestAmount = entry.getValue();
            }
        }
        return String.format(MessageDisplayer.HIGHEST_CAT_AMT_FORMAT, highestCategory, highestAmount);
    }

    //@@author venicephua
    public static String listAllCategoryTotals() {
        Map<String, Double> categoryTotals = getTotalExpensesByCategory();

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
        String highestCategory = getHighestExpenseCategory();
        double grandTotal = getTotalExpenses();

        list.append(String.format(MessageDisplayer.HIGHEST_CAT_FORMAT,
                MessageDisplayer.SUMMARY_HIGHEST_SPEND, highestCategory));
        list.append(String.format(MessageDisplayer.GRAND_TOTAL_FORMAT,
                MessageDisplayer.SUMMARY_GRAND_TOTAL, grandTotal));

        return list.toString();
    }

    //@@author venicephua
    public static String listSingleCategoryTotal(String category) {
        Map<String, Double> categoryTotals = getTotalExpensesByCategory();
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
