package unit.kernel.models.variable_provider_registry;

import kernel.models.VariableProviderRegistry;
import kernel.views.variables.Pressure;
import kernel.views.variables.VariableProvider;
import org.junit.Before;
import unit.kernel.models.ModelsTestCase;

/**
 * Base class for unit tests {@link kernel.models.VariableProviderRegistry}
 */
public abstract class VariableProviderRegistryTestCase extends ModelsTestCase {
    protected VariableProviderRegistry providerRegistry;

    protected VariableProvider<Pressure> mockPressureProvider;

    @Before
    public void setProviderRegistry(){
        providerRegistry = new VariableProviderRegistry();
    }

    @Before
    public void setMockPressureProvider(){
        mockPressureProvider = context.mock(VariableProvider.class);
    }
}
