package unit.ui.controllers.sequence_controller;

import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import org.junit.Test;
import ui.controllers.SequenceController;

import static org.junit.Assert.assertEquals;

/**
 * Contains unit tests of {@link SequenceController#handleVoltageSliderChanged()}
 */
public final class HandleVoltageTextFieldChanged extends SequenceControllerTestCase {
    private static final Double desiredVoltage = 10.0;

    @Test
    public void handleTextFieldChanged(){
        clickOn(queryForVoltageTextField);
        write(desiredVoltage.toString());
        press(KeyCode.ENTER);

        Slider voltageSlider = lookup(queryForVoltageSlider).query();

        assertEquals(
                desiredVoltage,
                voltageSlider.getValue(),
                1e-6
        );

    }
}
