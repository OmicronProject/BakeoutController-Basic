package unit.kernel.models.device_connector;

import kernel.models.DeviceConnector;
import kernel.views.DeviceListEntry;
import org.junit.Before;
import unit.kernel.models.ModelsTestCase;

/**
 * Base class for tests of {@link DeviceConnector}
 */
public abstract class DeviceConnectorTestCase extends ModelsTestCase {
    protected kernel.controllers.DeviceConnector deviceConnector;
    protected DeviceListEntry deviceListEntry;

    @Before
    public void setDeviceConnector(){
        deviceConnector = new DeviceConnector();
    }

    @Before
    public void setDeviceListEntry(){
        deviceListEntry = context.mock(DeviceListEntry.class);
    }
}
