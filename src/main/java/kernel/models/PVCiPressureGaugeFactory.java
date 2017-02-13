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

public class PVCiPressureGaugeFactory implements
        kernel.controllers.PVCiPressureGaugeFactory {

    private static Logger log = LoggerFactory.getLogger(
            PVCiPressureGaugeFactory.class
    );

    private Integer address = 2;

    private String portName;

    @Autowired
    private Kernel kernel;

    @Contract(pure = true)
    @Override
    public Kernel getKernel(){
        return kernel;
    }

    @Override
    public void setKernel(Kernel kernel){
        this.kernel = kernel;
    }

    @Override
    public Integer getAddress(){
        return this.address;
    }

    @Override
    public void setAddress(Integer address){
        this.address = address;
    }

    @Override
    public String getPortName(){
        return this.portName;
    }

    @Override
    public void setPortName(String portName){
        this.portName = portName;
    }

    @Override
    public PressureGauge getPressureGauge() {
        kernel.views.DeviceRegistry registry = kernel.getDeviceRegistryView();

        if(!registry.hasPressureGauge()){
            writeEntryforNoGauge();
            makePressureGauge();
        }

        PressureGauge gauge = registry.getPressureGauge();

        writeEntryForGauge(gauge);

        return gauge;
    }

    @Override
    public void makePressureGauge(){
        kernel.controllers.DeviceRegistry registry = kernel
                .getDeviceRegistryController();

        registry.setPressureGauge(createInstance());
    }

    @Contract(" -> !null")
    private PressureGauge createInstance(){
        return new PVCiPressureGauge(address, getConnection());
    }

    private ModbusConnector getConnection(){
        ModbusConnector connector = kernel.getModbusConnector();
        connector.setPortConfiguration(getConfiguration());

        return connector;
    }

    private ModbusPortConfiguration getConfiguration(){
        ModbusPortConfiguration config = new
                StandaloneModbusPortConfiguration();

        config.setPortName(portName);
        config.setBaudRate(ModbusPortConfiguration.BAUD_RATE_19200);
        config.setDataBits(ModbusPortConfiguration.DATABITS_8);
        config.setStopBits(ModbusPortConfiguration.STOPBITS_1);
        config.setParityBits(ModbusPortConfiguration.PARITY_NONE);
        config.setEncoding(ModbusPortConfiguration.ASCII_ENCODING);

        return config;
    }

    private static void writeEntryForGauge(PressureGauge gauge){
        log.debug("Found Pressure gauge {}", gauge);
    }

    private static void writeEntryforNoGauge(){
        log.debug("No pressure gauge found. Creating a new one.");
    }
}
