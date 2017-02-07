package unit.kernel.models.device_connector;

import kernel.models.DeviceConnector;
import kernel.controllers.DeviceRegistry;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Contains unit tests for
 * {@link DeviceConnector#getDeviceRegistry()}
 */
public final class GetDeviceRegistry extends DeviceConnectorTestCase {
    private final DeviceRegistry registry = context.mock(DeviceRegistry.class);

    @Before
    public void setDeviceRegistry(){
        deviceConnector.setDeviceRegistry(registry);
    }

    @Test
    public void getDeviceRegistry(){
        assertEquals(
                registry,
                deviceConnector.getDeviceRegistry()
        );
    }
}
