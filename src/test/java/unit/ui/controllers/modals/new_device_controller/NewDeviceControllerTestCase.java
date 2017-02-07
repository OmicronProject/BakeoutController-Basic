package unit.ui.controllers.modals.new_device_controller;

import org.junit.After;
import org.junit.Before;
import unit.ui.controllers.modals.ModalsTestCase;

/**
 * Base class for unit tests for
 * {@link ui.controllers.modals.NewDeviceController}
 */
public abstract class NewDeviceControllerTestCase extends ModalsTestCase {
    protected final String queryForDevicesTab = "#devices-tab";
    protected final String queryForNewDeviceButton = "#newDeviceButton";
    protected final String queryForCloseButton = "#device-panel-close-button";

    /**
     * Navigate to the window for new devices. This panel is what the
     * controller controls
     */
    @Before
    public void navigateToControllerPanel(){
        clickOn(queryForDevicesTab);
        clickOn(queryForNewDeviceButton);
    }

    /**
     * Close the device panel
     */
    @After
    public void closeNewDevicePanel(){
        clickOn(queryForCloseButton);
    }
}
