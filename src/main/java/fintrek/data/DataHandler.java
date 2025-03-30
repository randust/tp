package fintrek.data;
import fintrek.misc.MessageDisplayer;
import fintrek.Expense;
import fintrek.parser.ParseResult;
import fintrek.parser.Parser;
import fintrek.ExpenseManager;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
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

    public static void saveData() {
        try {
            FileWriter fw = new FileWriter(FILE_PATH);
            for(int i = 0; i < ExpenseManager.getLength(); i++) {
                Expense expense = ExpenseManager.getExpense(i);;
                fw.write(expense.toString() + "\n");
            }
            fw.close();
        } catch(IOException e) {
            System.out.println(MessageDisplayer.ERROR_SAVING_DATA_MESSAGE + e.getMessage());
        }
    }

    public static void loadData() {
        logger.info("Loading data...");
        File f = new File(FILE_PATH);
        if(f.exists() && !f.isDirectory()) {
            try(Scanner s = new Scanner(f)) {
                while(s.hasNext()) {
                    String currExpense = s.nextLine();
                    ParseResult result = Parser.parseFileData(currExpense);
                }
            } catch (IOException e) {
                System.out.println(String.format(MessageDisplayer.FILE_LOAD_ERROR_MESSAGE, FILE_PATH) +
                        e.getMessage());
            }
        } else {
            createNewSaveFile();
        }
    }

    public static void createNewSaveFile() {
        try {
            logger.info("Creating new save file...");
            File f = new File(FILE_PATH);
            f.createNewFile();
        } catch(IOException e) {
            System.out.println(String.format(MessageDisplayer.FILE_CREATION_ERROR_MESSAGE, FILE_PATH) +
                    e.getMessage());
        }
    }

}
