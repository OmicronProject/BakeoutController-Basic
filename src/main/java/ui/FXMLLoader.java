package ui;

import java.io.IOException;
import java.net.URL;

/**
 * Describes a wrapper for components capable of loading JavaFX FXML files
 */
public interface FXMLLoader {
    /**
     * @param fxml A URL to the FXML document that needs to be loaded
     */
    void setLocation(URL fxml);

    /**
     * Load the FXML document into JavaFX
     * @param <FXMLType> The resulting type of the top-level element loaded
     *                  by this loader
     * @return The loaded document
     * @throws IOException If the document cannot be found
     */
    <FXMLType>FXMLType load() throws IOException;

    /**
     * @param root The root element into which FXML is to be loaded
     */
    void setRoot(Object root);
}
