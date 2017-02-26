package kernel.serial_ports;

import java.util.List;

/**
 * Lists serial ports, and allows for serial ports to be opened
 */
public interface PortDriver {

    /**
     * @return The name of serial ports available to this machine
     */
    List<String> getSerialPortNames();

    /**
     * @return The name to use when signing out serial ports
     */
    String getApplicationName();

    /**
     * @param portName The name of the port that needs to be retrieved. An
     *                 example of a valid name is "/dev/ttyUSB0"
     * @return An open serial port
     */
    SerialPort getPortByName(String portName);
}
