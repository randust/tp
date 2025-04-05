//@@author Charly2312
package fintrek.util;

import fintrek.expense.core.Expense;
import fintrek.expense.core.ExpenseOperation;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RecurringExpenseProcessor {
    private static final Logger logger = Logger.getLogger(RecurringExpenseProcessor.class.getName());

    public static void checkAndInsertDueExpenses(ExpenseOperation recurringManager,
                                                 ExpenseOperation regularManager) {
        logger.log(Level.FINE, "Checking for recurring expenses to insert...");
        LocalDate today = LocalDate.now();

        for (Expense expense : recurringManager.getAll()) {
            LocalDate date = expense.getDate();
            if (date.getDayOfMonth() <= today.getDayOfMonth()
                    && date.getMonthValue() <= today.getMonthValue()
                    && date.getYear() <= today.getYear()
                    && !regularManager.getAll().contains(expense)) {
                logger.log(Level.FINE, "Recurring expense is due: " + expense);
                regularManager.add(expense);
            }
        }
    }
}
