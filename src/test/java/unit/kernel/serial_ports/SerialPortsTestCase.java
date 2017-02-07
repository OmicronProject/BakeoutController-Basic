package unit.kernel.serial_ports;

import kernel.serial_ports.comm_port_wrapper.PortIdentifierGetter;
import org.junit.Before;
import unit.kernel.KernelTestCase;

/**
 * Base class for unit tests of {@link kernel.serial_ports}
 */
public abstract class SerialPortsTestCase extends KernelTestCase {
    protected PortIdentifierGetter mockJavaCommAPI;

    @Before
    public final void setUpMockJavaCommunicationsAPI(){
        this.mockJavaCommAPI = this.context.mock(PortIdentifierGetter.class);
    }
}
