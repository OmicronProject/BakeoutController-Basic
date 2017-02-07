package unit.ui.user_interface_configuration;

import javafx.stage.Stage;
import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ui.UserInterfaceConfiguration;
import unit.ui.UserInterfaceTestCase;

/**
 * Base class for tests of {@link ui.UserInterfaceConfiguration}
 */
public abstract class UserInterfaceConfigurationTestCase extends
        UserInterfaceTestCase {
    protected ApplicationContext applicationContext;

    @Before
    public void setUserInterfaceConfiguration(){
        this.applicationContext = new AnnotationConfigApplicationContext(
            UserInterfaceConfiguration.class
        );
    }

    @Override public void start(Stage stage){
        // Don't start the application
    }
}
