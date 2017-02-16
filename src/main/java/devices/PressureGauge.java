package devices;

import com.ghgande.j2mod.modbus.ModbusException;
import exceptions.WrappedModbusException;

import java.io.IOException;

/**
 * Describes the methods implemented by the PVCi IGC3 pressure gauge. For
 * now, the only functionality available is to get the pressure
 */
public interface PressureGauge extends RS232Device {
    /**
     *
     * @return The measured pressure in millibars
     * @throws WrappedModbusException If the Modbus Library throws a general
     * exception.
     * @throws ModbusException If there is an error in the library that
     * causes ModbusException to be thrown explicitly.
     * @throws IOException if there is an error with parsing the output
     * response
     */
    Float getPressure() throws WrappedModbusException, ModbusException,
            IOException;
}
