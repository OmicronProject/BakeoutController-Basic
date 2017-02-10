package devices;

import exceptions.WrappedExceptionFromModbus;
import gnu.io.RXTXPort;
import net.wimpi.modbus.Modbus;
import net.wimpi.modbus.ModbusCoupler;
import net.wimpi.modbus.ModbusException;
import net.wimpi.modbus.io.ModbusSerialTransaction;
import net.wimpi.modbus.io.ModbusTransaction;
import net.wimpi.modbus.msg.ModbusRequest;
import net.wimpi.modbus.msg.ModbusResponse;
import net.wimpi.modbus.msg.ReadInputRegistersRequest;
import net.wimpi.modbus.net.SerialConnection;
import net.wimpi.modbus.util.SerialParameters;
import org.jetbrains.annotations.Contract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Contains an implementation of the PVCi IGC3 ion pressure gauge
 *
 * @implNote The serial port parameters are hard-coded for now. More
 * customization will come from a proper kernel, once that gets written.
 */
public class PVCiPressureGauge implements PressureGauge {

    /**
     * The application log to which entries about application state are to
     * be written
     */
    private static final Logger log = LoggerFactory.getLogger
            (PVCiPressureGauge.class);

    /**
     * The baud rate to be used for communicating with the gauge
     */
    private static final Integer baudRate = 19200;

    /**
     * The number of data bits to be used in communication
     */
    private static final Integer dataBits = RXTXPort.DATABITS_8;

    /**
     * The number of stop bits to be used in the connection
     */
    private static final Integer stopBits = RXTXPort.STOPBITS_1;

    /**
     * The number of parity bits to use
     * @implNote The MODBUS protocol already contains a CRC check, so
     * checking parity at the RS232 level is redundant, unless you're really
     * paranoid.
     */
    private static final Integer parityBits = RXTXPort.PARITY_NONE;

    /**
     * The address (0x9A in hexadecimal) where pressure information is stored
     */
    private static final Integer gaugePressureAddress = 154;

    /**
     * The size of the pressure register. The pressure is stored as a 32-bit
     * IEEE 754 floating point number. The MODBUS protocol uses 16 bit words.
     * Therefore, two words must be read from the device, starting at the
     * register address.
     */
    private static final Integer gaugePressureWordsTorRead = 2;

    /**
     * The name of the port at which the PVCi pressure gauge resides
     */
    private final String portName;

    /**
     * The device address
     */
    private final Integer address;

    /**
     * A thread that cleans up the serial connection. If a termination
     * signal is sent to this application, this thread is responsible for
     * closing the serial connection and freeing the port.
     */
    private PortShutdownThread shutdownThread;

    /**
     * The RS232 port over which the pressure gauge is connected to the device
     */
    private SerialConnection serialConnection;

    public PVCiPressureGauge(String portName, Integer address){
        this.portName = portName;
        this.address = address;

        ModbusCoupler.getReference().setUnitID(1);
        ModbusCoupler.getReference().setMaster(Boolean.TRUE);
    }

    @Override
    public Float getPressure() throws WrappedExceptionFromModbus,
            ModbusException {
        ReadInputRegistersRequest pressureRequest = getReadRegisterRequest(
                gaugePressureAddress, gaugePressureWordsTorRead
        );

        SerialConnection connection;

        try {
            connection = getSerialConnection();
        } catch (Exception connectionError) {
            throw new WrappedExceptionFromModbus(connectionError);
        }

        ModbusTransaction transaction = getModbusTransaction(
                pressureRequest, connection);

        transaction.execute();

        ModbusResponse response = transaction.getResponse();

        return Float.parseFloat(response.getHexMessage());
    }

    private SerialParameters getSerialParameters(){
        SerialParameters parameters = new SerialParameters();

        parameters.setPortName(portName);
        parameters.setBaudRate(baudRate);
        parameters.setDatabits(dataBits);
        parameters.setStopbits(stopBits);
        parameters.setParity(parityBits);
        parameters.setEncoding(Modbus.SERIAL_ENCODING_ASCII);

        return parameters;
    }

    @Contract(" -> !null")
    private SerialConnection buildSerialConnection(){
        return new SerialConnection(getSerialParameters());
    }

    private SerialConnection getSerialConnection() throws Exception {
        if (serialConnection == null) {
            serialConnection = buildSerialConnection();
        }

        if (!serialConnection.isOpen()) {
            serialConnection.open();
            shutdownThread = new PortShutdownThread(serialConnection);
            Runtime.getRuntime().addShutdownHook(shutdownThread);
        }

        return serialConnection;
    }

    private void closeSerialConnection(){
        serialConnection.close();
        Runtime.getRuntime().removeShutdownHook(shutdownThread);
    }

    @Contract("_, _ -> !null")
    private ReadInputRegistersRequest getReadRegisterRequest(
            Integer registerNumber, Integer numberofWordsToRead){
        ReadInputRegistersRequest request = new ReadInputRegistersRequest
                (registerNumber, numberofWordsToRead);
        request.setUnitID(address);
        request.setHeadless();

        return request;
    }

    private ModbusTransaction getModbusTransaction(
            ModbusRequest request, SerialConnection connection){
        ModbusTransaction transaction = new ModbusSerialTransaction
                (connection);

        transaction.setRequest(request);

        return transaction;
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
