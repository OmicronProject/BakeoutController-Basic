package main;

import javafx.application.Application;
import ui.UserInterfaceLauncher;

/**
 * The main runner for the application
 */
public class Main {
    /**
     * Launch the UI
     * @param args The launch arguments
     * @throws Exception if an exception is thrown
     */
    public static void main(String[] args) throws Exception {
        Application.launch(UserInterfaceLauncher.class, args);
    }
}
