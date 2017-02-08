package unit.kernel.models.kernel;

import kernel.models.Kernel;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Contains unit tests for {@link Kernel#getPowerSupplyFactory()}
 */
public final class GetPowerSupplyFactory extends KernelTestCase {
    @Test
    public void getPowerSupplyFactory(){
        assertNotNull(kernel.getPowerSupplyFactory());
    }

    /**
     * Tests that the power supply has a kernel that matches this kernel.
     *
     * @implNote This was written in response to a bug I found while trying
     * this code out on the TDK Lambda Power supply
     */
    @Test
    public void getPowerSupplyKernel(){
        assertEquals(kernel, kernel.getPowerSupplyFactory().getKernel());
    }
}
