package kernel.models;

import devices.PowerSupply;
import devices.TDKLambdaPowerSupply;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import kernel.serial_ports.PortConfiguration;
import kernel.serial_ports.SerialPort;
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
    @Autowired
    private Kernel kernel;

    private String portName;
    private static final Integer deviceAddress = 6;

    private static Logger logger = LoggerFactory.getLogger(
            "kernel.models.TDKLambdaPowerSupplyFactory");

    @Override
    public Kernel getKernel(){
        return kernel;
    }

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
            IOException {
        PowerSupply powerSupply;

        kernel.views.DeviceRegistry registry = kernel.getDeviceRegistryView();

        if (!registry.hasPowerSupply()){
            writeEntryForNoPowerSupply();

            kernel.controllers.DeviceRegistry registryController = kernel
                    .getDeviceRegistryController();
            powerSupply = createPowerSupply();
            registryController.setPowerSupply(powerSupply);
        } else {
            powerSupply = registry.getPowerSupply();
            writeEntryForSupply(powerSupply);
        }

        return powerSupply;
    }

    private PowerSupply createPowerSupply() throws PortInUseException,
            IOException
    {
        SerialPort port = kernel.getPortDriver().getPortByName(this.portName);

        configurePort(port);

        port.open();

        return new TDKLambdaPowerSupply(port.getCommunicator(), deviceAddress);
    }

    private void configurePort(SerialPort port){
        try {
            port.setConfig(new PortConfig());
        } catch (UnsupportedCommOperationException error){
            error.printStackTrace();
        }
    }

    private static void writeEntryForNoPowerSupply(){
        logger.debug("No Power Supply. Creating a new one");
    }

    private static void writeEntryForSupply(PowerSupply supply){
        logger.debug(
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
