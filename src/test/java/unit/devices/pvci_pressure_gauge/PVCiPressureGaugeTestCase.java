package unit.devices.pvci_pressure_gauge;

import devices.PVCiPressureGauge;
import devices.PressureGauge;
import kernel.modbus.ModbusConnector;
import net.wimpi.modbus.io.ModbusTransaction;
import net.wimpi.modbus.msg.ModbusMessage;
import net.wimpi.modbus.msg.ModbusRequest;
import net.wimpi.modbus.msg.ReadInputRegistersResponse;
import org.jmock.Expectations;
import org.junit.Before;
import unit.devices.DevicesTestCase;

/**
 * Base class for unit tests of {@link devices.PVCiPressureGauge}
 */
public abstract class PVCiPressureGaugeTestCase extends DevicesTestCase {
    protected final ModbusConnector mockModbusConnector = context.mock(
            ModbusConnector.class
    );

    protected final ModbusTransaction mockTransaction = context.mock(
            ModbusTransaction.class
    );

    protected final ModbusMessage mockResponse = new
            ReadInputRegistersResponse();

    protected static final Integer address = 11;

    protected PressureGauge pressureGauge;

    @Before
    public void createPressureGauge() throws Exception {
        setContext();
        pressureGauge = new PVCiPressureGauge(address, mockModbusConnector);
    }

    private void setContext() throws Exception{
        context.checking(new ExpectationsForConstruction());
    }

    private class ExpectationsForConstruction extends Expectations {
        public ExpectationsForConstruction() throws Exception {
            oneOf(mockModbusConnector).getTransactionForRequest(
                    with(any(ModbusRequest.class))
            );
            will(returnValue(mockTransaction));

            oneOf(mockTransaction).execute();

            oneOf(mockTransaction).getResponse();
            will(returnValue(mockResponse));

            oneOf(mockModbusConnector).parseStringFromResponse(mockResponse);
            will(returnValue("IGC3"));
        }
    }
}
