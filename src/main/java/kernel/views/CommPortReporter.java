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

    /**
     * @param portName The name of the port for which the check is to be made
     * @return {@link Boolean#TRUE} if the port is in use, otherwise
     * {@link Boolean#FALSE}
     */
    Boolean isPortInUse(String portName);
}
