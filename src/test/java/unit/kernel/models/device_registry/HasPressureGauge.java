package unit.kernel.models.device_registry;

import kernel.models.DeviceContainer;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Contains unit tests for {@link DeviceContainer#hasPressureGauge()}
 */
public final class HasPressureGauge extends DeviceRegistryTestCase {
    @Test
    public void testTrue(){
        deviceRegistry.setPressureGauge(mockPressureGauge);
        assertTrue(deviceRegistry.hasPressureGauge());
    }

    @Test
    public void testFalse(){
        assertFalse(deviceRegistry.hasPressureGauge());
    }
}
