package unit.main.app_configuration;

import main.ApplicationConfiguration;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Contains unit tests for {@link ApplicationConfiguration#kernel()}
 */
public final class Kernel extends AppConfigurationTestCase {
    @Test
    public void kernel(){
        assertNotNull(
            context.getBean(kernel.Kernel.class)
        );
    }
}
