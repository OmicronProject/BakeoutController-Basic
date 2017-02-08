package unit.kernel.models.kernel;

import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Contains unit tests for {@link kernel.models.Kernel#isPortInUse(String)}
 */
public final class IsPortInUse extends KernelTestCase {
    private static final String portName = "/dev/ttyUSB0";

    @Before
    public void setExpectations(){
        context.checking(new ExpectationsForPortDriver());
    }

    @Test
    public void isPortInUse(){
        assertNotNull(
                kernel.getCommPortReporter().isPortInUse(portName)
        );
    }

    private class ExpectationsForPortDriver extends Expectations {
        public ExpectationsForPortDriver(){
            oneOf(mockPortDriver).getPortByName(portName);
        }
    }
}
