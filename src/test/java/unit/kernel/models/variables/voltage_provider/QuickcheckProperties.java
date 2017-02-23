package unit.kernel.models.variables.voltage_provider;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import exceptions.NonNegativeDurationException;
import org.jetbrains.annotations.Contract;
import org.junit.Assume;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import java.time.Duration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Contains generative tests for {@link kernel.models.variables.VoltageProvider}
 */
@RunWith(JUnitQuickcheck.class)
public final class QuickcheckProperties extends VoltageProviderTestCase {

    @Contract(" -> !null")
    @Override
    protected ExpectationsForTest getExpectations(){
        return new ExpectationsForTest();
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Property
    public void numberOfDataPoints(Integer dataPoints){
        provider.setNumberOfDataPoints(dataPoints);
        assertEquals(dataPoints, provider.getNumberOfDataPoints());
    }

    @Property
    public void pollingIntervalBiggerThanZero(Duration pollingInterval){
        Assume.assumeFalse(pollingInterval.isNegative());

        try {
            provider.setPollingInterval(pollingInterval);
        } catch (NonNegativeDurationException error){
            fail();
        }

        assertEquals(pollingInterval, provider.getPollingInterval());
    }

    @Property
    public void pollingIntervalLessThanZero(Duration pollingInterval) throws
            NonNegativeDurationException {
        Assume.assumeTrue(pollingInterval.isNegative());

        thrown.expect(NonNegativeDurationException.class);
        provider.setPollingInterval(pollingInterval);
    }
}
