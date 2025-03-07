package fintrek;

import fintrek.misc.DisplayMessage;

public enum Command {
    ADD {
        @Override
        public void execute(String arguments) {
            //TODO
        }
    },
    DELETE {
        @Override
        public void execute(String arguments) {
            try {
                int expenseIndex = Integer.parseInt(arguments.trim());
                if (expenseIndex <= 0 || expenseIndex > ExpenseManager.getLength()) {
                    //TODO: print error message
                    return;
                }
                Expense removedExpense = ExpenseManager.popExpense(expenseIndex - 1);
                int remainingExpenseIndex = ExpenseManager.getLength();
                //TODO: print finish message
            } catch (NumberFormatException e) {
                //TODO: print error message (it is not a number)
            }
        }
    },
    TOTAL {
        @Override
        public void execute(String arguments) {
            try {
                double totalExpenses = ExpenseManager.getTotalExpenses();
                System.out.println(DisplayMessage.DISPLAY_TOTAL_MESSAGE + totalExpenses);
            } catch (Exception e) {
                System.out.println(DisplayMessage.ERROR_CALCULATING_TOTAL_EXPENSES + e.getMessage());
            }
        }
    },
    HELP {
        @Override
        public void execute(String arguments) {
            String keyword = arguments.toLowerCase();
            if (!arguments.isEmpty()) {
                if (keyword.contains("add")) {
                    DisplayMessage.addFormatPrinter();
                } else if (keyword.contains("delete")) {
                    DisplayMessage.deleteFormatPrinter();
                } else if (keyword.contains("total")) {
                    DisplayMessage.totalFormatPrinter();
                } else if (keyword.contains("average")) {
                    DisplayMessage.averageFormatPrinter();
                } else if (keyword.contains("summary")) {
                    DisplayMessage.summaryFormatPrinter();
                }
            } else {//print all the features
                DisplayMessage.allFeaturesPrinter();
            }
        }
    };
    //TODO: extend the Command with new features

    public final boolean emptyArg = false;
    public abstract void execute(String arguments);
}
