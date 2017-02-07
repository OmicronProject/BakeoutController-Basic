package kernel.views;

import devices.RS232Device;

/**
 * Created by mkononen on 07/02/17.
 */
public interface DeviceRegistry {
    <DeviceType extends RS232Device> DeviceType getRS232Device(
        Class<DeviceType> deviceType
    );
}
