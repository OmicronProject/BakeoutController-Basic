package kernel.models;

import devices.PowerSupply;
import devices.TDKLambdaPowerSupply;
import exceptions.DeviceAlreadyCreatedException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import kernel.serial_ports.PortConfiguration;
import kernel.serial_ports.SerialPort;
import kernel.views.DeviceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import kernel.Kernel;

import java.io.IOException;

/**
 * Implements a factory for making the power supply. An instance of the
 * power supply cannot legally exist without an open port with which to
 * communicate, and so this factory ensures that the power supply is made
 * correctly.
 */
public class TDKLambdaPowerSupplyFactory implements kernel.controllers.TDKLambdaPowerSupplyFactory {
    /**
     * The application kernel
     */
    @Autowired
    private Kernel kernel;

    /**
     * The name of the port that will be used to make the power supply
     */
    private String portName;

    /**
     * The address of the device. This parameter is required by the TDK
     * Lambda Power Supply. A valid ``ADR %d\r``, where ``%d`` is the
     * address of the device, must be sent to the device before it can start
     * receiving commands. This is a holdover from the fact that the TDK
     * Lambda power supply can connect on both RS232 and RS485 lines
     */
    private static final Integer deviceAddress = 6;

    /**
     * The application log
     */
    private static Logger log = LoggerFactory.getLogger(
            "kernel.models.TDKLambdaPowerSupplyFactory");

    /**
     * @return The kernel
     */
    @Override
    public Kernel getKernel(){
        return kernel;
    }

    /**
     * @param kernel The kernel to which this factory will be attached
     */
    @Override
    public void setKernel(Kernel kernel){
        this.kernel = kernel;
    }

    @Override
    public void setPortName(String portName){
        this.portName = portName;
    }

    @Override
    public PowerSupply getPowerSupply() throws PortInUseException,
            IOException, UnsupportedCommOperationException,
            DeviceAlreadyCreatedException {

        kernel.views.DeviceRegistry registry = kernel.getDeviceRegistryView();

        if (!registry.hasPowerSupply()){
            makePowerSupply();
        }
        PowerSupply supply = registry.getPowerSupply();
        writeEntryForSupply(supply);

        return supply;
    }

    @Override
    public void makePowerSupply() throws PortInUseException, IOException,
            UnsupportedCommOperationException, DeviceAlreadyCreatedException {
        assertPowerSupplyDoesNotExist();
        writeEntryForNoPowerSupply();

        kernel.controllers.DeviceRegistry registry = kernel
                .getDeviceRegistryController();

        PowerSupply supply = createPowerSupplyInstance();

        registry.setPowerSupply(supply);
    }

    private PowerSupply createPowerSupplyInstance() throws PortInUseException,
            IOException, UnsupportedCommOperationException
    {
        SerialPort port = kernel.getPortDriver().getPortByName(this.portName);

        configurePort(port);

        port.open();

        return new TDKLambdaPowerSupply(port.getCommunicator(), deviceAddress);
    }

    private void configurePort(SerialPort port){
        port.setConfig(new PortConfig());
    }

    private void assertPowerSupplyDoesNotExist() throws
            DeviceAlreadyCreatedException {
        DeviceRegistry registry = kernel.getDeviceRegistryView();
        if (registry.hasPowerSupply()){
            throw new DeviceAlreadyCreatedException(
                    "A power supply has already been made"
            );
        }
    }

    private static void writeEntryForNoPowerSupply(){
        log.debug("No Power Supply. Creating a new one");
    }

    private static void writeEntryForSupply(PowerSupply supply){
        log.debug(
                String.format("Found Power supply %s", supply.toString())
        );
    }

    private class PortConfig implements PortConfiguration {
        @Override
        public int getStopBits(){
            return STOPBITS_1;
        }

        @Override
        public int getBaudRate(){
            return BAUD_RATE_9600;
        }

        @Override
        public int getParityBits(){
            return PARITY_NONE;
        }

        @Override
        public int getDataBits(){
            return DATABITS_8;
        }
    }
}
