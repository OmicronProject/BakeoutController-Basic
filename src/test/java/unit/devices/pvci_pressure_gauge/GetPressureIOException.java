package unit.devices.pvci_pressure_gauge;

import devices.PVCiPressureGauge;
import exceptions.WrappedModbusException;
import net.wimpi.modbus.io.ModbusTransaction;
import net.wimpi.modbus.msg.ModbusResponse;
import net.wimpi.modbus.msg.ReadInputRegistersRequest;
import net.wimpi.modbus.msg.ReadInputRegistersResponse;
import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

/**
 * Contains unit tests for {@link PVCiPressureGauge#getPressure()} where an
 * {@link IOException is thrown}. Checks that the {@link IOException} is
 * wrapped into a {@link WrappedModbusException}
 */
public class GetPressureIOException extends PVCiPressureGaugeTestCase {
    private ModbusTransaction mockTransaction = context.mock(
            ModbusTransaction.class
    );

    private ModbusResponse mockResponse = new ReadInputRegistersResponse();

    @Before
    public void setContext() throws Exception {
        context.checking(new ExpectationsForTest());

    }

    @Test(expected = WrappedModbusException.class)
    public void getPressure() throws Exception {
        pressureGauge.getPressure();
    }

    private class ExpectationsForTest extends Expectations {

        public ExpectationsForTest() throws Exception {
            oneOf(mockModbusConnector).getTransactionForRequest(
                    with(any(ReadInputRegistersRequest.class))
            );
            will(returnValue(mockTransaction));

            oneOf(mockModbusConnector).parseFloatFromResponse(mockResponse);
            will(throwException(new IOException()));

            oneOf(mockTransaction).execute();
            oneOf(mockTransaction).getResponse();
            will(returnValue(mockResponse));
        }
    }
}
