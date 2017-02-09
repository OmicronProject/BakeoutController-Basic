package main;

import javafx.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ui.UserInterfaceLauncher;

/**
 * The main runner for the application
 */
public class Main {
    private static Logger logger = LoggerFactory.getLogger(Main.class);
    /**
     * Launch the UI
     * @param args The launch arguments
     * @throws Exception if an exception is thrown
     */
    public static void main(String[] args) throws Exception {
        logger.info("Started Logger");
        Application.launch(UserInterfaceLauncher.class, args);
    }
}
