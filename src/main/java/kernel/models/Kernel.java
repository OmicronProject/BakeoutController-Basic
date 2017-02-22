package kernel.models;

import kernel.controllers.TDKLambdaPowerSupplyFactory;
import kernel.controllers.TaskRunner;
import kernel.controllers.variables.VariableProviderRegistry;
import kernel.modbus.ModBusConnectionManager;
import kernel.modbus.ModbusConnector;
import kernel.serial_ports.PortDriver;
import kernel.serial_ports.SerialPort;
import kernel.views.CommPortReporter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

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

    private kernel.models.VariableProviderRegistry variableProviders;

    private kernel.controllers.TDKLambdaPowerSupplyFactory
            tdkLambdaPowerSupplyFactory;

    private static final Logger log = LoggerFactory.getLogger(Kernel.class);

    private kernel.controllers.PVCiPressureGaugeFactory
            pvCiPressureGaugeFactory;

    private TaskRunner runner = new kernel.models.TaskRunner();

    /**
     * @param portDriver The driver to be used for managing the RS232 serial
     *                   port
     */
    public Kernel(PortDriver portDriver){
        this.portDriver = portDriver;
        this.deviceRegistry = new DeviceRegistry();
        createTDKLambdaPowerSupplyFactory();

        log.info("Started kernel {}", this.toString());

        createPVCIPressureGaugeFactory();
        createVariableProviderRegistry();
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
        List<String> portNames = this.portDriver.getSerialPortNames();
        handleLogging(portNames);
        return portNames;
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

    @Contract(" -> !null")
    @Override
    public ModbusConnector getModbusConnector(){
        return new ModBusConnectionManager();
    }

    @Override
    public kernel.controllers.PVCiPressureGaugeFactory
    getPressureGaugeFactory(){
        return this.pvCiPressureGaugeFactory;
    }

    @Contract(pure = true)
    @Override
    public kernel.views.VariableProviderRegistry getVariableProvidersView(){
        return this.variableProviders;
    }

    @Contract(pure = true)
    @Override
    public VariableProviderRegistry
            getVariableProvidersController(){
        return this.variableProviders;
    }

    @Override
    public TaskRunner getTaskRunner(){
        return this.runner;
    }

    @Override
    public void setTaskRunner(TaskRunner runner){
        this.runner = runner;
    }

    private void createTDKLambdaPowerSupplyFactory(){
        this.tdkLambdaPowerSupplyFactory = new kernel.models
                .TDKLambdaPowerSupplyFactory();
        this.tdkLambdaPowerSupplyFactory.setKernel(this);
    }

    private void createPVCIPressureGaugeFactory(){
        this.pvCiPressureGaugeFactory = new PVCiPressureGaugeFactory();
        this.pvCiPressureGaugeFactory.setKernel(this);
    }

    private void createVariableProviderRegistry(){
        this.variableProviders = new kernel.models.VariableProviderRegistry();
    }

    /**
     * If the list of port names is empty, warn the user. If not, provide
     * the port names in Information.
     *
     * @param portNames A list containing the detected port names
     */
    private void handleLogging(List<String> portNames){
        if (portNames.isEmpty()) {
            writeLogEntryForEmptyList();
        } else {
            writeLogEntryForPortsDetected(portNames);
        }
    }

    private void writeLogEntryForEmptyList(){
        log.warn("Kernel {} has not detected any available serial ports",
                this.toString());
    }

    private void writeLogEntryForPortsDetected(List<String> portNames){
        for (String portName: portNames){
            log.info("Kernel {} detected serial port {}", this, portName);
        }
    }
}
