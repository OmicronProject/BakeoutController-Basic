package unit.kernel.serial_ports.rxtx_port_configuration;

import kernel.serial_ports.RXTXPortConfiguration;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Contains unit tests for {@link RXTXPortConfiguration#getBaudRate()}
 */
public final class GetBaudRate extends RXTXPortConfigurationTestCase {
    @Test
    public void getBaudRate(){
        assertEquals(this.baudRate, this.portConfiguration.getBaudRate());
    }
}
