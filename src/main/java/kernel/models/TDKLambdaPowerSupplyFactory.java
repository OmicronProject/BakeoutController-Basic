package kernel.models;

import devices.PowerSupply;
import devices.TDKLambdaPowerSupply;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import kernel.serial_ports.PortConfiguration;
import kernel.serial_ports.SerialPort;
import kernel.views.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * Created by mkononen on 07/02/17.
 */
public class TDKLambdaPowerSupplyFactory implements kernel.controllers.TDKLambdaPowerSupplyFactory {
    @Autowired
    private Kernel kernel;

    private String portName;
    private static final Integer deviceAddress = 6;

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
            kernel.controllers.DeviceRegistry registryController = kernel
                    .getDeviceRegistryController();
            powerSupply = createPowerSupply();
            registryController.setPowerSupply(powerSupply);
        } else {
            powerSupply = registry.getPowerSupply();
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
