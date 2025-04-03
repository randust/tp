package fintrek.util;

import fintrek.expense.core.Expense;
import fintrek.expense.core.RegularExpenseManager;
import fintrek.expense.core.RecurringExpenseManager;
import fintrek.misc.MessageDisplayer;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/// @deprecated Use ExpenseService and ExpenseReporter instead.
/// This class temporarily delegates to singleton managers.
@Deprecated
public class ExpenseManager {
    private static final Logger logger = Logger.getLogger(ExpenseManager.class.getName());

    private static final RegularExpenseManager regularManager = RegularExpenseManager.getInstance();
    private static final RecurringExpenseManager recurringManager = RecurringExpenseManager.getInstance();

    public static Expense getExpense(int index) {
        return regularManager.get(index);
    }

    public static int getLength() {
        return regularManager.getLength();
    }

    public static void addExpense(Expense expense) {
        regularManager.add(expense);
    }

    public static Expense popExpense(int index) {
        return regularManager.remove(index);
    }

    public static double getTotalExpenses() {
        return regularManager.getAll().stream()
                .mapToDouble(Expense::getAmount)
                .sum();
    }

    public static double getAverageExpenses() {
        int count = getLength();
        return count == 0 ? 0 : getTotalExpenses() / count;
    }

    public static void clearExpenses() {
        regularManager.clear();
    }

    public static String listExpenseBuilder(List<Expense> expenseList) {
        StringBuilder list = new StringBuilder();
        int i = 1;
        for (Expense expense : expenseList) {
            assert expense != null : MessageDisplayer.NULL_EXPENSE_ERROR;
            list.append(String.format(MessageDisplayer.LIST_EXPENSE_FORMAT, i++, expense));
        }
        return list.toString();
    }

    public static String listAllExpenses() {
        List<Expense> expenses = regularManager.getAll();
        if (expenses.isEmpty()) {
            return MessageDisplayer.EMPTY_LIST_MESSAGE;
        }
        return listExpenseBuilder(expenses);
    }

    public static String listRecurringExpenses() {
        List<Expense> recurring = recurringManager.getAll();
        if (recurring.isEmpty()) {
            return MessageDisplayer.EMPTY_RECURRING_LIST_MESSAGE;
        }
        return listExpenseBuilder(recurring);
    }

    public static int checkRecurringExpenseSize() {
        return recurringManager.getLength();
    }

    public static void checkRecurringExpense() {
        logger.info("Checking for recurring expenses matching today's date...");
        LocalDate today = LocalDate.now();

        for (Expense expense : recurringManager.getAll()) {
            if (expense.getDate().getDayOfMonth() <= today.getDayOfMonth()
                    && expense.getDate().getMonthValue() <= today.getMonthValue()) {
                logger.info("Recurring expense matched today: " + expense);
                regularManager.add(expense);
            }
        }
    }

    public static void addRecurringExpense(Expense expense) {
        recurringManager.add(expense);
    }

    public static Expense deleteRecurringExpense(int index) {
        return recurringManager.remove(index);
    }

    public static Expense getRecurringExpense(int index) {
        return recurringManager.get(index);
    }

    public static void insertRecurringIntoExpenses(Expense expense) {
        regularManager.add(expense);
    }

    public static void clearRecurringExpenses() {
        recurringManager.clear();
    }

    public static List<Expense> getExpensesByCategory(String category) {
        return regularManager.getAll().stream()
                .filter(e -> e.getCategory().equals(category))
                .toList();
    }

    public static Map<String, Double> getTotalExpensesByCategory() {
        return regularManager.getAll().stream()
                .collect(java.util.stream.Collectors.groupingBy(
                        Expense::getCategory,
                        java.util.stream.Collectors.summingDouble(Expense::getAmount)
                ));
    }

    public static double getTotalByMonth(int year, int month) {
        return regularManager.getAll().stream()
                .filter(expense -> expense.getDate().getYear() == year
                        && expense.getDate().getMonthValue() == month) // Filter by year and month
                .collect(Collectors.summingDouble(Expense::getAmount)); // Sum the amounts of the filtered expenses
    }


    public static String getHighestExpenseCategory() {
        return getTotalExpensesByCategory().entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(e
                        -> String.format(
                                MessageDisplayer.HIGHEST_CAT_AMT_FORMAT, e.getKey(), e.getValue()))
                .orElse(MessageDisplayer.EMPTY_LIST_MESSAGE);
    }

    public static String listAllCategoryTotals() {
        Map<String, Double> totals = getTotalExpensesByCategory();
        if (totals.isEmpty()) {
            return MessageDisplayer.EMPTY_LIST_MESSAGE;
        }
        StringBuilder list = new StringBuilder();
        totals.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> {
                    String category = entry.getKey();
                    double amount = entry.getValue();
                    list.append(String.format(MessageDisplayer.CAT_AMT_FORMAT, category, amount));
                });
        list.append(String.format(MessageDisplayer.HIGHEST_CAT_FORMAT,
                MessageDisplayer.SUMMARY_HIGHEST_SPEND, getHighestExpenseCategory()));
        list.append(String.format(MessageDisplayer.GRAND_TOTAL_FORMAT,
                MessageDisplayer.SUMMARY_GRAND_TOTAL, getTotalExpenses()));
        return list.toString();
    }

    public static String listSingleCategoryTotal(String category) {
        Map<String, Double> totals = getTotalExpensesByCategory();
        if (!totals.containsKey(category)) {
            return MessageDisplayer.CATEGORY_NOT_FOUND;
        }
        double amount = totals.get(category);
        List<Expense> matching = getExpensesByCategory(category);
        return String.format(MessageDisplayer.CAT_AMT_FORMAT, category, amount)
                + listExpenseBuilder(matching);
    }
}
