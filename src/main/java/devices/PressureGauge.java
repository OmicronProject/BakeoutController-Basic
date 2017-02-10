package devices;

import exceptions.WrappedExceptionFromModbus;
import net.wimpi.modbus.ModbusException;

/**
 * Describes working with a pressure gauge
 */
public interface PressureGauge extends RS232Device {
    Float getPressure() throws WrappedExceptionFromModbus, ModbusException;
}
