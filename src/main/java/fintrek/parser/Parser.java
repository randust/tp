package fintrek.parser;

import fintrek.expense.core.Expense;
import fintrek.expense.core.RegularExpenseManager;
import fintrek.command.Command;
import fintrek.command.registry.CommandRegistry;
import fintrek.command.registry.CommandResult;
import fintrek.misc.MessageDisplayer;

import java.time.LocalDate;
import java.util.logging.Logger;

/**
 * The {@code Parser} class is responsible for interpreting user input and executing the corresponding commands.
 * It validates the input format, checks for known commands, and ensures that required arguments are provided.
 */
public class Parser {
    private static final Logger logger = Logger.getLogger(Parser.class.getName());

    /**
     * Parses the current line in the save file and adds it into the list of expenses.
     *
     * @param fileData The raw expense in the format of the save file.
     * @return A {@code ParseResult} object indicating whether the addition of the expense
     *     into the list was successful and containing an error message if applicable.
     */

    public static ParseResult parseFileData(String fileData) {
        assert fileData != null : MessageDisplayer.EMPTY_DATA_MESSAGE;
        String[] tokens = fileData.trim().split("\\|", 4); // [description, amount, category, date]
        String description = tokens[0];

        if(tokens.length < 2) {
            return ParseResult.failure(MessageDisplayer.EMPTY_AMOUNT_DATA_MESSAGE);
        }

        if(tokens.length < 3) {
            return ParseResult.failure(MessageDisplayer.EMPTY_AMOUNT_DATA_MESSAGE);
        }

        String amountStr = tokens[1].substring(2); // amount without the $
        double amount = Double.parseDouble(amountStr);
        assert amount > 0 : MessageDisplayer.INVALID_AMT_DATA_MESSAGE;

        String category = tokens[2];
        LocalDate date = LocalDate.parse(tokens[3].trim());
        Expense newExpense = new Expense(description, amount, category, date);
        RegularExpenseManager.getInstance().add(newExpense);
        return ParseResult.success(null);
    }
}
