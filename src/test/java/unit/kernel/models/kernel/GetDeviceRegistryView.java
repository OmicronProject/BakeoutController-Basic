package unit.kernel.models.kernel;

import kernel.models.Kernel;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Contains unit tests for {@link Kernel#getDeviceRegistryView()}
 */
public final class GetDeviceRegistryView extends KernelTestCase {
    @Test
    public void getDeviceRegistryView(){
        assertNotNull(kernel.getDeviceRegistryView());
    }
}
