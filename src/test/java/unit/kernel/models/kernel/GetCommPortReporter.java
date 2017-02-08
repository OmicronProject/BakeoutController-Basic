package unit.kernel.models.kernel;

import kernel.Kernel;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Contains unit tests for {@link Kernel#getCommPortReporter()}
 */
public final class GetCommPortReporter extends KernelTestCase {
    @Test
    public void getCommPortReporter(){
        assertNotNull(
                kernel.getCommPortReporter()
        );
    }
}
