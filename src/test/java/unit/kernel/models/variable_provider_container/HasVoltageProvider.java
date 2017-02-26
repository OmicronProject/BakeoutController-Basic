package unit.kernel.models.variable_provider_container;

import kernel.models.VariableProviderContainer;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Contains unit tests for
 * {@link VariableProviderContainer#hasPressureProvider()}
 */
public final class HasVoltageProvider extends
        VariableProviderContainerTestCase {

    /**
     * Test that the false response is returned if the voltage provider is
     * null
     */
    @Test
    public void hasVoltageProviderFalse(){
        providerRegistry.setVoltageProvider(null);
        assertFalse(providerRegistry.hasVoltageProvider());
    }

    /**
     * Tests that the function returns {@link Boolean#TRUE} if a
     * {@link kernel.views.variables.VoltageProvider} has been set
     */
    @Test
    public void hasVoltageProviderTrue(){
        providerRegistry.setVoltageProvider(mockVoltageProvider);
        assertTrue(providerRegistry.hasVoltageProvider());
    }
}
