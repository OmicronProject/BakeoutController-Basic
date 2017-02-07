package unit.main.app_configuration;

import main.ApplicationConfiguration;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Contains unit tests for {@link ApplicationConfiguration#portDriver()}
 */
public final class PortDriver extends AppConfigurationTestCase {
    @Test
    public void portDriver(){
        assertNotNull(
            context.getBean(kernel.serial_ports.PortDriver.class)
        );
    }
}
