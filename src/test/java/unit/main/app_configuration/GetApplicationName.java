package unit.main.app_configuration;

import main.ApplicationConfiguration;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Contains unit tests for
 * {@link ApplicationConfiguration#getApplicationName()}
 */
public final class GetApplicationName extends AppConfigurationTestCase {
    @Test
    public void getapplicationName(){
        assertNotNull(context.getApplicationName());
    }
}
