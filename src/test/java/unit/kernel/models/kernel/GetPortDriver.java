package unit.kernel.models.kernel;

import kernel.Kernel;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Contains unit tests for {@link Kernel#getPortDriver()}
 */
public final class GetPortDriver extends KernelTestCase {
    @Test
    public void getPortDriver(){
        assertNotNull(kernel.getPortDriver());
    }
}
