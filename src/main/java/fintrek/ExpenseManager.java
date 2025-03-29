package fintrek;

import fintrek.misc.MessageDisplayer;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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

    public static String listExpenses() {
        if (expenses.isEmpty()) {
            return MessageDisplayer.EMPTY_LIST_MESSAGE;
        }
        StringBuilder list = new StringBuilder();
        int i = 1;
        for (Expense expense : expenses) {
            assert expense != null : MessageDisplayer.NULL_EXPENSE_ERROR;
            list.append(String.format("%n%d. %s", i++, expense));
        }
        return list.toString();
    }

    //@@Charly2312
    public static String listRecurringExpenses() {
        if (recurringExpenses.isEmpty()) {
            return MessageDisplayer.EMPTY_RECURRING_LIST_MESSAGE;
        }
        StringBuilder list = new StringBuilder();
        int i = 1;
        for (Expense expense : recurringExpenses) {
            assert expense != null : MessageDisplayer.NULL_EXPENSE_ERROR;
            list.append(String.format("%n%d. %s", i++, expense));
        }
        return list.toString();
    }

    //@@Charly 2312
    public static int checkRecurringExpenseSize() {
        return recurringExpenses.size();
    }

    //@@Charly2312
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

    //@@Charly2312
    public static void addRecurringExpense(Expense expense) {
        assert expense != null : MessageDisplayer.NULL_EXPENSE_ERROR;
        logger.info("Adding a recurring expense: " + expense);
        recurringExpenses.add(expense);
    }

    //@@Charly2312
    public static Expense deleteRecurringExpense(int index) {
        logger.info("Removing expense at index: " + index);
        assert index >= 0 && index < recurringExpenses.size() : MessageDisplayer.INVALID_IDX_MESSAGE;
        Expense removedExpense = recurringExpenses.remove(index);
        logger.info("Removed recurring expense: " + removedExpense);
        return removedExpense;
    }

    //@@Charly2312
    public static void insertRecurringIntoExpenses(Expense expense) {
        assert expense != null : MessageDisplayer.NULL_EXPENSE_ERROR;
        logger.info("Adding a recurring expense into the main expense list: " + expense);
        expenses.add(expense);
    }
}
