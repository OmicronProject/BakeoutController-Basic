package unit.kernel.models.variable_provider_container;

import kernel.models.VariableProviderContainer;
import kernel.views.variables.VariableProvider;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Contains unit tests for
 * {@link VariableProviderContainer#setPressureProvider(VariableProvider)}
 */
public final class SetPressureProvider extends
        VariableProviderContainerTestCase {
    @Test
    public void setProvider(){
        providerRegistry.setPressureProvider(mockPressureProvider);

        assertEquals(mockPressureProvider, providerRegistry
                .getPressureProvider());
    }
}
