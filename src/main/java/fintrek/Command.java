package fintrek;

import fintrek.misc.DisplayMessage;

public enum Command {
    ADD {
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


    public abstract void execute(String arguments);
}
