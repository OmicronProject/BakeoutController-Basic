package unit.kernel.models.device_registry;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by mkononen on 13/02/17.
 */
public final class SetPressureGauge extends DeviceRegistryTestCase {
    @Test
    public void setPressureGauge(){
        deviceRegistry.setPressureGauge(mockPressureGauge);
        assertEquals(
                mockPressureGauge, deviceRegistry.getPressureGauge()
        );
    }
}
