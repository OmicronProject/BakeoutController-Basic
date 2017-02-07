package unit.ui.user_interface_launcher;

import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import unit.ui.UserInterfaceTestCase;


/**
 * Base class for {@link ui.UserInterfaceLauncher}
 */
public abstract class UserInterfaceLauncherTestCase extends
        UserInterfaceTestCase {

    /**
     * The stage supplied by TestFX, to be used for seeing if the
     * application starts.
     */
    protected Stage stage;

    protected final ApplicationContext applicationContext =
            new AnnotationConfigApplicationContext(
                    UserInterfaceTestConfiguration.class
            );

    /**
     * Save the stage for testing, so that launch behaviour can be checked
     * @param stage The stage in which this application is to start
     */
    @Override
    public void start(Stage stage){
        this.stage = stage;
    }
}
