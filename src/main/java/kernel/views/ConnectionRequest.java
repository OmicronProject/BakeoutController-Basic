package kernel.views;

import kernel.serial_ports.PortConfiguration;

/**
 * Created by mkononen on 07/02/17.
 */
public interface ConnectionRequest {
    DeviceListEntry getDeviceListEntry();

    Integer getAddress();

    String getPortName();

    PortConfiguration getPortConfiguration();
}
