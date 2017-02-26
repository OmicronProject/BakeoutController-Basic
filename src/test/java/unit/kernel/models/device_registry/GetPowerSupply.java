package unit.kernel.models.device_registry;

import kernel.models.DeviceContainer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Contains unit tests for {@link DeviceContainer#getPowerSupply()}
 */
public final class GetPowerSupply extends DeviceRegistryTestCase {
    @Before
    public void setupDeviceRegistry(){
        this.deviceRegistry.setPowerSupply(mockPowerSupply);
    }

    @Test
    public void getPowerSupply(){
        assertEquals(
                mockPowerSupply,
                this.deviceRegistry.getPowerSupply()
        );
    }
}
