package unit.kernel.models.device_registry;

import devices.PowerSupply;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Contains unit tests for
 * {@link kernel.models.DeviceRegistry#setPowerSupply(PowerSupply)}
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
