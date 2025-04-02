package fintrek.parser;

import fintrek.command.edit.EditExpenseDescriptor;
import fintrek.command.edit.EditParseResult;
import fintrek.misc.MessageDisplayer;
import fintrek.util.InputValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses user input for the /edit command into a structured EditParseResult.
 */
public class EditArgumentParser implements CommandParser<ParseResult<EditParseResult>> {

    private static final String INDEX_PATTERN = "(\\d+)";
    private static final String DESC_PATTERN = "(?:\\s+/d\\s+([^/$]+))?";
    private static final String AMOUNT_PATTERN = "(?:\\s+/\\$\\s+(\\S+))?";
    private static final String CATEGORY_PATTERN = "(?:\\s+/c\\s+(\\S+))?";
    private static final String DATE_PATTERN = "(?:\\s+/dt\\s+(\\S+))?";

    private static final Pattern EDIT_PATTERN = Pattern.compile(
            "^" + INDEX_PATTERN + DESC_PATTERN + AMOUNT_PATTERN + CATEGORY_PATTERN + DATE_PATTERN + "$"
    );


    private static final String FORMAT_HINT =
            "Invalid format. Usage: /edit [INDEX] [/d DESC] [/$ AMOUNT] [/c CATEGORY]";
    private static final String NO_FIELD_PROVIDED_MSG =
            "Please provide at least one field to edit using /d, /$ or /c.";

    @Override
    public ParseResult<EditParseResult> parse(String input) {
        if (InputValidator.isNullOrBlank(input)) {
            return ParseResult.failure(
                    String.format(MessageDisplayer.ARG_EMPTY_MESSAGE_TEMPLATE, "edit")
            );
        }

        Matcher matcher = EDIT_PATTERN.matcher(input.trim());
        if (!matcher.matches()) {
            return ParseResult.failure(FORMAT_HINT);
        }

        int zeroBaseIndex = Integer.parseInt(matcher.group(1)) - 1;
        EditExpenseDescriptor descriptor = extractDescriptor(matcher);

        if (descriptor == null) {
            return ParseResult.failure(MessageDisplayer.INVALID_AMT_MESSAGE);
        }

        if (!descriptor.hasAnyField()) {
            return ParseResult.failure(NO_FIELD_PROVIDED_MSG);
        }

        return ParseResult.success(new EditParseResult(zeroBaseIndex, descriptor));
    }

    private EditExpenseDescriptor extractDescriptor(Matcher matcher) {
        EditExpenseDescriptor descriptor = new EditExpenseDescriptor();

        String description = matcher.group(2);
        String amountStr = matcher.group(3);
        String category = matcher.group(4);
        String dateStr = matcher.group(5);

        if (description != null) {
            descriptor.setDescription(description);
        }
        if (amountStr != null) {
            if (!InputValidator.isValidAmountInput(amountStr)) {
                return null;
            }
            descriptor.setAmount(amountStr);
        }
        if (category != null) {
            descriptor.setCategory(category);
        }
        if (dateStr != null) {
            if (!InputValidator.isValidDate(dateStr)) {
                return null; // triggers parse failure
            }
            descriptor.setDate(dateStr); // safe to parse
        }
        return descriptor;
    }
}
