package kernel.controllers;

/**
 * Contains methods for controlling the TDK Lambda Power supply
 */
public interface VoltageController {
    /**
     * @param newVoltage The voltage that the Power supply should output
     */
    void setVoltage(Double newVoltage);

    /**
     * Turn the device on
     */
    void turnOn();

    /**
     * Turn the device off
     */
    void turnOff();
}
