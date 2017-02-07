package unit.ui.controllers.devices_controller;


import javafx.scene.layout.GridPane;
import org.junit.After;
import org.junit.Test;
import ui.controllers.DevicesController;

import static org.junit.Assert.assertNotNull;

/**
 * contains unit tests for {@link DevicesController#displayNewDeviceForm()}
 */
public final class DisplayNewDeviceForm extends DevicesControllerTestCase {
    private static final String queryForCloseButton =
            "#device-panel-close-button";

    /**
     * Test that clicking the button for a new device will bring up the form
     */
    @Test
    public void newDeviceForm(){
        clickOn(newDeviceButton);
        assertNotNull(lookupNewDeviceForm());
    }

    /**
     * @return A representation of the form to register a new device
     */
    private GridPane lookupNewDeviceForm(){
        return lookup(queryForNewDeviceForm).query();
    }

    @After
    public void closeDialog(){
        clickOn(queryForCloseButton);
    }
}
