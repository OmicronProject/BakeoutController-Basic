package unit.kernel.models.variable_provider_container;

import kernel.models.VariableProviderContainer;
import kernel.views.variables.PressureProvider;
import kernel.views.variables.VoltageProvider;
import org.junit.Before;
import unit.kernel.models.ModelsTestCase;

/**
 * Base class for unit tests {@link VariableProviderContainer}
 */
public abstract class VariableProviderContainerTestCase extends ModelsTestCase {
    protected VariableProviderContainer providerRegistry;

    protected PressureProvider mockPressureProvider;

    protected VoltageProvider mockVoltageProvider;

    @Before
    public void setProviderRegistry(){
        providerRegistry = new VariableProviderContainer();
    }

    @Before
    public void setMockPressureProvider(){
        mockPressureProvider = context.mock(PressureProvider.class);
    }

    @Before
    public void setMockVoltageProvider(){
        mockVoltageProvider = context.mock(VoltageProvider.class);
    }
}
