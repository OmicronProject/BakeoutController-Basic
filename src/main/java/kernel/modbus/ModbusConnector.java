package kernel.modbus;

import exceptions.WrappedModbusException;
import net.wimpi.modbus.io.ModbusTransaction;
import net.wimpi.modbus.msg.ModbusMessage;
import net.wimpi.modbus.msg.ModbusRequest;

import java.io.IOException;

/**
 * Created by mkononen on 10/02/17.
 */
public interface ModbusConnector {
    ModbusPortConfiguration getPortConfiguration();

    void setPortConfiguration(ModbusPortConfiguration configuration);

    Boolean isPortOpen();

    ModbusTransaction getTransactionForRequest(ModbusRequest request) throws
            WrappedModbusException, IllegalStateException;

    Float parseFloatFromResponse(ModbusMessage response) throws
            ClassCastException, IOException;

    void close();
}
