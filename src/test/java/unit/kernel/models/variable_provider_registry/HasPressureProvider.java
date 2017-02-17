package unit.kernel.models.variable_provider_registry;

import kernel.models.VariableProviderRegistry;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Contains unit tests for
 * {@link VariableProviderRegistry#hasPressureProvider()}
 */
public final class HasPressureProvider extends
        VariableProviderRegistryTestCase {
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
