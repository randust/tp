//@@author szeyingg
package fintrek.parser;

import fintrek.command.add.AddParseResult;
import fintrek.command.sort.ListSortParseResult;
import fintrek.misc.MessageDisplayer;
import fintrek.util.InputValidator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Parser for the arguments of the list-sort command
 *
 * <p>This class implements the {@link CommandParser} interface and parses user input
 * related to adding a new expense. It expects exactly two mandatory arguments: the
 * expense description and the expense amount. If there are any errors with the input, a
 * {@link ParseResult} is returned with a descriptive error message.</p>
 *
 * <p>Upon successful parsing, a {@link ParseResult} containing a {@link AddParseResult}
 * is returned, which has the parsed sorting parameters.</p>
 */
public class AddArgumentParser implements CommandParser<ParseResult<AddParseResult>> {

    /** Pre-defined constants to avoid magic literals */
    public static final String COMMAND_NAME = "add";
    private static final double MIN_AMOUNT = 0;
    // maximum amount for the expense (one billion).
    private static final double MAX_AMOUNT = 1_000_000_000;
    private static final String DESC_PATTERN = "(.+?)\\s*";   // Description
    private static final String AMOUNT_PATTERN = "\\$\\s*(\\S+)";  // Amount
    private static final String CATEGORY_PATTERN = "(?:\\s*/c\\s*(\\S+))?"; // Category (optional)
    private static final String DATE_PATTERN = "(?:\\s*/dt\\s+(\\S+))?"; // Date (optional)

    private static final Pattern ADD_PATTERN = Pattern.compile(
            "^" + DESC_PATTERN + AMOUNT_PATTERN + CATEGORY_PATTERN + DATE_PATTERN + "$");


    /**
     * Parses the input string for the add command.
     *
     * Validates and extracts the description, amount, optional category
     * and optional date from the user input. The expected format is:
     * {@code <description> $<amount> [/c <category>] [/dt <date>]}</p>
     *
     * An error message is returned if the input is blank, does not match the
     * required input format, has an invalid amount, has an invalid date format,
     * tries to add an already-defined category, or if the expense description
     * exceeds 100 characters.
     *
     * @param input the raw user input string
     * @return a {@link ParseResult} containing an {@link AddParseResult} on success,
     *         or a failure result with an appropriate error message if the input is invalid.
     */
    @Override
    public ParseResult<AddParseResult> parse(String input) {
        if (InputValidator.isNullOrBlank(input)) {
            return ParseResult.failure(
                    String.format(MessageDisplayer.ARG_EMPTY_MESSAGE_TEMPLATE, COMMAND_NAME)
            );
        }

        Matcher m = ADD_PATTERN.matcher(input.trim());
        if (!m.matches()) {
            String message = String.format(MessageDisplayer.INVALID_FORMAT_MESSAGE_TEMPLATE, COMMAND_NAME);
            return ParseResult.failure(message);
        }

        String description = m.group(1);
        String amountStr = m.group(2);
        String category = (m.group(3) != null) ? m.group(3).trim() : "UNCATEGORIZED";
        String dateStr = m.group(4);

        if (!InputValidator.isValidStringLength(description)) {
            String message = String.format(MessageDisplayer.STRING_OUT_OF_RANGE_FORMAT_MESSAGE, "Description");
            return ParseResult.failure(message);
        }
        if (description.contains("$") || description.contains(" /c ") || description.contains(" /dt ")) {
            return ParseResult.failure(MessageDisplayer.RESERVED_DESC_ERROR);
        }

        if (!InputValidator.isValidAmountInput(amountStr)) {
            return ParseResult.failure(MessageDisplayer.INVALID_AMT_MESSAGE);
        }
        double amount = Double.parseDouble(amountStr);

        if (!InputValidator.isInValidDoubleRange(amount, MIN_AMOUNT, MAX_AMOUNT)) {
            return ParseResult.failure(MessageDisplayer.INVALID_AMT_MESSAGE);
        }
        if (!InputValidator.isValidStringLength(category)) {
            String message = String.format(MessageDisplayer.STRING_OUT_OF_RANGE_FORMAT_MESSAGE, "Category");
            return ParseResult.failure(message);
        }
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
     * Extracts the date in the form of LocalDate out of a given String object
     * of the form "dd-MM-yyyy".
     * @param dateStr the date in the form of String, which will be checked for the pattern
     *                and converted into LocalDate
     * @return a converted dateStr into a LocalDate variable following the required format
     */
    //@@author edwardrl101
    public LocalDate extractDate(String dateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        return LocalDate.parse(dateStr, formatter);
    }

    /**
     * Generates an error message if the expense description exceeds the
     * allowed character limit.
     * @param input invalid input containing description that is too lengthy.
     * @return an appropriate error message displaying the character limit.
     */
    public String getInvalidStringLengthMessage(String input) {
        String messageArg = input.substring(0, 1).toUpperCase() + input.substring(1);
        return String.format(MessageDisplayer.STRING_OUT_OF_RANGE_FORMAT_MESSAGE, messageArg);
    }
}
