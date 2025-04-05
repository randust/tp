package fintrek.parser;

import fintrek.command.add.AddParseResult;
import fintrek.misc.MessageDisplayer;
import fintrek.util.InputValidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddArgumentParser implements CommandParser<ParseResult<AddParseResult>> {

    public static final String COMMAND_NAME = "add";
    private static final String DESC_PATTERN = "(.+?)\\s*";   // Description
    private static final String AMOUNT_PATTERN = "\\$\\s*(\\S+)";  // Amount
    private static final String CATEGORY_PATTERN = "(?:\\s*/c\\s*(\\S+))?"; // Category (optional)
    private static final String DATE_PATTERN = "(?:\\s*/dt\\s+(\\S+))?"; // Date (optional)

    private static final Pattern ADD_PATTERN = Pattern.compile(
            "^" + DESC_PATTERN + AMOUNT_PATTERN + CATEGORY_PATTERN + DATE_PATTERN + "$");

    @Override
    public ParseResult<AddParseResult> parse(String input) {
        if (InputValidator.isNullOrBlank(input)) {
            return ParseResult.failure(
                    String.format(MessageDisplayer.ARG_EMPTY_MESSAGE_TEMPLATE, COMMAND_NAME)
            );
        }

        Matcher m = ADD_PATTERN.matcher(input.trim());
        if (!m.matches()) {
            return ParseResult.failure(String.format(MessageDisplayer.INVALID_FORMAT_MESSAGE_TEMPLATE, COMMAND_NAME));
        }

        String description = m.group(1).trim();
        String amountStr = m.group(2);
        String category = (m.group(3) != null) ? m.group(3).trim() : "UNCATEGORIZED";
        String dateStr = m.group(4);

        if (!InputValidator.isValidAmountInput(amountStr)) {
            return ParseResult.failure(MessageDisplayer.INVALID_AMT_MESSAGE);
        }
        double amount = Double.parseDouble(amountStr);

        if (!InputValidator.isValidCategory(category)) {
            String message = String.format(MessageDisplayer.INVALID_CATEGORY_MESSAGE, category);
            return ParseResult.failure(message);
        }

        LocalDate date = LocalDate.now();
        if (dateStr != null) {
            if (!InputValidator.isValidDate(dateStr)) {
                return ParseResult.failure(MessageDisplayer.INVALID_DATE_MESSAGE);
            }
            date = extractDate(dateStr);
        }

        return ParseResult.success(new AddParseResult(description, amount, category, date));
    }

    /**
     * This functions verifies a date is in the format that we want and then converts it into LocalDate type.
     * @param dateStr the date in the form of String, which will be checked for the pattern
     *                and converted into LocalDate
     * @return a converted dateStr into a LocalDate variable following the format we want
     */
    //@@author edwardrl101
    public LocalDate extractDate(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return LocalDate.parse(dateStr, formatter);
    }
}
