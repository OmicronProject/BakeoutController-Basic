package devices;

import exceptions.WrappedModbusException;
import net.wimpi.modbus.ModbusException;

/**
 * Describes working with a pressure gauge
 */
public interface PressureGauge extends RS232Device {
    Float getPressure() throws WrappedModbusException, ModbusException;
}
