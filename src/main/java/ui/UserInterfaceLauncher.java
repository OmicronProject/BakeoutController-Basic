package ui;

import javafx.stage.Stage;

/**
 * Describes methods for a UI launcher
 */
public interface UserInterfaceLauncher {
    /**
     * Start the application with a given stage
     * @param stage The stage in which the UI is to be loaded
     * @throws Exception If the UI cannot be started
     */
    void start(Stage stage) throws Exception;

    /**
     * Launch the UI and create a new stage
     * @param commandLineArguments The command line arguments with which the
     *                             application
     *                             ({@link main.Main#main(String[])}) was
     *                             called
     */
    void launchWithDefaultStage(String[] commandLineArguments);
}
