package unit.kernel.serial_ports.rxtx_port_driver;

import kernel.serial_ports.RXTXPortDriver;
import org.jmock.Expectations;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Contains unit tests for {@link RXTXPortDriver#getSerialPortNames()}
 */
public final class GetSerialPortNames extends RXTXPortDriverTestCase {
    @Test
    public void getSerialPortNames(){
        context.checking(new JMockExpectations());

        assertNotNull(portDriver.getSerialPortNames());
    }

    /**
     * Enumerates what is to be expected out of mock objects in
     * {@link #getSerialPortNames()}
     */
    private class JMockExpectations extends Expectations {
        JMockExpectations() {
            oneOf(mockJavaCommAPI).getPortIdentifiers();
        }
    }
}
