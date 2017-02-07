package unit.ui.controllers.modals.new_device_controller;

import devices.TDKLambdaPowerSupply;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import kernel.views.DeviceListEntry;
import org.junit.Before;
import org.junit.Test;
import ui.controllers.modals.NewDeviceController;

import static org.junit.Assert.assertEquals;

/**
 * Contains unit tests for {@link NewDeviceController#initialize()}
 */
public final class Initialize extends NewDeviceControllerTestCase {
    private final int expectedListSize = 1;
    private final Class expectedDeviceClass = TDKLambdaPowerSupply.class;
    private final String deviceSelectorQuery = "#device-selector";

    private ComboBox<DeviceListEntry> deviceSelector;

    @Before
    public void getDeviceSelector(){
        this.deviceSelector = lookup(deviceSelectorQuery).query();
    }

    @Test
    public void testInitializer(){
        ObservableList<DeviceListEntry> entriesInDeviceList =
                deviceSelector.getItems();

        assertEquals(expectedListSize, entriesInDeviceList.size());
        assertEquals(
                expectedDeviceClass,
                entriesInDeviceList.get(0).getDeviceType()
        );
    }
}
