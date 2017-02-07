package unit.kernel.models.device_connector;

import kernel.controllers.DeviceRegistry;
import kernel.models.DeviceConnector;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Contains unit tests for
 * {@link DeviceConnector#setDeviceRegistry(DeviceRegistry)}
 *
 */
public final class SetDeviceRegistry extends DeviceConnectorTestCase {
    private DeviceRegistry registry = context.mock(DeviceRegistry.class);

    @Test
    public void setDeviceRegistry(){
        deviceConnector.setDeviceRegistry(registry);

        assertEquals(
                registry,
                deviceConnector.getDeviceRegistry()
        );
    }
}
