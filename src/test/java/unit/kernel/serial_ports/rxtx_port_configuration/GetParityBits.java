package unit.kernel.serial_ports.rxtx_port_configuration;

import kernel.serial_ports.RXTXPortConfiguration;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Base class for testing {@link RXTXPortConfiguration#getParityBits()}
 */
public final class GetParityBits extends RXTXPortConfigurationTestCase {
    @Test
    public void getParityBits(){
        assertEquals(this.parityBits, this.portConfiguration.getParityBits());
    }
}
