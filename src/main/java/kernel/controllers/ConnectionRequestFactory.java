package kernel.controllers;

import kernel.serial_ports.PortConfiguration;
import kernel.views.ConnectionRequest;
import kernel.views.DeviceListEntry;

/**
 * Created by mkononen on 07/02/17.
 */
public interface ConnectionRequestFactory {
    void setDeviceListEntry(DeviceListEntry entry);
    void setAddress(Integer address);
    void setPortName(String portName);
    void setPortConfiguration(PortConfiguration configuration);

    ConnectionRequest getConnectionRequest();
}
