package unit.kernel.models.kernel;

import kernel.Kernel;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Contains unit tests for {@link Kernel#getVariableProvidersController()}
 */
public final class GetVariableProvidersController extends KernelTestCase {
    @Test
    public void getVariableProvidersController(){
        assertNotNull(kernel.getVariableProvidersController());
    }
}
