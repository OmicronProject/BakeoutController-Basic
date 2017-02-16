package kernel.models;

import devices.PVCiPressureGauge;
import devices.PressureGauge;
import kernel.Kernel;
import kernel.modbus.ModbusConnector;
import kernel.modbus.ModbusPortConfiguration;
import kernel.modbus.StandaloneModbusPortConfiguration;
import org.jetbrains.annotations.Contract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * Implements a factory for creating instances of {@link PressureGauge}
 */
public class PVCiPressureGaugeFactory implements
        kernel.controllers.PVCiPressureGaugeFactory {

    /**
     * The log to which construction details will be written
     */
    private static Logger log = LoggerFactory.getLogger(
            PVCiPressureGaugeFactory.class
    );

    /**
     * The address at which the device is located. This defaults to 2.
     */
    private Integer address = 2;

    /**
     * The current port name
     */
    private String portName;

    /**
     * The kernel to use for communicating with hardware, and interacting
     * with application singletons.
     */
    @Autowired
    private Kernel kernel;

    /**
     * @return The current kernel
     */
    @Contract(pure = true)
    @Override
    public Kernel getKernel(){
        return kernel;
    }

    /**
     * @param kernel The kernel to which this factory is to be attached
     */
    @Override
    public void setKernel(Kernel kernel){
        this.kernel = kernel;
    }

    /**
     * @return The desired device address
     */
    @Override
    public Integer getAddress(){
        return this.address;
    }

    /**
     * @param address The address to which the device will be attached
     */
    @Override
    public void setAddress(Integer address){
        this.address = address;
    }

    /**
     * @return The desired port name
     */
    @Override
    public String getPortName(){
        return this.portName;
    }

    /**
     * @param portName The port where the device will be found.
     */
    @Override
    public void setPortName(String portName){
        this.portName = portName;
    }

    /**
     * If an instance of the pressure gauge does not exist in the registry,
     * it creates a new one. Otherwise, it returns the {@link PressureGauge}
     * in the registry at the moment.
     *
     * @return The current pressure gauge
     */
    @Override
    public PressureGauge getPressureGauge() throws IOException {
        kernel.views.DeviceRegistry registry = kernel.getDeviceRegistryView();

        if(!registry.hasPressureGauge()){
            writeEntryforNoGauge();
            makePressureGauge();
        }

        PressureGauge gauge = registry.getPressureGauge();

        writeEntryForGauge(gauge);

        return gauge;
    }

    /**
     * Create an instance of {@link PressureGauge} and write it to the
     * registry.
     */
    @Override
    public void makePressureGauge() throws IOException {
        kernel.controllers.DeviceRegistry registry = kernel
                .getDeviceRegistryController();

        registry.setPressureGauge(createInstance());
    }

    /**
     * @return A new instance of {@link PressureGauge}
     */
    @Contract(" -> !null")
    private PressureGauge createInstance() throws IOException {
        return new PVCiPressureGauge(address, getConnection());
    }

    /**
     * @return The modbus connection to use for making the device
     */
    private ModbusConnector getConnection(){
        ModbusConnector connector = kernel.getModbusConnector();
        connector.setPortConfiguration(getConfiguration());

        return connector;
    }

    /**
     * @return The desired port configuration
     * @implNote Hard-coded values for port configuration are written here
     */
    private ModbusPortConfiguration getConfiguration(){
        ModbusPortConfiguration config = new
                StandaloneModbusPortConfiguration();

        config.setPortName(portName);
        config.setBaudRate(ModbusPortConfiguration.BAUD_RATE_19200);
        config.setDataBits(ModbusPortConfiguration.DATABITS_8);
        config.setStopBits(ModbusPortConfiguration.STOPBITS_1);
        config.setParityBits(ModbusPortConfiguration.PARITY_NONE);
        config.setEncoding(ModbusPortConfiguration.RTU_ENCODING);

        return config;
    }

    /**
     * Write a log entry indicating that the {@link PressureGauge} was
     * successfully created.
     *
     * @param gauge The gauge to use
     */
    private static void writeEntryForGauge(PressureGauge gauge){
        log.debug("Found Pressure gauge {}", gauge);
    }

    /**
     * Write an entry indicating that no pressure gauge was found in the
     * {@link DeviceRegistry}, and that a new instance is being created.
     */
    private static void writeEntryforNoGauge(){
        log.debug("No pressure gauge found. Creating a new one.");
    }
}
