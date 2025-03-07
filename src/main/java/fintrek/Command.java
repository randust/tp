package fintrek;

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
                System.out.println("Total expenses: " + totalExpenses);
            } catch (Exception e) {
                System.out.println(ERR_CALCULATING_TOTAL + e.getMessage());
            }
        }
    };
    //TODO: extend the Command with new features


    public static final String ERR_CALCULATING_TOTAL = "Error calculating total expenses: ";

    public abstract void execute(String arguments);
}
