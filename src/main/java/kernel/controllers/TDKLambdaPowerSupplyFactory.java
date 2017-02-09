package kernel.controllers;

import devices.PowerSupply;
import exceptions.DeviceAlreadyCreatedException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import kernel.Kernel;

import java.io.IOException;

/**
 * Factory for making power supplies
 */
public interface TDKLambdaPowerSupplyFactory {
    /**
     * @return The application kernel to which this factory is attached
     */
    Kernel getKernel();

    /**
     * Attach this factory to a new kernel
     * @param kernel The kernel to which this factory will be attached
     */
    void setKernel(Kernel kernel);

    /**
     * @param portName The name of the serial port that is to be used when
     *                 making the power supply
     */
    void setPortName(String portName);

    /**
     * @return A power supply
     *
     * @throws PortInUseException If the serial port used to create this
     * device is occupied
     * @throws IOException If the serial port can be acquired, but
     * communication with the power supply cannot be established
     * @throws UnsupportedCommOperationException If the serial port can be
     * acquired, but cannot be configured for proper communication with the
     * power supply.
     */
    PowerSupply getPowerSupply() throws PortInUseException, IOException,
            UnsupportedCommOperationException, DeviceAlreadyCreatedException;

    /**
     * Create an instance of the power supply and write it to the device
     * registry.
     *
     * @throws PortInUseException If the serial port used to create this
     * device is occupied
     * @throws IOException If the serial port can be acquired, but
     * communication with the power supply cannot be established
     * @throws UnsupportedCommOperationException If the serial port can be
     * acquired, but cannot be configured for proper communication with the
     * power supply.
     */
    void makePowerSupply() throws PortInUseException, IOException,
            UnsupportedCommOperationException, DeviceAlreadyCreatedException;
}
