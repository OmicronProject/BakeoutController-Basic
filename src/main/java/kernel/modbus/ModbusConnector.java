package kernel.modbus;

import exceptions.WrappedModbusException;
import net.wimpi.modbus.io.ModbusTransaction;
import net.wimpi.modbus.msg.ModbusRequest;

/**
 * Created by mkononen on 10/02/17.
 */
public interface ModbusConnector {
    ModbusPortConfiguration getPortConfiguration();

    void setPortConfiguration(ModbusPortConfiguration configuration);

    Boolean isPortOpen();

    ModbusTransaction getTransactionForRequest(ModbusRequest request) throws
            WrappedModbusException, IllegalStateException;

    void close();
}
