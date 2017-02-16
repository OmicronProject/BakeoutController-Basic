package unit.devices.pvci_pressure_gauge;

import com.ghgande.j2mod.modbus.msg.ReadWriteMultipleRequest;
import devices.PVCiPressureGauge;
import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Contains unit tests for {@link PVCiPressureGauge#getPressure()}
 */
public final class GetPressure extends PVCiPressureGaugeTestCase {

    private Float expectedAnswer = 1.5f;

    @Before
    public void setContext() throws Exception {
        context.checking(new ExpectationsForTest());
    }

    @Test
    public void getPressure() throws Exception {
        assertEquals(
                expectedAnswer,
                pressureGauge.getPressure()
        );
    }

    private class ExpectationsForTest extends Expectations {
        public ExpectationsForTest() throws Exception {
            expectationsForModbusConnector();
        }

        private void expectationsForModbusConnector() throws Exception {
            oneOf(mockModbusConnector).getTransactionForRequest(
                    with(any(ReadWriteMultipleRequest.class))
            );
            will(returnValue(mockTransaction));

            oneOf(mockModbusConnector).parseFloatFromResponse(mockResponse);
            will(returnValue(expectedAnswer));
        }
    }
}
