package fintrek.command.sort;

/**
 * Represents the result of parsing a list-sort command.
 *
 * <p>This record bundles the sorting parameters, sortBy and sortDir.</p>
 *
 * <p>Used internally by {@link ListSortCommand} after parsing the user's input.</p>
 *
 * @param sortBy the field by which list will be sorted in
 * @param sortDir the direction in which sorted list is displayed
 */
public record ListSortParseResult(String sortBy, String sortDir) {
}
