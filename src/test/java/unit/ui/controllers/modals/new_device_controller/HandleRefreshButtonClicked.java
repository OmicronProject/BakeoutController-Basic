package unit.ui.controllers.modals.new_device_controller;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import org.junit.After;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;


/**
 * Contains unit tests for
 * {@link ui.controllers.modals.NewDeviceController#handleRefreshButtonClicked(ActionEvent)}
 */
public final class HandleRefreshButtonClicked extends
        NewDeviceControllerTestCase {

    private static final String queryForRefreshButton = "#refresh-button";
    private static final String queryForPortSelector = "#port-selector";
    private static final String additionalPortName = "/dev/ttyUSB1";

    /**
     * Tests that when another port is added, that clicking the refresh
     * button will add this port to the port list.
     */
    @Test
    public void clickRefreshButton(){
        clickOn(queryForRefreshButton);

        addPortToPortList(additionalPortName);

        clickOn(queryForRefreshButton);

        assertTrue(getPortNames().contains(additionalPortName));
    }

    private void addPortToPortList(String newPortName){
        List<String> testData = getTestData();
        testData.add(newPortName);
    }

    @After
    public void removeAdditionalPortName(){
        List<String> testData = getTestData();
        testData.remove(additionalPortName);
    }

    @SuppressWarnings("unchecked")
    private List<String> getPortNames(){
        Node portSelector = lookup(queryForPortSelector).query();

        return ((ComboBox<String>) portSelector).getItems();
    }

    @SuppressWarnings("unchecked")
    private List<String> getTestData(){
        return (List<String>) applicationContext.getBean("testData");
    }
}
