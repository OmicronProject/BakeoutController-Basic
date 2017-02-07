package unit.ui.controllers.device_list_controller;

import org.junit.Before;
import unit.ui.controllers.ControllersTestCase;

/**
 * Base class for unit tests of {@link ui.controllers.DeviceListController}
 */
public abstract class DeviceListControllerTestCase extends
        ControllersTestCase {
    protected static final String queryForRefreshButton = "#refresh-button";
    protected static final String queryForNoPowerSupply =
            "#no-power-supply-message";
    protected static final String queryForPowerSupply =
            "#power-supply-message";
    protected static final String queryForDeviceListTab =
            "#device-list";

    @Before
    public void navigateToDeviceListTab(){
        clickOn(queryForDeviceListTab);
    }
}
