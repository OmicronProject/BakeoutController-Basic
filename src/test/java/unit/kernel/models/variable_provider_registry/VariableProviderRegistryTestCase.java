package unit.kernel.models.variable_provider_registry;

import kernel.models.VariableProviderContainer;
import kernel.views.variables.Pressure;
import kernel.views.variables.VariableProvider;
import org.junit.Before;
import unit.kernel.models.ModelsTestCase;

/**
 * Base class for unit tests {@link VariableProviderContainer}
 */
public abstract class VariableProviderRegistryTestCase extends ModelsTestCase {
    protected VariableProviderContainer providerRegistry;

    protected VariableProvider<Pressure> mockPressureProvider;

    @Before
    public void setProviderRegistry(){
        providerRegistry = new VariableProviderContainer();
    }

    @Before
    public void setMockPressureProvider(){
        mockPressureProvider = context.mock(VariableProvider.class);
    }
}
