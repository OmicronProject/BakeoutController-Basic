package unit.kernel.models.variable_provider_container;

import kernel.views.variables.VariableProvider;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Contains unit tests for
 * {@link kernel.controllers.variables.VariableProviderContainer#setVoltageProvider(VariableProvider)}
 */
public final class SetVoltageProvider extends
        VariableProviderContainerTestCase {
    @Test
    public void setVoltageProvider(){
        providerRegistry.setVoltageProvider(mockVoltageProvider);

        assertEquals(
                mockVoltageProvider, providerRegistry.getVoltageProvider()
        );
    }
}
