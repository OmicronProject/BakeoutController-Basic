package unit.kernel.models.device_registry;

import kernel.models.DeviceContainer;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

/**
 * Contains tests asserting that the initial state of the
 * {@link DeviceContainer} was correctly instantiated
 */
public final class Constructor extends DeviceRegistryTestCase {
    @Test
    public void constructor(){
        assertFalse(this.deviceRegistry.hasPowerSupply());
    }
}
