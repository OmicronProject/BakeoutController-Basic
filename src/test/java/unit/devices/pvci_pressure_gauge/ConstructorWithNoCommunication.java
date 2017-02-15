package unit.devices.pvci_pressure_gauge;

import devices.PVCiPressureGauge;
import net.wimpi.modbus.msg.ModbusRequest;
import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Contains unit tests for {@link devices.PVCiPressureGauge} constructor,
 * where communication cannot be established
 */
public final class ConstructorWithNoCommunication extends
        PVCiPressureGaugeTestCase {

    @Before
    public void setContext() throws Exception {
        context.checking(new ExpectationsForTest());
    }

    @Test(expected = IOException.class)
    public void construct() throws IOException {
        new PVCiPressureGauge(address, mockModbusConnector);
    }


    private class ExpectationsForTest extends Expectations {
        public ExpectationsForTest() throws Exception {
            oneOf(mockModbusConnector).getTransactionForRequest(
                    with(any(ModbusRequest.class))
            );
            will(returnValue(mockTransaction));

            oneOf(mockTransaction).execute();

            oneOf(mockTransaction).getResponse();
            will(returnValue(mockResponse));

            oneOf(mockModbusConnector).parseStringFromResponse(mockResponse);
            will(returnValue(null));
        }
    }
}
