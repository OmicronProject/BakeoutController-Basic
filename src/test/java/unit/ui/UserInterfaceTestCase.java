package unit.ui;

import javafx.stage.Stage;
import org.jmock.Mockery;
import org.junit.After;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ui.JavaFXGraphicalUserInterfaceLauncher;
import unit.UnitTestCase;

/**
 * Base class for tests involving the UI
 */
public abstract class UserInterfaceTestCase extends UnitTestCase {
    protected static final ApplicationContext applicationContext =
        new AnnotationConfigApplicationContext(TestingConfiguration.class);

    protected static volatile JavaFXGraphicalUserInterfaceLauncher application = new
            JavaFXGraphicalUserInterfaceLauncher(applicationContext);

    @Override public void start(Stage stage) throws Exception {
        application.start(stage);
    }

    @After public void assertGoodApplicationContextMockery(){
        applicationContext.getBean(Mockery.class).assertIsSatisfied();
    }
}
