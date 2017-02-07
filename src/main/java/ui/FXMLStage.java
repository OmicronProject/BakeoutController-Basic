package ui;

import java.io.IOException;

/**
 * Describes the methods that a Stage must possess in order to be rendered
 * by the {@link UserInterfaceLauncher}
 */
public interface FXMLStage {
    /**
     * Create a scene, and load an FXML file into that scene
     *
     * @throws IOException If the FXML file cannot be found
     */
    void loadFXML() throws IOException;

    /**
     * Show the stage
     */
    void show();

    /**
     * @param loader The FXML loader to use in this stage
     */
    void setFXMLLoader(FXMLLoader loader);
}
