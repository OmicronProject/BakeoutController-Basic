package kernel.models;

import kernel.controllers.TDKLambdaPowerSupplyFactory;
import kernel.controllers.TaskRunner;
import kernel.controllers.variables.VariableProviderContainer;
import kernel.modbus.ModBusConnectionManager;
import kernel.modbus.ModbusConnector;
import kernel.serial_ports.PortDriver;
import kernel.serial_ports.SerialPort;
import kernel.views.CommPortReporter;
import kernel.views.DeviceContainer;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Contains methods for working with application hardware, getting the
 * necessary views and controllers for working with the UI
 */
public final class Kernel implements kernel.Kernel, CommPortReporter {

    /**
     * The log to which the kernel writes information useful for runtime
     * debugging.
     */
    private static final Logger log = LoggerFactory.getLogger(Kernel.class);

    /**
     * The driver responsible for managing serial ports
     */
    private PortDriver portDriver;

    /**
     * The container that holds devices
     */
    private kernel.models.DeviceContainer deviceRegistry;

    /**
     * The container for variable providers
     */
    private kernel.models.VariableProviderContainer variableProviders;

    /**
     * A factory bean used to create the power supply
     */
    private kernel.controllers.TDKLambdaPowerSupplyFactory
            tdkLambdaPowerSupplyFactory;

    /**
     * A factory bean useful for creating instances of the pressure gauge
     * device
     */
    private kernel.controllers.PVCiPressureGaugeFactory
            pvCiPressureGaugeFactory;

    /**
     * An executor for runnable tasks that runs all its tasks in a
     * {@link java.util.concurrent.ThreadPoolExecutor}.
     */
    private TaskRunner runner = new kernel.models.TaskRunner();

    /**
     * @param portDriver The driver to be used for managing the RS232 serial
     *                   port
     */
    public Kernel(PortDriver portDriver){
        this.portDriver = portDriver;
        this.deviceRegistry = new kernel.models.DeviceContainer();
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

    /**
     * @return A {@link PortDriver} used for making RS232 connections, and
     * managing RS232 ports in this system
     */
    @Override
    public PortDriver getPortDriver(){
        return this.portDriver;
    }

    /**
     * @return a {@link kernel.controllers.DeviceRegistry} used for adding
     * or updating which devices are in the {@link kernel.models.DeviceContainer}
     */
    @Contract(pure = true)
    @Override
    public kernel.controllers.DeviceRegistry getDeviceRegistryController(){
        return this.deviceRegistry;
    }

    /**
     * @return a {@link DeviceContainer} used for looking up
     * devices that have been created
     */
    @Contract(pure = true)
    @Override
    public DeviceContainer getDeviceRegistryView(){
        return this.deviceRegistry;
    }

    /**
     * @return A factory bean useful for creating instances of
     * {@link devices.PowerSupply}
     */
    @Override
    public TDKLambdaPowerSupplyFactory getPowerSupplyFactory(){
        return this.tdkLambdaPowerSupplyFactory;
    }

    /**
     * @return A connection manager for making MODBUS connections over RS232
     */
    @Contract(" -> !null")
    @Override
    public ModbusConnector getModbusConnector(){
        return new ModBusConnectionManager();
    }

    /**
     * @return A factory useful for creating instances of
     * {@link devices.PVCiPressureGauge}
     */
    @Override
    public kernel.controllers.PVCiPressureGaugeFactory
    getPressureGaugeFactory(){
        return this.pvCiPressureGaugeFactory;
    }

    /**
     * @return A set of getters for variable providers managed by an
     * instance of this kernel
     */
    @Contract(pure = true)
    @Override
    public kernel.views.VariableProviderContainer getVariableProvidersView(){
        return this.variableProviders;
    }

    /**
     * @return A controller that allows mutation of variable providers
     */
    @Contract(pure = true)
    @Override
    public VariableProviderContainer
            getVariableProvidersController(){
        return this.variableProviders;
    }

    /**
     * @return A task runner that manages execution of asynchronous tasks
     * that do not require execution on a special thread
     */
    @Override
    public TaskRunner getTaskRunner(){
        return this.runner;
    }

    /**
     * @param runner The task runner to be used for executing tasks
     */
    @Override
    public void setTaskRunner(TaskRunner runner){
        this.runner = runner;
    }

    /**
     * Create an instance of
     * {@link kernel.models.TDKLambdaPowerSupplyFactory}, attach this kernel
     * to this factory, and write this factory to a field in the kernel
     */
    private void createTDKLambdaPowerSupplyFactory(){
        this.tdkLambdaPowerSupplyFactory = new kernel.models
                .TDKLambdaPowerSupplyFactory();
        this.tdkLambdaPowerSupplyFactory.setKernel(this);
    }

    /**
     * Create an instance of {@link PVCiPressureGaugeFactory} and attach
     * this kernel to it.
     */
    private void createPVCIPressureGaugeFactory(){
        this.pvCiPressureGaugeFactory = new PVCiPressureGaugeFactory();
        this.pvCiPressureGaugeFactory.setKernel(this);
    }

    /**
     * Create an instance of {@link kernel.models.VariableProviderContainer}
     */
    private void createVariableProviderRegistry(){
        this.variableProviders = new kernel.models.VariableProviderContainer();
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

    /**
     * If no available serial ports were found at application startup, write
     * a warning to the log.
     */
    private void writeLogEntryForEmptyList(){
        log.warn("Kernel {} has not detected any available serial ports",
                this.toString());
    }

    /**
     * Write a series of log entries stating that a serial port was detected
     * @param portNames The names of ports located on the machine
     */
    private void writeLogEntryForPortsDetected(List<String> portNames){
        for (String portName: portNames){
            log.info("Kernel {} detected serial port {}", this, portName);
        }
    }
}
