package devices;

import exceptions.ResponseNotOKException;
import kernel.serial_ports.PortCommunicator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Contains methods for working with the power supply
 */
public class TDKLambdaPowerSupply extends AbstractRS232Device
        implements PowerSupply {

    /**
     * A lock for communicating with the device, locked when a response is
     * expected.
     */
    private static final Lock portCommunicatorLock = new ReentrantLock();

    /**
     * The application log for this class
     */
    private static final Logger log = LoggerFactory.getLogger(
            TDKLambdaPowerSupply.class
    );

    /**
     * The device address
     */
    private final int deviceAddress;

    /**
     *
     * Constructs an instance of the power supply, and places the device
     * into a state where it's capable of accepting remote commands. For the
     * TDK Lambda power supply, this means the "ADR" command must be sent to
     * the device with its matching address. This command must return an
     * "OK" response.
     *
     * @param portCommunicator A tool capable of communicating with the device
     * @param deviceAddress An integer representing the address of the
     *                      device on the given port
     * @throws IOException If the device cannot be initialized
     */
    public TDKLambdaPowerSupply(
            PortCommunicator portCommunicator,
            int deviceAddress
    ) throws IOException {
        super(portCommunicator);
        this.deviceAddress = deviceAddress;
        this.startDevice();
    }

    /**
     * @return The address of the device
     */
    @Override public int getDeviceAddress(){
        return this.deviceAddress;
    }

    /**
     * @return The voltage that the device is currently set to output
     * @throws IOException if the command to get this value cannot be written
     */
    @Override public Double getVoltage() throws IOException {
        return this.writeWithDoubleResponse(GET_VOLTAGE_COMMAND);
    }

    /**
     * @return The currently-measured voltage going out of the power supply
     * @throws IOException If the command cannot be written
     */
    @Override public Double getMeasuredVoltage() throws IOException {
        return this.writeWithDoubleResponse(GET_MEASURED_VOLTAGE_COMMAND);
    }

    /**
     * @param newVoltage The voltage to which the device output is to be set
     * @throws IOException If the command to set this voltage could not be
     * sent to the device
     */
    @Override public void setVoltage(double newVoltage) throws IOException {
        String commandToWrite = String.format(
            PowerSupply.SET_VOLTAGE_COMMAND, newVoltage
        );
        this.writeWithOKResponse(commandToWrite);
    }

    /**
     * Bring the device to a safe, known state.
     * @throws IOException If this cannot be done
     */
    @Override public void reset() throws IOException {
        this.writeWithOKResponse(RESET_COMMAND);
    }

    /**
     * @return The currently-set value of the current that the device is to
     * output
     * @throws IOException if the current value cannot be retrieved from the
     * device
     */
    @Override public Double getCurrent() throws IOException {
        return this.writeWithDoubleResponse(GET_CURRENT_COMMAND);
    }

    /**
     * @param newCurrent The current in Amperes, to set the device to
     * @throws IOException If the command to set the current cannot be sent
     */
    @Override public void setCurrent(double newCurrent) throws IOException {
        String commandToWrite = String.format(
            PowerSupply.SET_CURRENT_COMMAND, newCurrent
        );

        this.writeWithOKResponse(commandToWrite);
    }

    /**
     * Turn off the device output
     * @throws IOException If the command to turn the device off could not
     * be sent
     */
    @Override public void outputOff() throws IOException {
        String commandToWrite = String.format(
            PowerSupply.SET_OUTPUT_COMMAND, PowerSupply.OFF
        );

        this.writeWithOKResponse(commandToWrite);
    }

    /**
     * Turn the device output on
     * @throws IOException If the command to turn the power supply on could
     * not be sent
     */
    @Override public void outputOn() throws IOException {
        String command = String.format(
            PowerSupply.SET_OUTPUT_COMMAND, PowerSupply.ON
        );

        this.writeWithOKResponse(command);
    }

    /**
     * Initializes the device by sending the
     * {@link PowerSupply#GET_ADDRESS_COMMAND} with the address being
     * {@link TDKLambdaPowerSupply#deviceAddress}.
     *
     * @throws IOException If the command could not be sent
     */
    private void startDevice() throws IOException {
        String commandToWrite = String.format(GET_ADDRESS_COMMAND,
                deviceAddress);

        this.writeWithOKResponse(commandToWrite);
    }

    /**
     * Write a formatted command to the communicator's
     * {@link java.io.OutputStream}, then read the response.
     * The response is a double, so the method parses this number and
     * returns it.
     *
     * @param commandToWrite The command to be written to the device
     * @return The floating point number returned
     * @throws IOException If the command could not be sent
     */
    private Double writeWithDoubleResponse(String commandToWrite) throws
            IOException {
        portCommunicatorLock.lock();

        this.write(commandToWrite);
        log.debug("Writing command {} to power supply.", commandToWrite);
        String response = this.read();
        log.debug("Received response {} from power supply", response);

        portCommunicatorLock.unlock();

        return Double.parseDouble(response);
    }

    /**
     * Write a formatted command to the device, expecting a response of "OK".
     * @param commandToWrite The command to be written to the device
     * @throws IOException If the command could not be sent
     */
    private void writeWithOKResponse(String commandToWrite) throws
            IOException {
        portCommunicatorLock.lock();

        this.write(commandToWrite);
        log.debug(
                String.format(
                        "Writing command %s to power supply",
                        commandToWrite
                )
        );
        String response = this.read();
        log.debug(
                String.format(
                        "Received response %s from power supply",
                        response
                )
        );

        if (!response.equals(PowerSupply.OK_RESPONSE)){
            throw new ResponseNotOKException("Did not receive response of " +
                String.format("%s", PowerSupply.OK_RESPONSE));
        }

        portCommunicatorLock.unlock();
    }
}
