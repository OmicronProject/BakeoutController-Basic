package devices.factories;

import devices.RS232Device;
import exceptions.DeviceNotCreatedException;
import kernel.serial_ports.SerialPort;

/**
 * Created by mkononen on 06/02/17.
 */
public interface DeviceFactory<DeviceType extends RS232Device> {
    DeviceType getDevice() throws DeviceNotCreatedException;

    SerialPort getSerialPort();

    void setSerialPort(SerialPort port);

    Integer getAddress();

    void setAddress(Integer address);
}
