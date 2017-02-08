package unit.ui.controllers.device_setup_controller;

import javafx.scene.control.ComboBox;
import org.junit.Before;
import org.junit.Test;
import ui.controllers.DeviceSetupController;

import static org.junit.Assert.assertEquals;

/**
 * Contains unit tests for {@link DeviceSetupController#initialize()}
 */
public final class Initialize extends DeviceSetupControllerTestCase {
    private static final String expectedPortName = "/dev/ttyUSB0";

    private ComboBox<String> portSelector;

    @Before
    public void getPortSelector(){
        portSelector = lookup(queryForPortSelector).query();
    }

    @Test
    public void initialize(){
        String portName = portSelector.getSelectionModel().getSelectedItem();
        assertEquals(expectedPortName, portName);
    }
}
