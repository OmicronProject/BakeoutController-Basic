package unit.devices.pvci_pressure_gauge;

import com.ghgande.j2mod.modbus.io.ModbusSerialTransaction;
import com.ghgande.j2mod.modbus.io.ModbusTransaction;
import com.ghgande.j2mod.modbus.msg.ModbusMessage;
import com.ghgande.j2mod.modbus.msg.ModbusRequest;
import com.ghgande.j2mod.modbus.msg.ModbusResponse;
import com.ghgande.j2mod.modbus.msg.ReadMultipleRegistersResponse;
import devices.PVCiPressureGauge;
import devices.PressureGauge;
import kernel.modbus.ModbusConnector;
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

    protected final ModbusTransaction mockTransaction = new MockTransaction();

    protected final ModbusResponse mockResponse = new
            ReadMultipleRegistersResponse();

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

    private class MockTransaction extends ModbusSerialTransaction {
        @Override
        public void execute(){
            // Don't actually run anything
        }

        @Override
        public ModbusResponse getResponse(){
            return mockResponse;
        }
    }

    private class ExpectationsForConstruction extends Expectations {
        public ExpectationsForConstruction() throws Exception {
            oneOf(mockModbusConnector).getTransactionForRequest(
                    with(any(ModbusRequest.class))
            );
            will(returnValue(mockTransaction));

            oneOf(mockModbusConnector).parseStringFromResponse(
                    with(any(ModbusResponse.class))
            );
            will(returnValue("IGC3"));
        }
    }
}
