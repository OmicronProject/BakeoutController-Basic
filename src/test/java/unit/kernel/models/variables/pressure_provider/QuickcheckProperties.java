package unit.kernel.models.variables.pressure_provider;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import exceptions.NonNegativeDurationException;
import org.jetbrains.annotations.Contract;
import org.junit.runner.RunWith;

import java.time.Duration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeTrue;

/**
 * Created by mkononen on 22/02/17.
 */
@RunWith(JUnitQuickcheck.class)
public final class QuickcheckProperties extends PressureProviderTestCase {
    @Contract(" -> !null")
    @Override
    protected ExpectationsForPressureProvider getExpectations() throws
            Exception {
        return new ExpectationsForPressureProvider();
    }

    @Property
    public void numberOfDataPoints(Integer numberOfDataPoints){
        provider.setNumberOfDataPoints(numberOfDataPoints);

        assertEquals(numberOfDataPoints, provider.getNumberOfDataPoints());
    }

    @Property
    public void pollingInterval(Duration pollingInterval){
        assumeTrue(!pollingInterval.isNegative());

        try {
            provider.setPollingInterval(pollingInterval);
        } catch (NonNegativeDurationException error){
            fail();
        }

        assertEquals(pollingInterval, provider.getPollingInterval());
    }
}
