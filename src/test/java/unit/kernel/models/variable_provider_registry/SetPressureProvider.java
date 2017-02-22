package unit.kernel.models.variable_provider_registry;

import kernel.views.variables.VariableProvider;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Contains unit tests for
 * {@link kernel.models.VariableProviderRegistry#setPressureProvider(VariableProvider)}
 */
public final class SetPressureProvider extends
        VariableProviderRegistryTestCase {
    @Test
    public void setProvider(){
        providerRegistry.setPressureProvider(mockPressureProvider);

        assertEquals(mockPressureProvider, providerRegistry
                .getPressureProvider());
    }
}
