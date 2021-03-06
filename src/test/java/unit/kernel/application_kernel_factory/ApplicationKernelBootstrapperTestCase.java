package unit.kernel.application_kernel_factory;

import exceptions.UnableToCreateKernelException;
import kernel.ApplicationKernelFactory;
import kernel.KernelFactory;
import kernel.models.Kernel;
import kernel.serial_ports.PortDriver;
import kernel.views.VoltageReporter;
import org.junit.Before;
import unit.kernel.KernelTestCase;

import static org.junit.Assert.fail;

/**
 * Base class for all unit tests of {@link Kernel}
 */
public abstract class ApplicationKernelBootstrapperTestCase
        extends KernelTestCase {
    protected VoltageReporter mockVoltageReporter;
    protected PortDriver mockPortDriver;
    protected KernelFactory applicationKernelBootstrapper;

    @Before
    public void setUp(){
        setupMockVoltageReporter();
        setupMockPortDriver();
        setupBootstrapper();
    }

    protected void assertKernelConstructionFails(){
        try {
            this.applicationKernelBootstrapper.getKernelInstance();
            fail("Expected an exception to be thrown");
        } catch (UnableToCreateKernelException error){
            error.printStackTrace();
        }
    }

    private void setupMockVoltageReporter(){
        mockVoltageReporter = context.mock(VoltageReporter.class);
    }

    private void setupMockPortDriver(){
        mockPortDriver = context.mock(PortDriver.class);
    }

    private void setupBootstrapper(){
        applicationKernelBootstrapper = new ApplicationKernelFactory();
    }
}
