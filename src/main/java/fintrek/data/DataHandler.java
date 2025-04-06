package fintrek.data;
import fintrek.budget.core.BudgetManager;
import fintrek.expense.core.RecurringExpenseManager;
import fintrek.misc.MessageDisplayer;
import fintrek.expense.core.Expense;
import fintrek.parser.ParseResult;
import fintrek.parser.FileDataParser;
import fintrek.expense.core.RegularExpenseManager;

import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The {@code DataHandler} class is responsible for ensuring that the user's expenses
 * are saved and loaded correctly.
 * It creates a new save file if none is found, loads the expenses from the save file
 * and saves the expenses properly in the save file.
 */
public class DataHandler {
    private static final Logger logger = Logger.getLogger(DataHandler.class.getName());
    private static final String FILE_PATH = "data.txt";
    private static final String RECURRING_EXPENSE_SEPARATOR = " | R";


    /**
     * Saves each expense in data.txt in the following format:
     * "DESCRIPTION | $AMOUNT | CATEGORY | DATE"
     * Prints our an error message if there is an error saving data
      */
    public static void saveData() {
        try {
            FileWriter fw = new FileWriter(FILE_PATH);
            if(BudgetManager.getInstance().isBudgetSet()) {
                fw.write(BudgetManager.getInstance() + MessageDisplayer.LINE_SEPARATOR);
            }

            for(int i = 0; i < RegularExpenseManager.getInstance().getLength(); i++) {
                Expense expense = RegularExpenseManager.getInstance().get(i);;
                fw.write(expense.toString() + MessageDisplayer.LINE_SEPARATOR);
            }

            for(int i = 0; i < RecurringExpenseManager.getInstance().getLength(); i++) {
                Expense expense = RecurringExpenseManager.getInstance().get(i);;
                fw.write(expense.toString() + RECURRING_EXPENSE_SEPARATOR +
                        MessageDisplayer.LINE_SEPARATOR);
            }
            fw.close();
        } catch(IOException e) {
            System.out.println(MessageDisplayer.ERROR_SAVING_DATA_MESSAGE + e.getMessage());
        }
    }

    /**
     * Loads the current list of expenses upon startup
     * Creates a new save file if the desired 'data.txt' file is not found
     * Prints an error message if there is a problem while loading data
     */
    public static void loadData() {
        logger.log(Level.FINE, "Loading data...");
        File f = new File(FILE_PATH);
        if(f.exists() && !f.isDirectory()) {
            try(Scanner s = new Scanner(f)) {
                while(s.hasNext()) {
                    String currExpense = s.nextLine();
                    ParseResult result = FileDataParser.parseFileData(currExpense);
                    printPotentialErrorMessage(result);
                }
            } catch (IOException e) {
                System.out.println(String.format(MessageDisplayer.FILE_LOAD_ERROR_MESSAGE, FILE_PATH) +
                        e.getMessage());
            }
        } else {
            createNewSaveFile();
        }
    }

    /**
     * Creates a new 'data.txt' save file if none is found
     * Prints an error message if problems are encountered while creating the file
     */
    public static void createNewSaveFile() {
        try {
            logger.log(Level.FINE, "Creating new save file...");
            File f = new File(FILE_PATH);
            f.createNewFile();
        } catch(IOException e) {
            System.out.println(String.format(MessageDisplayer.FILE_CREATION_ERROR_MESSAGE, FILE_PATH) +
                    e.getMessage());
        }
    }

    public static void printPotentialErrorMessage(ParseResult result) {
        if(!result.isSuccess()) {
            System.out.println(result.getError());
        }
    }

}
