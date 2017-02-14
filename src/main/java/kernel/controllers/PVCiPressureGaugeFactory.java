package kernel.controllers;

import devices.PressureGauge;
import kernel.Kernel;

import java.io.IOException;

/**
 * Responsible for creating instances of {@link PressureGauge}
 */
public interface PVCiPressureGaugeFactory {

    /**
     * @return The application kernel to which this factory is attached.
     */
    Kernel getKernel();

    /**
     * @param kernel The kernel to which this factory is to be attached
     */
    void setKernel(Kernel kernel);

    /**
     * @return The address that will be passed into the device on instantiation
     */
    Integer getAddress();

    /**
     * @param address The address to which the device will be attached
     */
    void setAddress(Integer address);

    /**
     * @return The port at which the pressure gauge can be found
     */
    String getPortName();

    /**
     * @param portName The port where the device will be found.
     */
    void setPortName(String portName);

    /**
     * @return An instance of the pressure gauge
     * @throws IOException if the instance could not be created
     */
    PressureGauge getPressureGauge() throws IOException ;

    /**
     * Create a pressure gauge and write it to the device registry
     */
    void makePressureGauge() throws IOException ;
}
