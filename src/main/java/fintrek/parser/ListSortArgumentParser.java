package fintrek.parser;

import fintrek.command.sort.ListSortParseResult;
import fintrek.misc.MessageDisplayer;
import fintrek.util.InputValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser for the arguments of the list-sort command
 *
 * <p>This class implements the {@link CommandParser} interface and parses user input
 * related to sorting the expense list. It expects exactly two words: the field to sort by
 * and the sort direction. If the input is blank or in an invalid format, a failure
 * {@link ParseResult} is returned with a descriptive error message.</p>
 *
 * <p>On successful parsing, a {@link ParseResult} containing a {@link ListSortParseResult}
 * is returned, which has the parsed sorting parameters.</p>
 */
public class ListSortArgumentParser implements CommandParser<ParseResult<ListSortParseResult>> {
    private static final String COMMAND_NAME = "sort";

    /**
     * Parses the input string for the list-sort command.
     *
     * Validates and extracts the sorting field and direction from the
     * user input. The expected format is: {@code <field> <direction>} (e.g., {@code amount asc}).
     *
     * @param input the raw user input string
     * @return a {@link ParseResult} containing a {@link ListSortParseResult} if successful,
     *         or a failure result with an error message if the input is invalid or blank, or if
     *         the input does not match the required input format.
     */
    @Override
    public ParseResult<ListSortParseResult> parse(String input) {
        if (InputValidator.isNullOrBlank(input)) {
            return ParseResult.failure(String.format(MessageDisplayer.ARG_EMPTY_MESSAGE_TEMPLATE, COMMAND_NAME)
            );
        }
        Pattern p = Pattern.compile("^\\s*(\\w+)\\s+(\\w+)\\s*$");
        Matcher m = p.matcher(input.trim().toUpperCase());

        if (!m.matches()) {
            return ParseResult.failure(String.format(MessageDisplayer.INVALID_FORMAT_MESSAGE_TEMPLATE, COMMAND_NAME));
        }
        String sortBy = m.group(1).trim();
        String sortDir = m.group(2).trim();

        return ParseResult.success(new ListSortParseResult(sortBy, sortDir));
    }
}
