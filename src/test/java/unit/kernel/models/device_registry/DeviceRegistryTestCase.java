package unit.kernel.models.device_registry;

import devices.PowerSupply;
import kernel.models.DeviceRegistry;
import org.junit.Before;
import unit.kernel.models.ModelsTestCase;

/**
 * Base class for unit tests of {@link DeviceRegistry}
 */
public abstract class DeviceRegistryTestCase extends ModelsTestCase {
    protected DeviceRegistry deviceRegistry;
    protected PowerSupply mockPowerSupply;

    @Before
    public void setMockPowerSupply(){
        mockPowerSupply = context.mock(PowerSupply.class);
    }

    @Before
    public void setDeviceRegistry(){
        deviceRegistry = new DeviceRegistry();
    }
}
