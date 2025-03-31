package fintrek.util;

import fintrek.expense.core.Expense;
import fintrek.expense.core.ExpenseOperation;

import java.time.LocalDate;
import java.util.logging.Logger;

public class RecurringExpenseProcessor {
    private static final Logger logger = Logger.getLogger(RecurringExpenseProcessor.class.getName());

    //@@Charly2312
    public static void checkAndInsertDueExpenses(ExpenseOperation recurringManager,
                                                 ExpenseOperation regularManager) {
        logger.info("Checking for recurring expenses to insert...");
        LocalDate today = LocalDate.now();

        for (Expense expense : recurringManager.getAll()) {
            LocalDate date = expense.getDate();
            if (date.getDayOfMonth() == today.getDayOfMonth()
                    && date.getMonth() == today.getMonth()) {
                logger.info("Recurring expense matched today's date: " + expense);
                regularManager.add(expense);
            }
        }
    }
}
