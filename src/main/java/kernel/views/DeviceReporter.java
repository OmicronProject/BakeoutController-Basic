package kernel.views;

import java.util.List;

/**
 * Returns a list of implemented devices
 */
public interface DeviceReporter {
    /**
     * @return A list of devices that require use of an RS232 port
     */
    List<DeviceListEntry> getRS232Devices();
}
