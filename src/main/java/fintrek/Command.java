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
    };
    //TODO: extend the Command with new features

    public abstract void execute(String arguments);
}
