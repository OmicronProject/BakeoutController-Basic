package unit.kernel.models.kernel;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Contains unit tests for {@link kernel.models.Kernel#isPortInUse(String)}
 */
public final class IsPortInUse extends KernelTestCase {
    private static final String portName = "/dev/ttyUSB0";

    @Test
    public void isPortInUse(){
        assertNotNull(
                kernel.getCommPortReporter().isPortInUse(portName)
        );
    }
}
