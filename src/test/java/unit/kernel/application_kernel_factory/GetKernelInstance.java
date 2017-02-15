package unit.kernel.application_kernel_factory;

import exceptions.UnableToCreateKernelException;
import kernel.ApplicationKernelFactory;
import kernel.Kernel;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Contains unit tests for
 * {@link ApplicationKernelFactory#getKernelInstance()}
 */
public final class GetKernelInstance extends
        ApplicationKernelBootstrapperTestCase {

    @Test(expected = UnableToCreateKernelException.class)
    public void unfinishedKernel(){
        this.applicationKernelBootstrapper.getKernelInstance();
    }

    @Test
    public void finishedKernel(){
        constructKernelFactory();
        Kernel kernel = this.applicationKernelBootstrapper.getKernelInstance();
        assertNotNull(kernel);
    }

    @Test
    public void isKernelSingleton(){
        constructKernelFactory();

        Kernel firstKernelGet = this.applicationKernelBootstrapper
                .getKernelInstance();
        Kernel secondKernelGet = this.applicationKernelBootstrapper
                .getKernelInstance();

        assertEquals(
                firstKernelGet, secondKernelGet
        );
    }

    private void constructKernelFactory(){
        applicationKernelBootstrapper.setPortDriver(mockPortDriver);
    }
}
