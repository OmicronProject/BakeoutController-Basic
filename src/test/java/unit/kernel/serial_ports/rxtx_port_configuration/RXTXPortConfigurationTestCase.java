package unit.kernel.serial_ports.rxtx_port_configuration;

import kernel.serial_ports.PortConfiguration;
import kernel.serial_ports.RXTXPortConfiguration;
import org.junit.Before;
import unit.kernel.serial_ports.SerialPortsTestCase;

/**
 * Base class for tests involving
 * {@link kernel.serial_ports.RXTXPortConfiguration}
 */
public abstract class RXTXPortConfigurationTestCase extends
        SerialPortsTestCase {

    protected final int baudRate = PortConfiguration.BAUD_RATE_9600;
    protected final int stopBits = PortConfiguration.STOPBITS_1;
    protected final int dataBits = PortConfiguration.DATABITS_8;
    protected final int parityBits = PortConfiguration.PARITY_NONE;

    protected RXTXPortConfiguration portConfiguration;


    @Before
    public void setUpPortConfiguration(){
        this.portConfiguration = new RXTXPortConfiguration(
            baudRate, stopBits, dataBits, parityBits
        );
    }
}
