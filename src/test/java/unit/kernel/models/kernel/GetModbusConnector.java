package unit.kernel.models.kernel;

import kernel.models.Kernel;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Contains unit tests for {@link Kernel#getModbusConnector()}
 */
public final class GetModbusConnector extends KernelTestCase {
    @Test
    public void getModbusConnector(){
        assertNotNull(kernel.getModbusConnector());
    }
}
