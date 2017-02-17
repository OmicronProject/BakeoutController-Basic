package unit.kernel.models.variables.pressure_provider;


import kernel.models.variables.PressureProvider;
import org.jetbrains.annotations.Contract;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Contains unit tests for {@link PressureProvider#isProvidingVariables()}
 */
public final class IsProvidingVariables extends PressureProviderTestCase {
    @Contract(" -> !null")
    @Override
    protected ExpectationsForPressureProvider getExpectations() throws
            Exception {
        return new ExpectationsForPressureProvider();
    }

    @Test
    public void isProvidingVariables(){
        assertNotNull(provider.isProvidingVariables());
    }
}
