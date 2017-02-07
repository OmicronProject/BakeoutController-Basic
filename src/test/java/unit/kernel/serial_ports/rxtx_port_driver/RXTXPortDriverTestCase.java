package unit.kernel.serial_ports.rxtx_port_driver;

import kernel.serial_ports.RXTXPortDriver;
import org.junit.Before;
import unit.kernel.serial_ports.SerialPortsTestCase;

/**
 * Base class for {@link kernel.serial_ports.RXTXPortDriver}
 */
public abstract class RXTXPortDriverTestCase extends SerialPortsTestCase {
    protected final String applicationName = "Unit Testing";
    protected RXTXPortDriver portDriver;

    @Before
    public void setUpRXTXPortDriver(){
        this.portDriver = new RXTXPortDriver(
            this.applicationName, this.mockJavaCommAPI
        );
    }
}
