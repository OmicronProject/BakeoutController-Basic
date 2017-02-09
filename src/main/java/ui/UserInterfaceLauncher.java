package ui;

import javafx.stage.Stage;

/**
 * Describes methods for a UI launcher
 */
public interface UserInterfaceLauncher {
    /**
     * Start the application with a given stage
     * @param stage
     * @throws Exception
     */
    void start(Stage stage) throws Exception;

    void launchWithDefaultStage(String[] commandLineArguments);
}
