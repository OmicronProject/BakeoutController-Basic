package unit.kernel.models.variables.pressure_provider;

import kernel.models.variables.PressureProvider;
import org.jetbrains.annotations.Contract;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Contains unit tests for {@link PressureProvider#getValues()}
 */
public final class GetValues extends PressureProviderTestCase {
    @Contract(" -> !null")
    @Override
    protected ExpectationsForPressureProvider getExpectations() throws
            Exception {
        return new ExpectationsForPressureProvider();
    }

    @Test
    public void getValues(){
        assertNotNull(provider.getValues());
    }
}
