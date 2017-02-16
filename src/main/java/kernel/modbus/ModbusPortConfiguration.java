package kernel.modbus;

import com.ghgande.j2mod.modbus.Modbus;
import com.ghgande.j2mod.modbus.util.SerialParameters;
import kernel.serial_ports.PortConfiguration;

/**
 * A wrapper to configure serial ports using the MODBUS protocol
 */
public interface ModbusPortConfiguration extends PortConfiguration {

    String ASCII_ENCODING = Modbus.SERIAL_ENCODING_ASCII;

    String RTU_ENCODING = Modbus.SERIAL_ENCODING_RTU;

    int BAUD_RATE_19200 = 19200;

    /**
     * @return the encoding used
     */
    String getEncoding();

    /**
     * @param encoding The string encoding to use
     */
    void setEncoding(String encoding);

    /**
     * @return The name of the port to use
     */
    String getPortName();

    /**
     * @param portName The port name to use
     */
    void setPortName(String portName);

    /**
     * @return An instance of {@link SerialParameters} that MODBUS can then
     * parse in order to establish a connection.
     */
    SerialParameters getSerialParameters();

    /**
     * @param baudRate The desired Baud rate
     * @throws IllegalArgumentException If the baud rate is less than 0, or
     * some un-allowed value
     */
    void setBaudRate(int baudRate) throws IllegalArgumentException;

    /**
     * @param dataBits The data bits in an RS232 frame
     */
    void setDataBits(int dataBits);

    /**
     * @param stopBits The number of stop bits in the RS232 connection
     */
    void setStopBits(int stopBits);

    /**
     * @param parityBits The number of parity bits
     */
    void setParityBits(int parityBits);
}
