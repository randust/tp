package fintrek.expense.service;

import fintrek.expense.core.RegularExpenseManager;
import fintrek.expense.core.RecurringExpenseManager;

/**
 * This class helps to create new reporter and service from the ExpenseService
 *          and ExpenseReporter. This will be done depending on if it is for
 *          a regular or recurring expense.
 * This reporter and service will be used by other classes to call the
 *          respective functions for either regular or recurring expenses.
 */
public class AppServices {
    public static final ExpenseService REGULAR_SERVICE =
            new ExpenseService(RegularExpenseManager.getInstance());

    public static final ExpenseService RECURRING_SERVICE =
            new ExpenseService(RecurringExpenseManager.getInstance());

    public static final ExpenseReporter REGULAR_REPORTER =
            new ExpenseReporter(RegularExpenseManager.getInstance());

    public static final ExpenseReporter RECURRING_REPORTER =
            new ExpenseReporter(RecurringExpenseManager.getInstance());

    private AppServices() {
        // Prevent instantiation
    }
}
