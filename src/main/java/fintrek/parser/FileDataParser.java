package fintrek.parser;

import fintrek.budget.BudgetManager;
import fintrek.expense.core.CategoryManager;
import fintrek.expense.core.Expense;
import fintrek.expense.core.RecurringExpenseManager;
import fintrek.expense.core.RegularExpenseManager;
import fintrek.misc.MessageDisplayer;
import fintrek.util.InputValidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Parses saved expense file data into an Expense object.
 */
public class FileDataParser implements CommandParser<ParseResult<Void>> {

    private static final FileDataParser INSTANCE = new FileDataParser(); // singleton

    /**
     * Parses a line in the "data.txt" save file
     * @param fileData the raw file data/line to be parsed
     * @return a {@code ParseResult<Void>} representing the success or failure of the parsing.
     *      On success, the result is {@code null} and on failure, it contains the error message on
     *      why the parsing failed
     */
    public static ParseResult<Void> parseFileData(String fileData) {
        return INSTANCE.parse(fileData); // delegates to the instance method
    }

    /**
     * This function is to parse a budget saved in the .txt file
     *               by first checking if it is the right format
     * @param line the raw input string (e.g. user command arguments)
     * @return ParseResult signifying if an expense from the .txt file can be parsed properly
     */
    public ParseResult<Void> parseBudgetFromLine(String line) {
        String budgetStr = line.substring("Monthly Budget: $".length()).trim();
        if(!InputValidator.isValidPositiveDouble(budgetStr)) {
            return ParseResult.failure(MessageDisplayer.INVALID_LOAD_BUDGET_AMOUNT_MESSAGE);
        }
        double budget = Double.parseDouble(budgetStr);
        BudgetManager.getInstance().setBudget(budget);
        return ParseResult.success(null);
    }

    /**
     * Checks if a particular line in the "data.txt" save file is of the format
     * "Monthly Budget: $" which is how the monthly budget is saved
     * @param line a line in the "data.txt" save file
     * @return a {@code Boolean} value stating whether the line is of the format of
     *      how the monthly budget is saved
     */
    public Boolean isOfBudgetFormat(String line) {
        return line.startsWith("Monthly Budget: $");
    }

    /**
     * This function is to parse a budget saved in the .txt file
     *               by first checking if it is the right format
     * @param line the raw input string (e.g. user command arguments)
     * @return ParseResult signifying if an expense from the .txt file can be parsed properly
     */
    public ParseResult<Void> parseCategoryFromLine(String line) {
        String categoryStr = line.substring("Custom Categories: ".length()).trim();
        String[] categories = categoryStr.split(",");

        for (String category : categories) {
            if (!InputValidator.isValidStringLength(category) || InputValidator.isNullOrBlank(category)) {
                CategoryManager.clearCustomCategories();
                return ParseResult.failure(MessageDisplayer.CATEGORY_LOAD_ERROR_MESSAGE);
            }
            CategoryManager.addCustomCategory(category);
        }
        return ParseResult.success(null);
    }

    /**
     * Checks if a particular line in the "data.txt" save file is of the format
     * "Monthly Budget: $" which is how the monthly budget is saved
     * @param line a line in the "data.txt" save file
     * @return a {@code Boolean} value stating whether the line is of the format of
     *      how the monthly budget is saved
     */
    public Boolean isOfCategoryFormat(String line) {
        return line.startsWith("Custom Categories: ");
    }

    /**
     * This function is to parse an expense saved in the .txt file
     * @param fileData the raw input string (e.g. user command arguments)
     * @return ParseResult signifying if an expense from the .txt file can be parsed properly
     */
    @Override
    public ParseResult<Void> parse(String fileData) {
        if (InputValidator.isNullOrBlank(fileData)) {
            return ParseResult.failure(MessageDisplayer.EMPTY_DATA_MESSAGE);
        }
        if(isOfBudgetFormat(fileData)) {
            return parseBudgetFromLine(fileData);
        }

        if(isOfCategoryFormat(fileData)) {
            return parseCategoryFromLine(fileData);
        }

        String[] tokens = fileData.trim().split("\\|", 5);
        if (tokens.length < 2) {
            return ParseResult.failure(MessageDisplayer.EMPTY_AMOUNT_DATA_MESSAGE);
        }
        if (tokens.length < 3) {
            return ParseResult.failure(MessageDisplayer.EMPTY_CATEGORY_DATA_MESSAGE);
        }
        if (tokens.length < 4) {
            return ParseResult.failure(MessageDisplayer.EMPTY_DATE_DATA_MESSAGE);
        }
        if (tokens.length >= 4) {
            return processExpense(tokens);
        }
        return ParseResult.failure(MessageDisplayer.INVALID_DATA_FORMAT_MESSAGE);
    }

    /**
     * This function process all the variables in the form of an array
     *               needed to create a new expense
     * @param tokens contain the variables such as description, amount,
     *               category and date
     * @return the ParseResult which depends on whether the processing
     *               is successful or failed
     */
    private ParseResult<Void> processExpense(String[] tokens) {
        String description = tokens[0].trim();
        String amountStr = tokens[1].trim().substring(1);
        String category = tokens[2].trim();
        String dateStr = tokens[3].trim();
        boolean isRecurring = tokens.length == 5 && tokens[4].trim().equals("R");

        if (InputValidator.isNullOrBlank(description)) {
            return ParseResult.failure(MessageDisplayer.EMPTY_DESC_DATA_MESSAGE);
        }

        if (!InputValidator.isValidPositiveDouble(amountStr)) {
            return ParseResult.failure(MessageDisplayer.INVALID_AMT_DATA_MESSAGE);
        }

        double amount = Double.parseDouble(amountStr);

        if (!InputValidator.isValidDate(dateStr)) {
            return ParseResult.failure(MessageDisplayer.INVALID_DATE_DATA_MESSAGE);
        }

        LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        Expense newExpense = new Expense(description, amount, category, date);
        if(isRecurring) {
            RecurringExpenseManager.getInstance().add(newExpense);
        } else {
            RegularExpenseManager.getInstance().add(newExpense);
        }
        return ParseResult.success(null);
    }
}
