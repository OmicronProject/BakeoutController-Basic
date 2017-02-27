package unit.ui.controllers.sequence_controller;

import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by mkononen on 27/02/17.
 */
public final class HandlePressureSliderChanged extends
        SequenceControllerTestCase {
    private static final Double desiredPressure = 1e-8;

    @Test
    public void handleSliderChanged(){
        Slider pressureSlider = lookup(queryForPressureSlider).query();
        pressureSlider.setValue(desiredPressure);

        TextField pressureTextField = lookup(queryForPressureTextField)
                .query();

        assertEquals(
                desiredPressure,
                Double.parseDouble(pressureTextField.getText()),
                1e-2
        );
    }
}
