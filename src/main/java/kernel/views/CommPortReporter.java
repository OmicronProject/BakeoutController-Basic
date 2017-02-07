package kernel.views;

import java.util.List;

/**
 * Describes a reporter for available serial ports
 */
public interface CommPortReporter {
    /**
     * @return The list of serial ports accessible to this machine
     */
    List<String> getSerialPortNames();

    Boolean isPortInUse(String portName);
}
