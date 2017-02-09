package unit.ui.user_interface_launcher;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;
import ui.FXMLStage;
import ui.JavaFXGraphicalUserInterfaceLauncher;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * Contains unit tests for {@link JavaFXGraphicalUserInterfaceLauncher#start(Stage)}
 */
public final class Start extends UserInterfaceLauncherTestCase {
    private JavaFXGraphicalUserInterfaceLauncher launcher;

    @Before
    public void setLauncher(){
        this.launcher = new JavaFXGraphicalUserInterfaceLauncher(applicationContext);
    }

    @Test
    public void start() throws Exception {
        this.applicationContext.getBean(
                Mockery.class
        ).checking(new ExpectationsForSetPrimaryStage());

        this.launcher.start(stage);
        assertNotNull(this.launcher);
    }

    private class ExpectationsForSetPrimaryStage extends Expectations {
        private final FXMLStage mockApplication = applicationContext.getBean(
                FXMLStage.class
        );

        public ExpectationsForSetPrimaryStage(){
            oneOf(mockApplication).show();
            loadFXMLExpectation();
        }

        private void loadFXMLExpectation(){
            try {
                oneOf(mockApplication).loadFXML();
            } catch (IOException error){
                fail("Unable to set LoadFXML expectation. This shouldn't " +
                        "happen");
            }
        }
    }
}
