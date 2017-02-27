package unit.ui.controllers.sequence_controller;

import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import org.junit.Test;
import ui.controllers.SequenceController;

import static org.junit.Assert.assertEquals;

/**
 * Contains unit tests for
 * {@link SequenceController#handlePressureTextFieldChanged()}
 */
public final class HandlePressureTextFieldChanged extends
        SequenceControllerTestCase {
    private static final Double desiredPressure = 1e-8;

    @Test
    public void handlePressureTextFieldChanged(){
        clickOn(queryForPressureTextField);
        write(desiredPressure.toString());
        press(KeyCode.ENTER);

        Slider pressureSlider = lookup(queryForPressureSlider).query();

        assertEquals(
                desiredPressure,
                pressureSlider.getValue(),
                1e-3
        );
    }
}
