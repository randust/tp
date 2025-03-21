package fintrek.command;

import fintrek.misc.MessageDisplayer;
import fintrek.Expense;
import fintrek.ExpenseManager;

/**
 * Represents different commands that can be executed within the application.
 */
public enum Command {
    ADD(false) {
        @Override
        public ExecutionResult execute(String arguments) {
            String[] parts = arguments.split("\\s*\\$\\s*|\\s*/c\\s*");
            String description = "";
            if (parts.length >= 1) {
                description = parts[0].trim();
                if (description.isEmpty()) {
                    return new ExecutionResult(false, MessageDisplayer.MISSING_DESC_MESSAGE);
                }
            }

            double amount = -1;
            if (parts.length >= 2) {
                if (!parts[1].matches("\\d+(\\.\\d+)?")) {
                    return new ExecutionResult(false, MessageDisplayer.INVALID_AMT_MESSAGE);
                }
                amount = Double.parseDouble(parts[1].trim());
                assert amount > 0 : MessageDisplayer.INVALID_AMT_MESSAGE;
            }

            String category = (parts.length >= 3) ? parts[2].trim() : "Uncategorized";

            Expense addedExpense = new Expense(description, amount, category);
            ExpenseManager.addExpense(addedExpense);
            String message = String.format(MessageDisplayer.ADD_SUCCESS_MESSAGE_TEMPLATE, addedExpense);
            return new ExecutionResult(true, message);
        }
    },
    DELETE(false) {
        @Override
        public ExecutionResult execute(String arguments) {
            assert arguments != null : MessageDisplayer.EMPTY_LIST_MESSAGE;
            if (!arguments.trim().matches("\\d+")) {
                return new ExecutionResult(false, MessageDisplayer.INVALID_NUM_MESSAGE);
            }

            int expenseIndex = Integer.parseInt(arguments.trim());
            boolean inValidRange = expenseIndex <= 0 || expenseIndex > ExpenseManager.getLength();
            if (inValidRange) {
                return new ExecutionResult(false, MessageDisplayer.INVALID_NUM_MESSAGE);
            }
            Expense removedExpense = ExpenseManager.popExpense(expenseIndex-1);
            int remainingExpenseIndex = ExpenseManager.getLength();

            String message = String.format(MessageDisplayer.DELETE_SUCCESS_MESSAGE_TEMPLATE,
                    remainingExpenseIndex);
            return new ExecutionResult(true, message);
        }
    },
    LIST(true) {
        @Override
        public ExecutionResult execute(String arguments) {
            String message = String.format(MessageDisplayer.LIST_SUCCESS_MESSAGE_TEMPLATE,
                    ExpenseManager.listExpenses());
            return new ExecutionResult(true, message);
        }
    },
    //@@author venicephua
    TOTAL(true) {
        @Override
        public ExecutionResult execute(String arguments) {
            try {
                double totalExpenses = ExpenseManager.getTotalExpenses();
                assert totalExpenses >= 0 : MessageDisplayer.INVALID_AMOUNT;
                String message = String.format(MessageDisplayer.TOTAL_SUCCESS_MESSAGE_TEMPLATE,
                        totalExpenses);
                return new ExecutionResult(true, message);
            } catch (Exception e) {
                return new ExecutionResult(false, MessageDisplayer.ERROR_CALCULATING_TOTAL_EXPENSES + e.getMessage());
            }
        }
    },
    //@@author venicephua
    //@@author Charly2312
    HELP(true) {
        @Override
        public ExecutionResult execute(String arguments) {
            String message;
            if (arguments != null && !arguments.isBlank()) {
                String keyword = arguments.toLowerCase();
                if (keyword.contains("add")) {
                    message = MessageDisplayer.ADD_FORMAT_MESSAGE;
                } else if (keyword.contains("delete")) {
                    message = MessageDisplayer.DELETE_FORMAT_MESSAGE;
                } else if (keyword.contains("total")) {
                    message = MessageDisplayer.TOTAL_FORMAT_MESSAGE;
                } else if (keyword.contains("average")) {
                    message = MessageDisplayer.AVERAGE_FORMAT_MESSAGE;
                } else if (keyword.contains("summary")) {
                    message = MessageDisplayer.SUMMARY_FORMAT_MESSAGE;
                } else {
                    message = MessageDisplayer.HELP_UNKNOWN_TOPIC;
                }
            } else {
                message = MessageDisplayer.getAllFeaturesMessage();
            }
            return new ExecutionResult(true, message);
        }
    },
    //@@author Charly2312
    //@@author edwardrl101
    AVERAGE(true) {
        @Override
        public ExecutionResult execute(String arguments) {
            try {
                double averageExpense = ExpenseManager.getAverageExpenses();
                String message = String.format(MessageDisplayer.AVERAGE_SUCCESS_MESSAGE_TEMPLATE,
                        averageExpense);
                return new ExecutionResult(true, message);
            } catch (Exception e) {
                return new ExecutionResult(false, MessageDisplayer.ERROR_CALCULATING_AVERAGE_EXPENSES + e.getMessage());
            }
        }
    };
    //@@author edwardrl101
    public final boolean acceptEmptyArg;

    Command(boolean acceptEmptyArg) {
        this.acceptEmptyArg = acceptEmptyArg;
    }

    public abstract ExecutionResult execute(String arguments);
}
