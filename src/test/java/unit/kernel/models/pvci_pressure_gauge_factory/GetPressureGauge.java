package unit.kernel.models.pvci_pressure_gauge_factory;

import kernel.modbus.ModbusConnector;
import kernel.modbus.ModbusPortConfiguration;
import kernel.models.PVCiPressureGaugeFactory;
import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Contains unit tests for {@link PVCiPressureGaugeFactory#getPressureGauge()}
 */
public final class GetPressureGauge extends PVCiPressureGaugeFactoryTestCase {
    private ModbusConnector mockConnector = context.mock(
            ModbusConnector.class);

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
            oneOf(mockKernel).getModbusConnector();
            will(returnValue(mockConnector));

            oneOf(mockConnector).setPortConfiguration(
                    with(any(ModbusPortConfiguration.class))
            );
        }
    }
}
