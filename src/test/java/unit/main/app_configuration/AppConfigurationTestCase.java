package unit.main.app_configuration;

import main.ApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import unit.main.MainTestCase;

/**
 * Base class for tests of {@link ApplicationConfiguration}
 */
public abstract class AppConfigurationTestCase extends MainTestCase {
    protected final ApplicationContext context = new
        AnnotationConfigApplicationContext(ApplicationConfiguration.class);
}
