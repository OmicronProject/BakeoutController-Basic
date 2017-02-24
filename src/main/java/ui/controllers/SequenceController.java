package ui.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import kernel.Kernel;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import ui.Controller;

/**
 * Controls routing and dialogues for the sequence tab
 */
@Controller
public class SequenceController {
    @Autowired
    private Kernel kernel;

    @FXML
    private TextField voltageTextField;

    @FXML
    private Slider voltageSlider;

    @FXML
    public void initialize(){
        voltageSlider.valueProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(
                            ObservableValue<? extends Number> observableValue,
                            Number number, Number t1
                    ) {
                        handleSliderChanged();
                    }
                }
        );
    }

    @FXML public void handleTextFieldChanged() {
        voltageSlider.setValue(parseTextFieldToDouble());
    }

    @FXML public void handleSliderChanged(){
        Double sliderValue = voltageSlider.getValue();

        voltageTextField.setText(sliderValue.toString());
    }

    @FXML public void handleGoButtonClicked(){

    }

    @NotNull
    private Double parseTextFieldToDouble(){
        CharSequence seq = voltageTextField.getCharacters();
        return Double.parseDouble(seq.toString());
    }
}
