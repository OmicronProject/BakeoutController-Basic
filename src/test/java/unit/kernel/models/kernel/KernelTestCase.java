package unit.kernel.models.kernel;

import kernel.ApplicationKernelFactory;
import kernel.Kernel;
import kernel.KernelFactory;
import kernel.serial_ports.PortDriver;
import org.junit.Before;
import unit.kernel.models.ModelsTestCase;

import static org.junit.Assert.assertTrue;

/**
 * Base class for unit tests of {@link kernel.Kernel}
 */
public abstract class KernelTestCase extends ModelsTestCase {
    protected Kernel kernel;
    protected PortDriver mockPortDriver;

    @Before
    public void setUp(){
        setMockPortDriver();
        setKernel();
    }

    private void setMockPortDriver(){
        mockPortDriver = context.mock(PortDriver.class);
    }

    private void setKernel(){
        KernelFactory kernelFactory = new ApplicationKernelFactory();
        kernelFactory.setPortDriver(mockPortDriver);

        assertTrue(kernelFactory.canKernelBeStarted());

        kernel = kernelFactory.getKernelInstance();
    }
}
