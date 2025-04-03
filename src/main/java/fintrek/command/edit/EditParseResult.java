package fintrek.command.edit;

/**
 * Represents the result of parsing an edit command.
 *
 * <p>This record bundles the zero-based index of the expense to edit
 * along with a descriptor containing any updated fields.</p>
 *
 * <p>Used internally by {@link EditCommand} after parsing the user's input.</p>
 *
 * @param zeroBaseIndex the 0-based index of the expense in the list
 * @param descriptor an {@link EditExpenseDescriptor} containing the updated fields
 */
public record EditParseResult(int zeroBaseIndex, EditExpenseDescriptor descriptor) {
}
