package unit.kernel.models.kernel;

import kernel.models.Kernel;
import kernel.views.CommPortReporter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Contains unit tests for {@link Kernel#getSerialPortNames()}
 */
public final class GetSerialPortNames extends KernelTestCase {
    private CommPortReporter reporter;

    @Before
    public void setReporter(){
        reporter = kernel.getCommPortReporter();
    }

    @Test
    public void getSerialPortNames(){
        assertNotNull(reporter.getSerialPortNames());
    }
}
