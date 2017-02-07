package kernel.controllers;

import exceptions.DeviceNotCreatedException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import kernel.views.ConnectionRequest;

/**
 * Factory for creating connections to devices
 */
public interface DeviceConnector {
    ConnectionRequest getConnectionRequest();
    void setConnectionRequest(ConnectionRequest request);

    DeviceRegistry getDeviceRegistry();
    void setDeviceRegistry(DeviceRegistry registry);
    Boolean canMakeConnections();

    void connect() throws DeviceNotCreatedException, PortInUseException,
            UnsupportedCommOperationException;
}
