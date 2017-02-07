package unit.kernel.serial_ports.rxtx_port_configuration;

import kernel.serial_ports.RXTXPortConfiguration;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Base class for unit tests of {@link RXTXPortConfiguration#getStopBits()}
 */
public final class GetStopBits extends RXTXPortConfigurationTestCase {
    @Test
    public void getStopBits(){
        assertEquals(this.stopBits, this.portConfiguration.getStopBits());
    }
}
