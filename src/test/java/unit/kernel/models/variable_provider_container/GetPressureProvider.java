package unit.kernel.models.variable_provider_container;

import kernel.models.VariableProviderContainer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Contains unit tests for
 * {@link VariableProviderContainer#getPressureProvider()}
 */
public final class GetPressureProvider extends
        VariableProviderContainerTestCase {
    @Before
    public void setUp(){
        providerRegistry.setPressureProvider(mockPressureProvider);
    }

    @Test
    public void getPressureProvider(){
        assertEquals(
                mockPressureProvider, providerRegistry.getPressureProvider()
        );
    }
}
