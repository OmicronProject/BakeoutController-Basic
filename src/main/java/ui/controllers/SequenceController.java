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
 * Controls routing and dialogues for the sequence tab
 */
@Controller
public class SequenceController {
    /**
     * The location of the markup for the new step form
     */
    private final URL newStepFormLocation = getClass().getResource(
            "/modals/NewStep.fxml"
    );

    /**
     * The stage for the new form
     */
    private FXMLStage newStepFormStage;

    /**
     * The root application stage
     */
    @Autowired
    private FXMLStage applicationStage;

    /**
     * The FXML Loader to use for loading the markup in the new step form
     */
    @Autowired
    private FXMLLoader fxmlLoader;

    @PostConstruct
    public void setUp() throws IOException {
        configureNewStepFormStage();
    }

    @FXML public void displayNewStepForm(){
        newStepFormStage.show();
    }

    private void configureNewStepFormStage() throws IOException {
        newStepFormStage = new AutowiredFXMLStage(
            newStepFormLocation, castStageToWindow(applicationStage)
        );
        newStepFormStage.setFXMLLoader(fxmlLoader);
        newStepFormStage.loadFXML();
    }

    private static Window castStageToWindow(FXMLStage stage){
        return (Window) stage;
    }
}
