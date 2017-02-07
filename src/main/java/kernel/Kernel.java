package kernel;

import kernel.controllers.ConnectionRequestFactory;
import kernel.controllers.DeviceConnector;
import kernel.controllers.RS232PortConfigurationFactory;
import kernel.serial_ports.PortDriver;
import kernel.views.CommPortReporter;
import kernel.views.ConnectionRequest;
import kernel.views.DeviceReporter;

/**
 * Describes the main application kernel
 */
public interface Kernel {
    /**
     * @return A view that can list the available serial ports on this machine
     */
    CommPortReporter getCommPortReporter();

    /**
     * @return A view that lists the available devices that can be implemented
     */
    DeviceReporter getDeviceReporter();

    RS232PortConfigurationFactory getRS232PortConfigurationFactory();

    DeviceConnector getDeviceConnector();

    PortDriver getPortDriver();

    ConnectionRequestFactory getConnectionRequestFactory();
}
