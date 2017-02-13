package unit.devices.pvci_pressure_gauge;

import devices.PVCiPressureGauge;
import devices.PressureGauge;
import kernel.modbus.ModbusConnector;
import org.junit.Before;
import unit.devices.DevicesTestCase;

/**
 * Base class for unit tests of {@link devices.PVCiPressureGauge}
 */
public abstract class PVCiPressureGaugeTestCase extends DevicesTestCase {
    protected final ModbusConnector mockModbusConnector = context.mock(
            ModbusConnector.class
    );

    protected static final Integer address = 11;

    protected PressureGauge pressureGauge;

    @Before
    public void createPressureGauge(){
        pressureGauge = new PVCiPressureGauge(address, mockModbusConnector);
    }
}
