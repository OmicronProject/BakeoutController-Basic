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

import java.text.DecimalFormat;

/**
 * Controls routing and dialogues for the sequence tab
 */
@Controller
public class SequenceController {
    private static final Logger log = LoggerFactory.getLogger
            (SequenceController.class);

    @Autowired
    private Kernel kernel;

    @Autowired
    private VoltageSetPointAlgorithm algorithm;

    @FXML
    private TextField voltageTextField;

    @FXML
    private Slider voltageSlider;

    @FXML
    private Button goButton;

    @FXML
    private TextField pressureTextField;

    @FXML
    private Slider pressureSlider;

    @FXML
    public void initialize(){
        voltageSlider.valueProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(
                            ObservableValue<? extends Number> observableValue,
                            Number number, Number t1
                    ) {
                        handleVoltageSliderChanged();
                    }
                }
        );
        pressureSlider.valueProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(
                            ObservableValue<? extends Number> observableValue,
                            Number number, Number t1) {
                        handlePressureSliderChanged();
                    }
                }
        );
    }

    @FXML public void handleVoltageTextFieldChanged() {
        voltageSlider.setValue(parseTextFieldToDouble());
    }

    @FXML public void handlePressureTextFieldChanged(){
        pressureSlider.setValue(parsePressureTextFieldToDouble());
    }

    @FXML public void handleGoButtonClicked(){
        algorithm.setKernel(kernel);
        algorithm.setDesiredVoltage(voltageSlider.getValue());
        algorithm.setMaximumIterations(100);
        algorithm.setPressureUpperBound(new Float(pressureSlider.getValue()));

        try {
            algorithm.run();
            disableGoButton();
        } catch (UnableToRunControlAlgorithmException error){
            handleException(error);
        }
    }

    private void handleVoltageSliderChanged(){
        Double sliderValue = voltageSlider.getValue();
        DecimalFormat format = new DecimalFormat("#.00");
        voltageTextField.setText(format.format(sliderValue));
    }

    private void handlePressureSliderChanged(){
        Double sliderValue = pressureSlider.getValue();
        DecimalFormat format = new DecimalFormat("0.00E0");
        pressureTextField.setText(format.format(sliderValue));
    }

    @NotNull
    private Double parseTextFieldToDouble(){
        CharSequence seq = voltageTextField.getCharacters();
        return Double.parseDouble(seq.toString());
    }

    @NotNull
    private Double parsePressureTextFieldToDouble(){
        CharSequence sequence = pressureTextField.getCharacters();
        return Double.parseDouble(sequence.toString());
    }

    private void disableGoButton(){
        goButton.setDisable(Boolean.TRUE);
    }

    private void handleException(Exception error){
        log.error("Trying to run algorithm threw error", error);
    }
}
