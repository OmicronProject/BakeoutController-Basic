package ui.controllers;

import javafx.fxml.FXML;
import javafx.stage.Window;
import org.springframework.beans.factory.annotation.Autowired;
import ui.AutowiredFXMLStage;
import ui.Controller;
import ui.FXMLLoader;
import ui.FXMLStage;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;

/**
 * Controller for devices.fxml
 */
@Controller
public class DevicesController {
    /**
     * Represents the stage in which the new device form will be loaded
     */
    private FXMLStage newDeviceFormStage;

    /**
     * The location of the markup for the new device form
     */
    private final URL newDeviceFormLocation = getClass().getResource(
            "/modals/NewDevice.fxml"
    );

    /**
     * The current stage in which the root application window is loaded
     */
    @Autowired
    private FXMLStage applicationStage;

    /**
     * The context-aware FXML loader capable of loading required controller
     * beans
     */
    @Autowired
    private FXMLLoader fxmlLoader;

    /**
     * Set up the new device stage after construction
     * @throws IOException if the new device form cannot be found
     */
    @PostConstruct
    public void setUp() throws IOException {
        configureNewDeviceFormStage();
    }

    /**
     * Show the new device form
     */
    @FXML public void displayNewDeviceForm(){
        newDeviceFormStage.show();
    }

    /**
     * Set up an FXML stage, and load the markup for the new device form
     *
     * @throws IOException if the form cannot be loaded
     */
    private void configureNewDeviceFormStage() throws IOException {
        newDeviceFormStage = new AutowiredFXMLStage(
                newDeviceFormLocation, castStageToWindow(applicationStage)
        );
        newDeviceFormStage.setFXMLLoader(fxmlLoader);
        newDeviceFormStage.loadFXML();
    }

    /**
     * @param stage The stage to cast
     * @return The stage cast to a {@link Window}
     */
    private static Window castStageToWindow(FXMLStage stage){
        return (Window) stage;
    }
}
