package unit.kernel.models.variable_provider_container;

import kernel.models.VariableProviderContainer;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Contains unit tests for
 * {@link VariableProviderContainer#hasPressureProvider()}
 */
public final class HasPressureProvider extends
        VariableProviderContainerTestCase {
    @Test
    public void hasPressureProviderFalse(){
        providerRegistry.setPressureProvider(null);

        assertFalse(providerRegistry.hasPressureProvider());
    }

    @Test
    public void hasPressureProviderTrue(){
        providerRegistry.setPressureProvider(mockPressureProvider);

        assertTrue(providerRegistry.hasPressureProvider());
    }
}
