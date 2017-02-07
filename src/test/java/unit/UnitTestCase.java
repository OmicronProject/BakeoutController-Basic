package unit;

import javafx.stage.Stage;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.junit.After;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import java.util.concurrent.TimeoutException;

/**
 * Base class for all unit tests
 */
public abstract class UnitTestCase extends ApplicationTest {
    /**
     * Set up a context for performing mocks using JMock
     */
    protected final Mockery context = new UnitTestingMockery();

    /**
     * Check that any mockeries set up were correctly used
     */
    @After
    public void assertGoodContext(){
        this.context.assertIsSatisfied();
    }

    /**
     * Extends the JUnit mockery with a synchronizer to allow for
     * multi-threaded testing.
     */
    private class UnitTestingMockery extends JUnit4Mockery {
        UnitTestingMockery(){
            setThreadingPolicy(new Synchroniser());
        }
    }

    @Override
    public void init() throws TimeoutException {
        FxToolkit.registerStage(Stage::new);
    }

    /**
     * Do not start the UI
     * @param stage the Stage to which a UI would normally be attached
     */
    @Override
    public void start(Stage stage) throws Exception {
        // This is deliberate. Don't start the test
    }

    @Override
    public void stop() throws TimeoutException {
        FxToolkit.hideStage();
    }
}
