package unit.kernel.models.kernel;

import kernel.models.Kernel;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Contains unit tests for {@link Kernel#getDeviceRegistryController()}
 */
public final class GetDeviceRegistryController extends KernelTestCase {
    @Test
    public void getDeviceRegistryController(){
        assertNotNull(kernel.getDeviceRegistryController());
    }
}
