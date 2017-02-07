package unit.main.app_configuration;

import kernel.serial_ports.comm_port_wrapper.PortIdentifierGetter;
import main.ApplicationConfiguration;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Contains unit tests for {@link ApplicationConfiguration#portWrapper()}
 */
public final class PortWrapper extends AppConfigurationTestCase {
    @Test
    public void portWrapper(){
        assertNotNull(context.getBean(PortIdentifierGetter.class));
    }
}
