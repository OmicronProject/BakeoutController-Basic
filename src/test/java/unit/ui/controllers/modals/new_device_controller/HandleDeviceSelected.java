package unit.ui.controllers.modals.new_device_controller;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import kernel.views.DeviceListEntry;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Contains unit tests for
 * {@link ui.controllers.modals.NewDeviceController#handleDeviceSelected(ActionEvent)}
 */
public final class HandleDeviceSelected extends NewDeviceControllerTestCase {
    private static final String queryForDeviceSelector = "#device-selector";
    private static final String queryForBaudRateSelector =
            "#availableBaudRates";
    private DeviceListEntry listEntry;

    @Before
    public void getListEntry(){
        listEntry = applicationContext.getBean(DeviceListEntry.class);
    }

    @Test
    public void selectDevice(){
        ComboBox<DeviceListEntry> deviceSelector = getDeviceSelector();
        deviceSelector.getSelectionModel().select(listEntry);

        Integer baudRate = getSelectedBaudRate();
        Integer expectedBaudRate = getExpectedBaudRate();

        assertEquals(expectedBaudRate, baudRate);
    }

    @SuppressWarnings("unchecked")
    private ComboBox<DeviceListEntry> getDeviceSelector(){
        Node selector = lookup(queryForDeviceSelector).query();
        return (ComboBox) selector;
    }

    @SuppressWarnings("unchecked")
    private Integer getSelectedBaudRate(){
        Node baudRateNode = lookup(queryForBaudRateSelector).query();
        ComboBox<Integer> baudRateSelector = (ComboBox<Integer>) baudRateNode;

        return baudRateSelector.getSelectionModel().getSelectedItem();
    }

    private Integer getExpectedBaudRate(){

        return listEntry.getDefaultConfiguration().getBaudRate();
    }
}
