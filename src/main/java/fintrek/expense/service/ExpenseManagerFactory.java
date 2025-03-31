package fintrek.expense.service;

import fintrek.expense.core.ExpenseOperation;
import fintrek.expense.core.RegularExpenseManager;
import fintrek.expense.core.RecurringExpenseManager;


public class ExpenseManagerFactory {

    public enum ExpenseType {
        REGULAR, RECURRING
    }

    public static ExpenseOperation getManager(ExpenseType type) {
        return switch (type) {
        case REGULAR -> new RegularExpenseManager();
        case RECURRING -> new RecurringExpenseManager();
        };
    }
}
