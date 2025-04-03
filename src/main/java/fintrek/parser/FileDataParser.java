package fintrek.parser;

import fintrek.expense.core.BudgetManager;
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

    public static ParseResult<Void> parseFileData(String fileData) {
        return INSTANCE.parse(fileData); // delegates to the instance method
    }

    public ParseResult<Void> parseBudgetFromLine(String line) {
        String budgetStr = line.substring("Monthly Budget: $".length()).trim();
        if(!InputValidator.isValidPositiveDouble(budgetStr)) {
            return ParseResult.failure(MessageDisplayer.INVALID_LOAD_BUDGET_AMOUNT_MESSAGE);
        }
        double budget = Double.parseDouble(budgetStr);
        BudgetManager.getInstance().setBudget(budget);
        return ParseResult.success(null);
    }

    public Boolean isOfBudgetFormat(String line) {
        return line.startsWith("Monthly Budget: $");
    }

    @Override
    public ParseResult<Void> parse(String fileData) {
        if (InputValidator.isNullOrBlank(fileData)) {
            return ParseResult.failure(MessageDisplayer.EMPTY_DATA_MESSAGE);
        }
        if(isOfBudgetFormat(fileData)) {
            return parseBudgetFromLine(fileData);
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
