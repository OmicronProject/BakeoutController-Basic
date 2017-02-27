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
 * This controller manages operation of the voltage set point algorithm
 */
@Controller
public class SequenceController {
    /**
     * The application log
     */
    private static final Logger log = LoggerFactory.getLogger
            (SequenceController.class);

    /**
     * The kernel to which this controller is attached
     */
    @Autowired
    private Kernel kernel;

    /**
     * The algorithm to use
     */
    @Autowired
    private VoltageSetPointAlgorithm algorithm;

    /**
     * The text field in which the desired voltage is reported
     */
    @FXML
    private TextField voltageTextField;

    /**
     * A slider where the user can interact with voltages
     */
    @FXML
    private Slider voltageSlider;

    /**
     * The button to click when the control algorithm is ready to be run
     */
    @FXML
    private Button goButton;

    /**
     * A text field where the user can enter the upper bound for the pressure
     */
    @FXML
    private TextField pressureTextField;

    /**
     * A slider that complements {@link SequenceController#pressureTextField}
     */
    @FXML
    private Slider pressureSlider;

    /**
     * Add listeners to the variable sliders, so that the value in the
     * {@link TextField} changes with a change in the slider position.
     */
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

    /**
     * Set the slider to the position that is in the text field
     */
    @FXML public void handleVoltageTextFieldChanged() {
        voltageSlider.setValue(parseTextFieldToDouble(voltageTextField));
    }

    /**
     * Set the position of the pressure slider to that in the text field
     */
    @FXML public void handlePressureTextFieldChanged(){
        pressureSlider.setValue(parseTextFieldToDouble(pressureTextField));
    }

    /**
     * Run the algorithm
     */
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

    /**
     * If the position of the slider changed, take the value in the slider
     * and write it to its corresponding text field
     */
    private void handleVoltageSliderChanged(){
        Double sliderValue = voltageSlider.getValue();
        DecimalFormat format = new DecimalFormat("#.00");
        voltageTextField.setText(format.format(sliderValue));
    }

    /**
     * If the position of the pressure slider changed, write the changes to
     * its corresponding text field
     */
    private void handlePressureSliderChanged(){
        Double sliderValue = pressureSlider.getValue();
        DecimalFormat format = new DecimalFormat("0.00E0");
        pressureTextField.setText(format.format(sliderValue));
    }

    /**
     * @param field The field from which a number needs to be retrieved
     * @return The number written in the text field
     */
    @NotNull
    private static Double parseTextFieldToDouble(TextField field){
        CharSequence seq = field.getCharacters();
        return Double.parseDouble(seq.toString());
    }

    /**
     * Gray out the go button
     */
    private void disableGoButton(){
        goButton.setDisable(Boolean.TRUE);
    }

    /**
     * Log the error out
     * @param error The error that was thrown while trying to run the
     *              control algorithm
     */
    private void handleException(Exception error){
        log.error("Trying to run algorithm threw error", error);
    }
}
