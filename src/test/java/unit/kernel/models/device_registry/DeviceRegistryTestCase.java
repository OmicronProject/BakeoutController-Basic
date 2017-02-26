package unit.kernel.models.device_registry;

import devices.PowerSupply;
import devices.PressureGauge;
import kernel.models.DeviceContainer;
import org.junit.Before;
import unit.kernel.models.ModelsTestCase;

/**
 * Base class for unit tests of {@link DeviceContainer}
 */
public abstract class DeviceRegistryTestCase extends ModelsTestCase {
    protected DeviceContainer deviceRegistry;
    protected PowerSupply mockPowerSupply;
    protected PressureGauge mockPressureGauge;

    @Before
    public void setMockPowerSupply(){
        mockPowerSupply = context.mock(PowerSupply.class);
    }

    @Before
    public void setMockPressureGauge(){
        this.mockPressureGauge = context.mock(PressureGauge.class);
    }

    @Before
    public void setDeviceRegistry(){
        deviceRegistry = new DeviceContainer();
    }
}
