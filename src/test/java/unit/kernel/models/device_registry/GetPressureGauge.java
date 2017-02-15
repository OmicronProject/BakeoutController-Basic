package unit.kernel.models.device_registry;

import kernel.models.DeviceRegistry;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Contains unit tests for {@link DeviceRegistry#getPressureGauge()}
 */
public final class GetPressureGauge extends DeviceRegistryTestCase {
    @Before
    public void setPressureGauge(){
        deviceRegistry.setPressureGauge(mockPressureGauge);
    }

    @Test
    public void getPressureGauge(){
        assertEquals(
                mockPressureGauge,
                deviceRegistry.getPressureGauge()
        );
        assertTrue(deviceRegistry.hasPressureGauge());
    }
}
