package unit.devices.pvci_pressure_gauge;

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
 * Created by mkononen on 13/02/17.
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
