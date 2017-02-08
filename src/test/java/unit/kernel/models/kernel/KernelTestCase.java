package unit.kernel.models.kernel;

import kernel.Kernel;
import main.ApplicationConfiguration;
import org.junit.Before;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import unit.kernel.models.ModelsTestCase;

/**
 * Base class for unit tests of {@link kernel.Kernel}
 */
public abstract class KernelTestCase extends ModelsTestCase {
    protected ApplicationContext applicationContext;
    protected Kernel kernel;

    @Before
    public void setUp(){
        setApplicationContext();
        setKernel();
    }

    private void setApplicationContext(){
        applicationContext = new AnnotationConfigApplicationContext(
                ApplicationConfiguration.class
       );
    }

    private void setKernel(){
        kernel = applicationContext.getBean(Kernel.class);
    }
}
