package kernel.modbus;

import com.ghgande.j2mod.modbus.io.ModbusSerialTransaction;
import com.ghgande.j2mod.modbus.io.ModbusTransaction;
import com.ghgande.j2mod.modbus.msg.ModbusMessage;
import com.ghgande.j2mod.modbus.msg.ModbusRequest;
import com.ghgande.j2mod.modbus.msg.ModbusResponse;
import com.ghgande.j2mod.modbus.net.SerialConnection;
import exceptions.WrappedModbusException;
import org.jetbrains.annotations.Contract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.DataInput;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.DataOutput;

/**
 * Manages connections to an RS232 port using MODBUS
 */
public class ModBusConnectionManager implements ModbusConnector {

    /**
     * The application log
     */
    private static final Logger log = LoggerFactory.getLogger(
            ModBusConnectionManager.class);

    /**
     * The configuration to be used when connecting to a MODBUS device over
     * RS232.
     */
    private ModbusPortConfiguration desiredPortConfiguration;

    /**
     * A thread responsible for closing the port if a shutdown request is sent
     */
    private PortShutdownThread portShutdownThread;

    /**
     * The serial connection to use
     */
    private SerialConnection connection;

    private static final Integer recieveTimeOut = 4000;

    private static final Integer numberOfRetries = 1;

    /**
     * @return The RS232 port config
     */
    @Override
    public ModbusPortConfiguration getPortConfiguration(){
        return this.desiredPortConfiguration;
    }

    /**
     * @param portConfiguration The desired port configuration
     */
    @Override
    public void setPortConfiguration(
            ModbusPortConfiguration portConfiguration
    ){
        desiredPortConfiguration = portConfiguration;
    }

    /**
     * @return {@link Boolean#TRUE} if the port is open, otherwise
     * {@link Boolean#FALSE}. If the connection is null,
     * it is assumed that the connection is not open.
     */
    @Override
    public Boolean isPortOpen(){
        if (connection == null){
            return Boolean.FALSE;
        } else {
            return connection.isOpen();
        }
    }

    /**
     * Closes the port and removes the shutdown thread
     */
    @Override
    public void close(){
        connection.close();
        removeShutdownThread();
    }

    /**
     * @param request A transaction which, when executed, will retrieve the
     *                value at a particular set of registers in the MODBUS
     *                device
     * @return The transaction
     * @throws WrappedModbusException If the connection cannot be opened.
     * @throws IllegalStateException If assertions like the device having a
     * connection, and having a port configuration, fail.
     */
    @Override
    public ModbusTransaction getTransactionForRequest(ModbusRequest request)
        throws WrappedModbusException, IllegalStateException {

        log.debug(
                "Creating transaction for request {}", request.getHexMessage()
        );

        if (!isPortOpen()){
            log.debug("Port {} is not open. Opening now", this);
            openConnection();
            log.debug("Port {} successfully opened", this);
        } else {
            log.debug("Port {} is open, using for connection", this);
        }
        connection.setTimeout(recieveTimeOut);

        ModbusSerialTransaction transaction = new ModbusSerialTransaction();
        transaction.setSerialConnection(connection);
        transaction.setRequest(request);
        transaction.setRetries(numberOfRetries);

        return transaction;
    }

    /**
     * Helper method to retrieve a float from a MODBUS response.
     *
     * @param response The response to be parsed
     * @return The 32-bit IEEE floating point number contained in the response
     * @throws ClassCastException If the message cannot be cast to a response
     * @throws IOException If the message cannot be read
     */
    @Override
    public Float parseFloatFromResponse(ModbusMessage response) throws
            ClassCastException, IOException {
        ModbusResponse inputRegistersResponse = (ModbusResponse) response;
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        DataOutput writer = new DataOutputStream(byteBuffer);
        log.debug(
                "Received response {}. Parsing to float",
                inputRegistersResponse.toString()
        );

        inputRegistersResponse.writeData(writer);

        byte[] dataToRead = processByteArray(byteBuffer.toByteArray());

        DataInput reader = new DataInputStream(
                new ByteArrayInputStream(dataToRead)
        );

        return reader.readFloat();

    }

    /**
     * Retrieve a string from the data package of the retrieved response
     * @param response The response from which a string must be retrieved
     * @return The string from the data package of the response
     * @throws ClassCastException If the {@link ModbusMessage} cannot be
     * cast to a {@link ModbusResponse}. This cast is required so that the
     * method {@link ModbusResponse#writeData(DataOutput)} can be used to
     * extract the response data package
     * @throws IOException if a string cannot be parsed from the response
     */
    @Override
    public String parseStringFromResponse(ModbusMessage response) throws
            ClassCastException, IOException {
        ModbusResponse inputRegistersResponse = (ModbusResponse) response;

        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        DataOutput writer = new DataOutputStream(byteBuffer);
        log.debug(
                "Received response {}. Parsing to string",
                inputRegistersResponse.toString()
        );

        inputRegistersResponse.writeData(writer);

        DataInput reader = new DataInputStream(
                new ByteArrayInputStream(byteBuffer.toByteArray())
        );

        return reader.readLine();
    }

    /**
     * Open the serial connection, and add the shutdown thread to the
     * runtime environment
     *
     * @throws IllegalStateException If assertions required to open the port
     * fail
     * @throws WrappedModbusException If opening the port throws an
     * {@link Exception}
     */
    private void openConnection() throws IllegalStateException,
            WrappedModbusException {
        assertPortClosed();
        assertHasPortConfiguration();

        connection = new SerialConnection(
                desiredPortConfiguration.getSerialParameters()
        );

        createShutdownThread();

        try {
            connection.open();
        } catch (Exception error){
            throw new WrappedModbusException(error);
        }
    }

    /**
     * @throws IllegalStateException if the port is not open
     */
    private void assertPortClosed() throws IllegalStateException {
        if (this.isPortOpen()){
            throw new IllegalStateException("MODBUS port is not open.");
        }
    }

    /**
     * @throws IllegalStateException if this manager has no desired port
     * configuration set.
     */
    private void assertHasPortConfiguration() throws IllegalStateException {
        if (desiredPortConfiguration == null){
            throw new IllegalStateException(
                    "Attempted to open a port without configuration being set"
            );
        }
    }

    /**
     * Construct the shutdown thread and add it to the list of shutdown
     * hooks that are executed when a termination signal is sent to the
     * application
     */
    private void createShutdownThread(){
        portShutdownThread = new PortShutdownThread(connection);
        Runtime.getRuntime().addShutdownHook(portShutdownThread);
    }

    /**
     * If the port is closed, remove this thread from the list of shutdown
     * hooks.
     */
    private void removeShutdownThread(){
        Runtime.getRuntime().removeShutdownHook(portShutdownThread);
    }

    /**
     * Perform a series of operations on the response data package in order
     * to extract some meaningful response out of it. These manipulations
     * are required due to the idiosyncracies of the PVCi IGC3 pressure
     * gauge, and not due to the MODBUS protocol itself.
     *
     * The first byte of the response data package contains an unsigned
     * short which states the number of bytes in the response. Remove this
     * byte from the response.
     *
     * The byte order of the message is also reversed, in that the byte in
     * which the mantissa of a float is to be located is transmitted first,
     * as opposed to being transmitted last. Reverse the byte order of the
     * bytes that remain in the stream
     *
     * @implNote Thank God it's only the byte orded and not the endianness
     * of the data that I need to worry about. The data is big endian, but
     * the bytes of the response are not loaded in the correct order.
     *
     * @param inputBytes The bytes to process
     * @return A processed byte array
     */
    @Contract(pure = true)
    private static byte[] processByteArray(byte[] inputBytes){
        byte[] outputBytes = new byte[inputBytes.length - 1];

        for (int index = 0; index < outputBytes.length; index++){
            outputBytes[index] = inputBytes[
                    inputBytes.length - 1 - index
            ];
        }

        return outputBytes;
    }

    /**
     * The thread to execute when the port is open. This thread catches a
     * shutdown signal, and closes the port.
     */
    private class PortShutdownThread extends Thread {
        /**
         * The thread's log
         */
        private final Logger log = LoggerFactory.getLogger
                (PortShutdownThread.class);

        /**
         * The connection to manage
         */
        private final SerialConnection connection;

        /**
         * @param connection The connection to manage
         */
        public PortShutdownThread(SerialConnection connection){
            this.connection = connection;
        }

        /**
         * Execute the thread, and log relevant parameters.
         */
        @Override
        public void run(){
            log.info("Connection {} caught shutdown signal. Closing",
                    connection.toString());
            connection.close();
            log.info("MODBUS Connection {} successfully closed",
                    connection.toString());
        }
    }
}
