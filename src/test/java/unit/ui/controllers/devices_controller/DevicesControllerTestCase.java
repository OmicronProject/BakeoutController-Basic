package unit.ui.controllers.devices_controller;

import javafx.scene.control.Button;
import org.junit.Before;
import unit.ui.controllers.ControllersTestCase;

/**
 * Base class for unit testing {@link ui.controllers.DevicesController}
 */
public abstract class DevicesControllerTestCase extends ControllersTestCase {
    protected Button newDeviceButton;

    protected static final String queryForNewDeviceButton = "#newDeviceButton";
    protected static final String queryForNewDeviceForm = "#newDeviceForm";
    private static final String queryForDevicesTab = "#devices-tab";

    @Before
    public void navigateToDevicesTab(){
        clickOn(queryForDevicesTab);
    }

    @Before
    public void setNewDeviceButton(){
        newDeviceButton = lookup(queryForNewDeviceButton).query();
    }
}
