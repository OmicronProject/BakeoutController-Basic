package unit.devices.pvci_pressure_gauge;

import devices.PVCiPressureGauge;
import net.wimpi.modbus.ModbusException;
import net.wimpi.modbus.io.BytesOutputStream;
import net.wimpi.modbus.io.ModbusTransaction;
import net.wimpi.modbus.msg.ModbusResponse;
import net.wimpi.modbus.msg.ReadInputRegistersRequest;
import net.wimpi.modbus.msg.ReadInputRegistersResponse;
import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;

/**
 * Contains unit tests for {@link PVCiPressureGauge#getPressure()}
 */
public final class GetPressure extends PVCiPressureGaugeTestCase {
    private ModbusTransaction mockTransaction = context.mock(
            ModbusTransaction.class
    );

    private ModbusResponse mockResponse = new ReadInputRegistersResponse();

    private Float expectedAnswer = 1.5f;

    @Before
    public void setup() throws Exception {
        setMockResponse();
        setContext();
    }

    private void setContext() throws Exception {
        context.checking(new ExpectationsForTest());
    }

    private void setMockResponse() throws Exception {
        DataOutput stream = new BytesOutputStream(4);
        stream.writeFloat(expectedAnswer);
        mockResponse.writeData(stream);
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
            expectationsForTransaction();
        }

        private void expectationsForModbusConnector() throws Exception {
            oneOf(mockModbusConnector).getTransactionForRequest(
                    with(any(ReadInputRegistersRequest.class))
            );
            will(returnValue(mockTransaction));

            oneOf(mockModbusConnector).parseFloatFromResponse(mockResponse);
            will(returnValue(expectedAnswer));
        }

        private void expectationsForTransaction() throws ModbusException {
            oneOf(mockTransaction).execute();
            oneOf(mockTransaction).getResponse();
            will(returnValue(mockResponse));
        }
    }
}
