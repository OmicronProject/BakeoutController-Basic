package kernel.models;

import kernel.controllers.TDKLambdaPowerSupplyFactory;
import kernel.serial_ports.PortDriver;
import kernel.serial_ports.SerialPort;
import kernel.views.CommPortReporter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Contains methods for working with application hardware, getting the
 * necessary views and controllers for working with the UI
 */
public final class Kernel implements kernel.Kernel, CommPortReporter {

    /**
     * The driver responsible for managing serial ports
     */
    private PortDriver portDriver;

    private DeviceRegistry deviceRegistry;

    private kernel.controllers.TDKLambdaPowerSupplyFactory
            tdkLambdaPowerSupplyFactory;

    /**
     * @param portDriver The driver to be used for managing the RS232 serial
     *                   port
     */
    public Kernel(PortDriver portDriver){
        this.portDriver = portDriver;
        this.deviceRegistry = new DeviceRegistry();
        createTDKLambdaPowerSupplyFactory();
    }

    /**
     * @return An implementation of {@link CommPortReporter} that can report
     * serial port names
     */
    @Contract(pure = true)
    @Override public CommPortReporter getCommPortReporter(){
        return this;
    }

    /**
     * @return The names of serial ports available on this machine
     */
    @Override public List<String> getSerialPortNames(){
        return this.portDriver.getSerialPortNames();
    }

    /**
     * @param portName The name of the port to be determined whether it is
     *                 open or not
     * @return True if the port is in use, otherwise false
     */
    @NotNull
    @Override public Boolean isPortInUse(String portName){
        SerialPort port = portDriver.getPortByName(portName);
        return port.isPortOpen();
    }

    @Override
    public PortDriver getPortDriver(){
        return this.portDriver;
    }

    @Contract(pure = true)
    @Override
    public kernel.controllers.DeviceRegistry getDeviceRegistryController(){
        return this.deviceRegistry;
    }

    @Contract(pure = true)
    @Override
    public kernel.views.DeviceRegistry getDeviceRegistryView(){
        return this.deviceRegistry;
    }

    @Override
    public TDKLambdaPowerSupplyFactory getPowerSupplyFactory(){
        return this.tdkLambdaPowerSupplyFactory;
    }

    private void createTDKLambdaPowerSupplyFactory(){
        this.tdkLambdaPowerSupplyFactory = new kernel.models
                .TDKLambdaPowerSupplyFactory();
        this.tdkLambdaPowerSupplyFactory.setKernel(this);
    }
}
