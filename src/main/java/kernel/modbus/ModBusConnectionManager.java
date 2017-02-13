package kernel.modbus;


import exceptions.WrappedModbusException;
import net.wimpi.modbus.io.ModbusSerialTransaction;
import net.wimpi.modbus.io.ModbusTransaction;
import net.wimpi.modbus.msg.ModbusRequest;
import net.wimpi.modbus.net.SerialConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Manages connections to an RS232 port using MODBUS
 */
public class ModBusConnectionManager implements ModbusConnector {

    private ModbusPortConfiguration desiredPortConfiguration;

    private PortShutdownThread portShutdownThread;

    private SerialConnection connection;

    @Override
    public ModbusPortConfiguration getPortConfiguration(){
        return this.desiredPortConfiguration;
    }

    @Override
    public void setPortConfiguration(
            ModbusPortConfiguration portConfiguration
    ){
        desiredPortConfiguration = portConfiguration;
    }

    @Override
    public Boolean isPortOpen(){
        if (connection == null){
            return Boolean.FALSE;
        } else {
            return connection.isOpen();
        }
    }

    @Override
    public void close(){
        connection.close();
        removeShutdownThread();
    }

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


    private void assertPortClosed() throws IllegalStateException {
        if (!this.isPortOpen()){
            throw new IllegalStateException("MODBUS port is not open.");
        }
    }

    private void assertHasPortConfiguration(){
        if (desiredPortConfiguration == null){
            throw new IllegalStateException(
                    "Attempted to open a port without configuration being set"
            );
        }
    }

    private void createShutdownThread(){
        portShutdownThread = new PortShutdownThread(connection);
        Runtime.getRuntime().addShutdownHook(portShutdownThread);
    }

    private void removeShutdownThread(){
        Runtime.getRuntime().removeShutdownHook(portShutdownThread);
    }

    private class PortShutdownThread extends Thread {
        private final Logger log = LoggerFactory.getLogger
                (PortShutdownThread.class);

        private final SerialConnection connection;

        public PortShutdownThread(SerialConnection connection){
            this.connection = connection;
        }

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
