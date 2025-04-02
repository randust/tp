package fintrek.parser;

import fintrek.expense.core.Expense;
import fintrek.expense.core.RegularExpenseManager;
import fintrek.misc.MessageDisplayer;
import fintrek.util.InputValidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

/**
 * Parses saved expense file data into an Expense object.
 */
public class FileDataParser {

    private static final Pattern AMOUNT_PATTERN = Pattern.compile("\\$(\\d+(?:\\.\\d{1,2})?)");
    private static final String FORMAT_HINT = "Invalid expense format in save file.";

    public static ParseResult<Void> parseFileData(String fileData) {
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
        if(tokens.length < 4) {
            return ParseResult.failure(MessageDisplayer.EMPTY_DATE_DATA_MESSAGE);
        }

        return processExpense(tokens);
    }

    private static ParseResult<Void> processExpense(String[] tokens) {
        String description = tokens[0].trim();
        String amountStr = (tokens[1].trim()).substring(1);
        String category = tokens[2].trim();
        String dateStr = tokens[3].trim();

        if(InputValidator.isNullOrBlank(description)) {
            return ParseResult.failure(MessageDisplayer.EMPTY_DESC_DATA_MESSAGE);
        }

        if(!InputValidator.isValidPositiveDouble(amountStr)) {
            return ParseResult.failure(MessageDisplayer.INVALID_AMT_DATA_MESSAGE);
        }

        double amount = Double.parseDouble(amountStr);

        if(!InputValidator.isValidDate(dateStr)) {
            return ParseResult.failure(MessageDisplayer.INVALID_DATE_DATA_MESSAGE);
        }

        LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        Expense newExpense = new Expense(description, amount, category, date);
        RegularExpenseManager.getInstance().add(newExpense);
        return ParseResult.success(null);
    }

}
