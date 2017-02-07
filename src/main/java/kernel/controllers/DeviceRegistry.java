package kernel.controllers;

import devices.RS232Device;

/**
 * Created by mkononen on 03/02/17.
 */
public interface DeviceRegistry {
    void registerRS232Device(RS232Device device);
}
