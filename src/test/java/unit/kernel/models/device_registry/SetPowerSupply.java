package unit.kernel.models.device_registry;

import devices.PowerSupply;
import kernel.models.DeviceContainer;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Contains unit tests for
 * {@link DeviceContainer#setPowerSupply(PowerSupply)}
 */
public final class SetPowerSupply extends DeviceRegistryTestCase {
    @Test
    public void setPowerSupply(){
        this.deviceRegistry.setPowerSupply(mockPowerSupply);

        assertEquals(
                mockPowerSupply,
                this.deviceRegistry.getPowerSupply()
        );
        assertTrue(this.deviceRegistry.hasPowerSupply());
    }
}
