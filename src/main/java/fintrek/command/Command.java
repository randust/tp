package fintrek.command;

import fintrek.misc.DisplayMessage;
import fintrek.Expense;
import fintrek.ExpenseManager;

/**
 * Represents different commands that can be executed within the application.
 */
public enum Command {
    ADD(false) {
        @Override
        public ExecutionResult execute(String arguments) {
            String[] parts = arguments.split("\\$|/c");

            String description = null;
            double amount = 0.0;
            String category = null;

            if (parts.length >= 1) {
                description = parts[0].trim();
            }
            if (parts.length >= 2) {
                amount = Double.parseDouble(parts[1].trim());
            }
            if (parts.length >= 3) {
                category = parts[2].trim();
            }

            Expense e = new Expense(description, amount, category);
            ExpenseManager.addExpense(e);
            System.out.println(DisplayMessage.ADD_EXPENSE + e);
            return new ExecutionResult(true, DisplayMessage.ADD_SUCCESS_MESSAGE);
        }
    },
    DELETE(false) {
        @Override
        public ExecutionResult execute(String arguments) {
            assert arguments != null;
            if (!arguments.trim().matches("\\d+")) {
                return new ExecutionResult(false, DisplayMessage.INVALID_NUM_MESSAGE);
            }

            int expenseIndex = Integer.parseInt(arguments.trim());
            boolean inValidRange = expenseIndex <= 0 || expenseIndex > ExpenseManager.getLength();
            if (inValidRange) {
                return new ExecutionResult(false, DisplayMessage.INVALID_NUM_MESSAGE);
            }

            Expense removedExpense = ExpenseManager.popExpense(expenseIndex - 1);
            int remainingExpenseIndex = ExpenseManager.getLength();

            String message = String.format(DisplayMessage.DELETE_SUCCESS_MESSAGE_TEMPLATE, remainingExpenseIndex);
            return new ExecutionResult(true, message);
        }
    },
    LIST(true) {
        @Override
        public ExecutionResult execute(String arguments) {
            ExpenseManager.listExpenses();
            return new ExecutionResult(true, DisplayMessage.LIST_SUCCESS_MESSAGE);
        }
    },
    TOTAL(true) {
        @Override
        public ExecutionResult execute(String arguments) {
            try {
                double totalExpenses = ExpenseManager.getTotalExpenses();
                String message = String.format(DisplayMessage.TOTAL_SUCCESS_MESSAGE_TEMPLATE, totalExpenses);
                return new ExecutionResult(true, message);
            } catch (Exception e) {
                return new ExecutionResult(false, DisplayMessage.ERROR_CALCULATING_TOTAL_EXPENSES + e.getMessage());
            }
        }
    },
    HELP(true) {
        @Override
        public ExecutionResult execute(String arguments) {
            String message;
            if (arguments != null && !arguments.isBlank()) {
                String keyword = arguments.toLowerCase();
                if (keyword.contains("add")) {
                    message = DisplayMessage.ADD_FORMAT_MESSAGE;
                } else if (keyword.contains("delete")) {
                    message = DisplayMessage.DELETE_FORMAT_MESSAGE;
                } else if (keyword.contains("total")) {
                    message = DisplayMessage.TOTAL_FORMAT_MESSAGE;
                } else if (keyword.contains("average")) {
                    message = DisplayMessage.AVERAGE_FORMAT_MESSAGE;
                } else if (keyword.contains("summary")) {
                    message = DisplayMessage.SUMMARY_FORMAT_MESSAGE;
                } else {
                    message = DisplayMessage.HELP_UNKNOWN_TOPIC;
                }
            } else {
                message = DisplayMessage.getAllFeaturesMessage();
            }
            return new ExecutionResult(true, message);
        }
    },
    AVERAGE(true) {
        @Override
        public ExecutionResult execute(String arguments) {
            try {
                double averageExpense = ExpenseManager.getAverageExpenses();
                String message = String.format(DisplayMessage.AVERAGE_SUCCESS_MESSAGE_TEMPLATE, averageExpense);
                return new ExecutionResult(true, message);
            } catch (Exception e) {
                return new ExecutionResult(false, DisplayMessage.ERROR_CALCULATING_AVERAGE_EXPENSES + e.getMessage());
            }
        }
    };

    public final boolean acceptEmptyArg;

    Command(boolean acceptEmptyArg) {
        this.acceptEmptyArg = acceptEmptyArg;
    }

    public abstract ExecutionResult execute(String arguments);
}
