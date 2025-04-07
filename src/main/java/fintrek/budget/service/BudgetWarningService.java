package fintrek.budget.service;

import fintrek.budget.core.BudgetManager;
import fintrek.expense.service.AppServices;
import fintrek.misc.MessageDisplayer;

import java.time.LocalDate;

public class BudgetWarningService {
    public static final double NINETY_PERCENT = 0.9;

    /**
     * This function will generate warnings if the total regular expenses of the current month
     * almost exceeds or exceeds the current monthly budget. Note that expenses in
     * the previous month will not be accounted for anymore.
     * @param date the date today, which will be used to generate the current year and month
     * @return warnings depending on whether total expenses exceeds or almost exceeds the monthly budget.
     */
    public static String generateBudgetWarnings(LocalDate date) {
        BudgetManager budgetManager = BudgetManager.getInstance();
        if (!budgetManager.isBudgetSet()) {
            return ""; // No budget set, no warning needed
        }

        double budget = budgetManager.getBudget();
        double totalExpenses = AppServices.REGULAR_REPORTER.getTotalByMonthOfYear(date.getYear(), date.getMonthValue());

        if (totalExpenses >= budget) {
            return String.format(MessageDisplayer.EXCEEDED_BUDGET_MESSAGE, budget, totalExpenses-budget);
        } else if (totalExpenses >= budget * NINETY_PERCENT) {
            return String.format(MessageDisplayer.ALMOST_EXCEEDED_BUDGET_MESSAGE, budget-totalExpenses, budget);
        }
        return "";
    }
}
