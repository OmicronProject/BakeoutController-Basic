package ui.controllers;

import exceptions.UnableToRunControlAlgorithmException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import kernel.Kernel;
import kernel.controllers.VoltageSetPointAlgorithm;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ui.Controller;

/**
 * Controls routing and dialogues for the sequence tab
 */
@Controller
public class SequenceController {
    private static final Logger log = LoggerFactory.getLogger
            (SequenceController.class);

    @Autowired
    private Kernel kernel;

    @FXML
    private TextField voltageTextField;

    @FXML
    private Slider voltageSlider;

    @FXML
    private Button goButton;

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
        VoltageSetPointAlgorithm algorithm = new kernel.models
                .VoltageSetPointAlgorithm();

        algorithm.setKernel(kernel);
        algorithm.setDesiredVoltage(voltageSlider.getValue());
        algorithm.setMaximumIterations(100);
        algorithm.setPressureUpperBound(1e-9f);

        try {
            algorithm.run();
            disableGoButton();
        } catch (UnableToRunControlAlgorithmException error){
            handleException(error);
        }


    }

    @NotNull
    private Double parseTextFieldToDouble(){
        CharSequence seq = voltageTextField.getCharacters();
        return Double.parseDouble(seq.toString());
    }

    private void disableGoButton(){
        goButton.setDisable(Boolean.TRUE);
    }

    private void handleException(Exception error){
        log.error("Trying to run algorithm threw error", error);
    }
}
