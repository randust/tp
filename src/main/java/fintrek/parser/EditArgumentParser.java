package fintrek.parser;

import fintrek.command.edit.EditExpenseDescriptor;
import fintrek.command.edit.EditParseResult;
import fintrek.misc.MessageDisplayer;
import fintrek.util.InputValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses user input for the /edit command into a structured EditParseResult.
 * This parser supports editing any subset of the fields: description, amount,
 * category, and date of an existing expense entry, indexed by the user.
 */
public class EditArgumentParser implements CommandParser<ParseResult<EditParseResult>> {
    private static final double MAX_AMOUNT = 1_000_000_000;
    private static final double MIN_AMOUNT = 0;
    private static final String INDEX_PATTERN = "(\\d+)";
    private static final String DESC_PATTERN = "(?:\\s+/d\\s+([^/$]+))?";
    private static final String AMOUNT_PATTERN = "(?:\\s+/\\$\\s+(\\S+))?";
    private static final String CATEGORY_PATTERN = "(?:\\s+/c\\s+(\\S+))?";
    private static final String DATE_PATTERN = "(?:\\s+/dt\\s+(\\S+))?";

    private static final Pattern EDIT_PATTERN = Pattern.compile(
            "^" + INDEX_PATTERN + DESC_PATTERN + AMOUNT_PATTERN + CATEGORY_PATTERN + DATE_PATTERN + "$"
    );

    /**
     * Parses a raw input string for the /edit command.
     * Extracts the index and fields to edit, validates them,
     * and returns a structured EditParseResult.
     *
     * @param input the raw input string for the edit command
     * @return a successful ParseResult containing EditParseResult if valid,
     *         or a failed ParseResult with an error message if invalid
     */
    @Override
    public ParseResult<EditParseResult> parse(String input) {
        if (InputValidator.isNullOrBlank(input)) {
            return ParseResult.failure(
                    String.format(MessageDisplayer.ARG_EMPTY_MESSAGE_TEMPLATE, "edit")
            );
        }

        Matcher matcher = EDIT_PATTERN.matcher(input.trim());
        if (!matcher.matches()) {
            return ParseResult.failure(MessageDisplayer.EDIT_FORMAT_HINT);
        }

        int zeroBaseIndex = Integer.parseInt(matcher.group(1)) - 1;

        ParseResult<EditExpenseDescriptor> descriptorResult = extractDescriptor(matcher);
        if (!descriptorResult.isSuccess()) {
            return ParseResult.failure(descriptorResult.getError());
        }

        EditExpenseDescriptor descriptor = descriptorResult.getResult();
        if (!descriptor.hasAnyField()) {
            return ParseResult.failure(MessageDisplayer.EDIT_NO_FIELD_PROVIDED_MSG);
        }

        return ParseResult.success(new EditParseResult(zeroBaseIndex, descriptor));
    }

    /**
     * Extracts and validates each editable field (description, amount,
     * category, date) from the regex matcher.
     *
     * @param matcher the regex matcher with parsed groups
     * @return a successful ParseResult containing EditExpenseDescriptor if valid,
     *         or a failed ParseResult with an error message if any field is invalid
     */
    private ParseResult<EditExpenseDescriptor> extractDescriptor(Matcher matcher) {
        EditExpenseDescriptor descriptor = new EditExpenseDescriptor();

        ParseResult<?> result;

        result = trySetDescription(descriptor, matcher.group(2));
        if (!result.isSuccess()) {
            return ParseResult.failure(result.getError());
        }

        result = trySetAmount(descriptor, matcher.group(3));
        if (!result.isSuccess()) {
            return ParseResult.failure(result.getError());
        }

        result = trySetCategory(descriptor, matcher.group(4));
        if (!result.isSuccess()) {
            return ParseResult.failure(result.getError());
        }

        result = trySetDate(descriptor, matcher.group(5));
        if (!result.isSuccess()) {
            return ParseResult.failure(result.getError());
        }

        return ParseResult.success(descriptor);
    }

    /**
     * Attempts to validate and set the description field.
     *
     * @param descriptor the descriptor to populate
     * @param description the raw description string
     * @return a successful ParseResult if valid, else failure with message
     */
    private ParseResult<Void> trySetDescription(EditExpenseDescriptor descriptor, String description) {
        if (description == null) {
            return ParseResult.success(null);
        }
        if (!InputValidator.isValidStringLength(description)) {
            String msg = String.format(MessageDisplayer.STRING_OUT_OF_RANGE_FORMAT_MESSAGE, "Description");
            return ParseResult.failure(msg);
        }
        descriptor.setDescription(description);
        return ParseResult.success(null);
    }

    /**
     * Attempts to validate and set the amount field.
     *
     * @param descriptor the descriptor to populate
     * @param amountStr the raw amount string
     * @return a successful ParseResult if valid, else failure with message
     */
    private ParseResult<Void> trySetAmount(EditExpenseDescriptor descriptor, String amountStr) {
        if (amountStr == null) {
            return ParseResult.success(null);
        }
        if (!InputValidator.isValidAmountInput(amountStr)) {
            return ParseResult.failure(MessageDisplayer.INVALID_AMT_MESSAGE);
        }
        descriptor.setAmount(amountStr);
        if (!InputValidator.isInValidDoubleRange(descriptor.getAmount(), MIN_AMOUNT, MAX_AMOUNT)) {
            return ParseResult.failure(MessageDisplayer.INVALID_AMT_MESSAGE);
        }
        return ParseResult.success(null);
    }

    /**
     * Attempts to validate and set the category field.
     *
     * @param descriptor the descriptor to populate
     * @param category the raw category string
     * @return a successful ParseResult if valid, else failure with message
     */
    private ParseResult<Void> trySetCategory(EditExpenseDescriptor descriptor, String category) {
        if (category == null) {
            return ParseResult.success(null);
        }
        if (!InputValidator.isValidStringLength(category)) {
            String msg = String.format(MessageDisplayer.STRING_OUT_OF_RANGE_FORMAT_MESSAGE, "Category");
            return ParseResult.failure(msg);
        }
        if (!InputValidator.isValidCategory(category)) {
            String msg = String.format(MessageDisplayer.INVALID_CATEGORY_MESSAGE, category);
            return ParseResult.failure(msg);
        }
        descriptor.setCategory(category);
        return ParseResult.success(null);
    }

    /**
     * Attempts to validate and set the date field.
     *
     * @param descriptor the descriptor to populate
     * @param dateStr the raw date string
     * @return a successful ParseResult if valid, else failure with message
     */
    private ParseResult<Void> trySetDate(EditExpenseDescriptor descriptor, String dateStr) {
        if (dateStr == null) {
            return ParseResult.success(null);
        }
        if (!InputValidator.isValidDate(dateStr)) {
            return ParseResult.failure(MessageDisplayer.INVALID_DATE_MESSAGE);
        }
        descriptor.setDate(dateStr);
        return ParseResult.success(null);
    }
}
