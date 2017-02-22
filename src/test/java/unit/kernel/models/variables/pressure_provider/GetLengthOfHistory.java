package unit.kernel.models.variables.pressure_provider;

import org.jetbrains.annotations.Contract;
import org.junit.Before;
import org.junit.Test;

import java.time.Duration;

import static org.junit.Assert.assertEquals;

/**
 * Created by mkononen on 22/02/17.
 */
public final class GetLengthOfHistory extends PressureProviderTestCase {
    private final Integer numberOfPoints = 10;
    private final Duration pollingInterval = Duration.ofMillis(1000);

    @Contract(" -> !null")
    @Override
    protected ExpectationsForPressureProvider getExpectations() throws
            Exception {
        return new ExpectationsForPressureProvider();
    }

    @Before
    public void configureProvider() throws Exception {
        provider.setNumberOfDataPoints(numberOfPoints);
        provider.setPollingInterval(pollingInterval);
    }

    @Test
    public void getLengthOfHistory(){
        assertEquals(
                pollingInterval.multipliedBy(numberOfPoints).toMillis(),
                provider.getLengthOfHistory().toMillis(),
                1e-6
        );
    }

}
