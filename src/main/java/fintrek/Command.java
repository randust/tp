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
    };
    //TODO: extend the Command with new features

    public final boolean emptyArg = false;
    public abstract void execute(String arguments);
}
