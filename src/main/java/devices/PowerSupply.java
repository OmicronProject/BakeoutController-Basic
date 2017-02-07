package devices;

import java.io.IOException;

/**
 * Describes all methods that the power supply implements
 */
public interface PowerSupply extends RS232Device {

    /**
     * Command to send to the power supply to specify the address of the
     * device. The format parameter is the address of the device to use.
     *
     * @apiNote This command MUST be sent to the power supply with the
     * address matching that of the device address before the supply will
     * accept remote commands
     */
    String GET_ADDRESS_COMMAND = "ADR %d\r";

    /**
     * Command to return the voltage that is set on the device
     */
    String GET_VOLTAGE_COMMAND = "PV?\r";

    /**
     * Command to set the voltage to a specific amount. The format parameter
     * is a float representing the value of the voltage to be set
     */
    String SET_VOLTAGE_COMMAND = "PV %.3f\r";

    /**
     * Command to return the current that the Power supply has been set to
     */
    String GET_CURRENT_COMMAND = "PC?\r";

    /**
     * Command to set the current to a desired value. The format parameter
     * is the current that the value should be set to.
     */
    String SET_CURRENT_COMMAND = "PC %.3f\r";

    /**
     * Command to set the output mode to a specific value. The format parameter
     * should be either {@link PowerSupply#OFF} or {@link PowerSupply#ON}, to
     * indicate the desired output status. An output status of
     * {@link PowerSupply#ON} indicates that power is flowing out of the
     * power supply.
     */
    String SET_OUTPUT_COMMAND = "OUT %s\r";

    /**
     * Command to bring the device to a known state.
     */
    String RESET_COMMAND = "RST\r";

    /**
     * Command returned by the device to indicate that a command has been
     * processed successfully.
     */
    String OK_RESPONSE = "OK";

    /**
     * Represents the device output mode being off
     */
    String OFF = "0";

    /**
     * Represents the device output mode being on
     */
    String ON = "1";

    /**
     * Bring the device to a known safe state
     * @throws IOException if the command could not be sent
     */
    void reset() throws IOException;

    /**
     * @return The voltage value that the supply is set to output, in Volts.
     * @throws IOException if the command could not be sent
     */
    Double getVoltage() throws IOException;

    /**
     * @param newVoltage The voltage to which the device output is to be set
     * @throws IOException If the command could not be sent to the device
     */
    void setVoltage(double newVoltage) throws IOException;

    /**
     * @return The current in Amperes that the power supply is set to output.
     * @throws IOException If the command could not be sent to the device
     */
    Double getCurrent() throws IOException;

    /**
     * @param newCurrent The current in Amperes, to set the device to
     * @throws IOException If the current could not be set
     */
    void setCurrent(double newCurrent) throws IOException;

    /**
     * @return The address that this device is mapped to
     */
    int getDeviceAddress();

    /**
     * Turn the output on
     * @throws IOException If the command could not be sent to the device
     */
    void outputOn() throws IOException;

    /**
     * Turn the output off
     * @throws IOException If the command could not be sent
     */
    void outputOff() throws IOException;
}
