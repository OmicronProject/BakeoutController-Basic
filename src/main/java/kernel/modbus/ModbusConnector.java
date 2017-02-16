package kernel.modbus;

import com.ghgande.j2mod.modbus.io.ModbusTransaction;
import com.ghgande.j2mod.modbus.msg.ModbusMessage;
import com.ghgande.j2mod.modbus.msg.ModbusRequest;
import exceptions.WrappedModbusException;

import java.io.IOException;

/**
 * Contains methods for working with modbus
 */
public interface ModbusConnector {
    /**
     * @return The current desired port configuration
     */
    ModbusPortConfiguration getPortConfiguration();

    /**
     * @param configuration The desired port configuration
     */
    void setPortConfiguration(ModbusPortConfiguration configuration);

    /**
     * @return True if the port managed by this connector, otherwise false.
     */
    Boolean isPortOpen();

    /**
     * @param request The request for which a transaction is required
     * @return The transaction that, when executed, will perform the desired
     * request
     * @throws WrappedModbusException If the transaction cannot be created
     * due to a MODBUS error
     * @throws IllegalStateException If the transaction cannot be created
     * due to a condition not being met. For instance, this should be thrown
     * if the transaction is attempted to be created without a valid connection
     */
    ModbusTransaction getTransactionForRequest(ModbusRequest request) throws
            WrappedModbusException, IllegalStateException;

    /**
     * With a response consisting of two 16-bit words, parse this response
     * into an IEEE 754 single-precision floating-point number
     * @param response The response received from the transaction
     * @return The data cast into a floating point number
     * @throws ClassCastException If the {@link ModbusMessage} cannot be
     * cast to a {@link com.ghgande.j2mod.modbus.msg.ModbusResponse}. This is a
     * required step in order to get the response's output stream
     * @throws IOException If the output stream from the response cannot be
     * parsed into a float
     */
    Float parseFloatFromResponse(ModbusMessage response) throws
            ClassCastException, IOException;

    String parseStringFromResponse(ModbusMessage response) throws
            ClassCastException, IOException;

    /**
     * Close the connection
     */
    void close();
}
