package unit.kernel.serial_ports.rxtx_port_driver;

import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Contains unit tests for
 * {@link kernel.serial_ports.RXTXPortDriver#getPortByName(String)}
 */
public final class GetPortByName extends RXTXPortDriverTestCase {
    private final String portName = "/dev/null";

    @Test
    public void getPortbyName() throws NoSuchPortException,
            PortInUseException {
        assertNotNull(portDriver.getPortByName(portName));
    }
}
