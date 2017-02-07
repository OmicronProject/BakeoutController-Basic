package unit.kernel.models.rxtx_port_configuration_factory;

import kernel.controllers.RS232PortConfigurationFactory;
import kernel.models.RXTXPortConfigurationFactory;
import kernel.serial_ports.PortConfiguration;
import org.junit.Before;
import unit.kernel.models.ModelsTestCase;

/**
 * Base class for unit tests of
 * {@link RXTXPortConfigurationFactory}
 */
public abstract class RXTXPortConfigurationFactoryTestCase extends
        ModelsTestCase {
    protected RS232PortConfigurationFactory portConfigurationFactory;

    protected static final Integer baudRate = 9001;
    protected static final Integer dataBits = 8;
    protected static final Integer stopBits = 0;
    protected static final Integer parityBits = 4;


    @Before
    public void setPortConfigurationFactory(){
        this.portConfigurationFactory = new RXTXPortConfigurationFactory();
    }

    protected PortConfiguration getConstructedConfiguration(){
        return this.portConfigurationFactory.getConfiguration();
    }
}
