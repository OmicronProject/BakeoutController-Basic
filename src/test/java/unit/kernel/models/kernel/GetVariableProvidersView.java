package unit.kernel.models.kernel;

import kernel.Kernel;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Contains unit tests for {@link Kernel#getVariableProvidersView()}
 */
public final class GetVariableProvidersView extends KernelTestCase {
    @Test
    public void getVariableProviders(){
        assertNotNull(kernel.getVariableProvidersView());
    }
}
