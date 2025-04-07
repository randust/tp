//@@author Charly2312
package fintrek.util;

import fintrek.expense.core.Expense;
import fintrek.expense.core.ExpenseOperation;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is responsible for checking and inserting any recurring expenses
 * that are set to be added on the current date, while logging all state-changing
 * actions.
 */
public class RecurringExpenseProcessor {
    private static final Logger logger = Logger.getLogger(RecurringExpenseProcessor.class.getName());

    /**
     * Checks all recurring expenses and inserts those that are set to be added on
     * today's date, given that the date on the recurring expense is not in the future, e.g.
     * if today is 06 April 2025 and the recurring expense is dated 06 May 2025, then it will
     * not be added to the list.
     * @param recurringManager the recurring expense manager, which is used to go through all
     *                         recurring expenses.
     * @param regularManager the regular expense manager, which is used to add any recurring expense
     *                       into the main list.
     */
    public static void checkAndInsertDueExpenses(ExpenseOperation recurringManager,
                                                 ExpenseOperation regularManager) {
        logger.log(Level.FINE, "Checking for recurring expenses to insert...");
        LocalDate today = LocalDate.now();

        for (Expense expense : recurringManager.getAll()) {
            LocalDate date = expense.getDate();
            if (date.getDayOfMonth() == today.getDayOfMonth()
                    && date.getMonthValue() <= today.getMonthValue()
                    && date.getYear() <= today.getYear()
                    && !regularManager.getAll().contains(expense)) {
                logger.log(Level.FINE, "Recurring expense is due: " + expense);
                regularManager.add(expense);
            }
        }
    }
}
