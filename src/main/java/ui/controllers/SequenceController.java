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

import javax.annotation.PostConstruct;

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
                    public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                        handleSliderChanged();
                    }
                }
        );
    }

    @FXML public void handleTextFieldChanged() {
        voltageSlider.setValue(parseTextFieldToDouble());
    }

    public void handleSliderChanged(){
        Double sliderValue = voltageSlider.getValue();

        voltageTextField.setText(sliderValue.toString());
    }

    @NotNull
    private Double parseTextFieldToDouble(){
        CharSequence seq = voltageTextField.getCharacters();
        return Double.parseDouble(seq.toString());
    }
}
