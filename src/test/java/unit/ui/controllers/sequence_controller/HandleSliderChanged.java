package unit.ui.controllers.sequence_controller;

import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import org.junit.Test;
import ui.controllers.SequenceController;

import static org.junit.Assert.assertEquals;

/**
 * Contains unit tests for {@link SequenceController#handleSliderChanged()}
 */
public final class HandleSliderChanged extends SequenceControllerTestCase {
    private static final Double desiredVoltage = 10.0;

    @Test
    public void handleSliderChanged(){
        Slider voltageSlider = lookup(queryForVoltageSlider).query();
        voltageSlider.setValue(desiredVoltage);

        TextField voltageTextField = lookup(queryForVoltageTextField).query();

        assertEquals(
                desiredVoltage,
                Double.parseDouble(voltageTextField.getText()),
                1e-6
        );
    }
}
