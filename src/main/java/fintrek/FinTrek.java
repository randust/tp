package fintrek;

import fintrek.ui.FinTrekUi;
import java.util.logging.Logger;

/**
 * Main entry-point for the FinTrek application.
 * Initializes the application and starts the UI.
 */
public class FinTrek {
    private static final Logger logger = Logger.getLogger(FinTrek.class.getName());

    public static void main(String[] args) {
        logger.info("FinTrek application started.");
        FinTrekUi ui = new FinTrekUi();
        ui.start();

        logger.info("FinTrek application shutting down.");
        ui.close();
    }
}
