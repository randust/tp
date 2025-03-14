package fintrek;

import fintrek.misc.DisplayMessage;

public enum Command {
    ADD(false) {
        @Override
        public void execute(String arguments) {
            String[] parts = arguments.split("/");

            String description = null;
            double amount = 0.0;
            String category = null;

            if (parts.length >= 2) {
                description = parts[0].trim();
            }
            if (parts.length >= 3) {
                amount = Double.parseDouble(parts[1].trim());
            }
            if (parts.length >= 4) {
                category = parts[2].trim();
            }

            ExpenseManager.addExpense(new Expense(description, amount, category));
            System.out.println(DisplayMessage.ADD_EXPENSE + description);
        }
    },
    DELETE(false) {

        @Override
        public void execute(String arguments) {
            try {
                int expenseIndex = Integer.parseInt(arguments.trim());
                if (expenseIndex <= 0 || expenseIndex > ExpenseManager.getLength()) {
                    System.out.println(DisplayMessage.INVALID_NUM_MESSAGE);
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
    };
    //TODO: extend the Command with new features

    public final boolean acceptEmptyArg;
    private Command(boolean acceptEmptyArg){
        this.acceptEmptyArg = acceptEmptyArg;
    }
    public abstract void execute(String arguments);
}
