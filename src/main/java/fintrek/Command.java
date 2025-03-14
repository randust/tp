package fintrek;

import fintrek.misc.DisplayMessage;

public enum Command {
    ADD(false) {
        @Override
        public void execute(String arguments) {
            //TODO
        }
    },
    DELETE(false) {
        @Override
        public void execute(String arguments) {
            // Ensures only positive integers are allowed
            if (!arguments.trim().matches("\\d+")) {
                System.out.println(DisplayMessage.INVALID_NUM_MESSAGE);
                return;
            }
            // Check if the index is within the valid range
            int expenseIndex = Integer.parseInt(arguments.trim());
            if (expenseIndex <= 0 || expenseIndex > ExpenseManager.getLength()) {
                System.out.println(DisplayMessage.INVALID_NUM_MESSAGE);
                return;
            }

            Expense removedExpense = ExpenseManager.popExpense(expenseIndex - 1);
            int remainingExpenseIndex = ExpenseManager.getLength();
            //TODO: print finish message

        }
    },
    TOTAL(true) {
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
    HELP(true) {
        @Override
        public void execute(String arguments) {
            if (!(arguments == null || arguments.isBlank())) {
                String keyword = arguments.toLowerCase();
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
    },
    AVERAGE(true) {
        @Override
        public void execute(String arguments) {
            try {
                double averageExpense = ExpenseManager.getAverageExpenses();
                System.out.println(DisplayMessage.DISPLAY_AVERAGE_MESSAGE + averageExpense);
            } catch (Exception e) {
                System.out.println(DisplayMessage.ERROR_CALCULATING_AVERAGE_EXPENSES + e.getMessage());
            }
        }
    };
    //TODO: extend the Command with new features

    public final boolean acceptEmptyArg;
    private Command(boolean acceptEmptyArg){
        this.acceptEmptyArg = acceptEmptyArg;
    }
    public abstract void execute(String arguments);
}
