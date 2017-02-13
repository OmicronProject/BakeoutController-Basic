package unit.kernel.models.pvci_pressure_gauge_factory;

import devices.PressureGauge;
import kernel.models.PVCiPressureGaugeFactory;
import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Contains unit tests for {@link PVCiPressureGaugeFactory#getPressureGauge()}
 */
public final class GetPressureGauge extends PVCiPressureGaugeFactoryTestCase {
    private PressureGauge mockPressureGauge = context.mock(
            PressureGauge.class
    );

    @Before
    public void setContext(){
        context.checking(new ExpectationsForTest());
    }

    @Test
    public void getPressureGauge(){
        assertNotNull(factory.getPressureGauge());
    }

    private class ExpectationsForTest extends Expectations {
        public ExpectationsForTest(){

            oneOf(mockKernel).getDeviceRegistryView();
            will(returnValue(mockRegistry));

            oneOf(mockRegistry).hasPressureGauge();
            will(returnValue(Boolean.TRUE));

            oneOf(mockRegistry).getPressureGauge();
            will(returnValue(mockPressureGauge));
        }
    }
}
