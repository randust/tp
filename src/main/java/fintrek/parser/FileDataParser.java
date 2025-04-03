package fintrek.parser;

import fintrek.expense.core.Expense;
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

    @Override
    public ParseResult<Void> parse(String fileData) {
        if (InputValidator.isNullOrBlank(fileData)) {
            return ParseResult.failure(MessageDisplayer.EMPTY_DATA_MESSAGE);
        }

        String[] tokens = fileData.trim().split("\\|", 4);
        if (tokens.length < 2) {
            return ParseResult.failure(MessageDisplayer.EMPTY_AMOUNT_DATA_MESSAGE);
        }
        if (tokens.length < 3) {
            return ParseResult.failure(MessageDisplayer.EMPTY_CATEGORY_DATA_MESSAGE);
        }
        if (tokens.length < 4) {
            return ParseResult.failure(MessageDisplayer.EMPTY_DATE_DATA_MESSAGE);
        }

        return processExpense(tokens);
    }

    private ParseResult<Void> processExpense(String[] tokens) {
        String description = tokens[0].trim();
        String amountStr = tokens[1].trim().substring(1);
        String category = tokens[2].trim();
        String dateStr = tokens[3].trim();

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
        RegularExpenseManager.getInstance().add(newExpense);
        return ParseResult.success(null);
    }
}
