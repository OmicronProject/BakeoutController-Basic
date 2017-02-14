package kernel.modbus;


import exceptions.WrappedModbusException;
import net.wimpi.modbus.io.ModbusSerialTransaction;
import net.wimpi.modbus.io.ModbusTransaction;
import net.wimpi.modbus.msg.ModbusMessage;
import net.wimpi.modbus.msg.ModbusRequest;
import net.wimpi.modbus.msg.ModbusResponse;
import net.wimpi.modbus.net.SerialConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;


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
     * @return True if the port is open, otherwise false. If the connection
     * is null, it is assumed that the connection is not open.
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
        if (!isPortOpen()){
            openConnection();
        }

        ModbusSerialTransaction transaction = new ModbusSerialTransaction();
        transaction.setSerialConnection(connection);
        transaction.setRequest(request);

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

        DataInput reader = new DataInputStream(
                new ByteArrayInputStream(byteBuffer.toByteArray())
        );

        return reader.readFloat();

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
        if (!this.isPortOpen()){
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
