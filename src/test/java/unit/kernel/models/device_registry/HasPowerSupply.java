package unit.kernel.models.device_registry;

import kernel.models.DeviceContainer;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Contains unit tests for {@link DeviceContainer#hasPowerSupply()}
 */
public final class HasPowerSupply extends DeviceRegistryTestCase {
    @Test
    public void hasPowerSupplyIsInitiallyFalse(){
        assertFalse(this.deviceRegistry.hasPowerSupply());
    }
    @Test
    public void hasPowerSupplyIsTrueAfterSetting(){
        this.deviceRegistry.setPowerSupply(mockPowerSupply);

        assertTrue(this.deviceRegistry.hasPowerSupply());
    }
}
