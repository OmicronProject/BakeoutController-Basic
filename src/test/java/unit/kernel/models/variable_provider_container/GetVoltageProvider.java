package unit.kernel.models.variable_provider_container;

import kernel.views.VariableProviderContainer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Contains unit tests for
 * {@link VariableProviderContainer#getVoltageProvider()}
 */
public final class GetVoltageProvider extends
        VariableProviderContainerTestCase {
    @Before
    public void setUp(){
        providerRegistry.setVoltageProvider(mockVoltageProvider);
    }

    @Test
    public void getVoltageProvider(){
        assertEquals(
                mockVoltageProvider, providerRegistry.getVoltageProvider()
        );
    }
}
