package kernel.modbus;

import kernel.serial_ports.PortConfiguration;
import net.wimpi.modbus.Modbus;
import net.wimpi.modbus.util.SerialParameters;

/**
 * A wrapper to configure serial ports using the MODBUS protocol
 */
public interface ModbusPortConfiguration extends PortConfiguration {

    String ASCII_ENCODING = Modbus.SERIAL_ENCODING_ASCII;

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

    SerialParameters getSerialParameters();

    void setBaudRate(int baudRate) throws IllegalArgumentException;

    void setDataBits(int dataBits);

    void setStopBits(int stopBits);

    void setParityBits(int parityBits);
}
