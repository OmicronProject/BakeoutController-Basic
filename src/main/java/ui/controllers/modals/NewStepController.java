package ui.controllers.modals;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;
import ui.Controller;

/**
 * Contains the controller for the modal of the new step form
 */
@Controller
public class NewStepController {
    @FXML
    public void closeStage(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
