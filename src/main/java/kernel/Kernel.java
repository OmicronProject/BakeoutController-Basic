package kernel;

import kernel.controllers.*;
import kernel.controllers.DeviceRegistry;
import kernel.controllers.variables.VariableProviderContainer;
import kernel.modbus.ModbusConnector;
import kernel.serial_ports.PortDriver;
import kernel.views.*;

/**
 * Describes the main application kernel
 */
public interface Kernel {
    /**
     * @return A view that can list the available serial ports on this machine
     */
    CommPortReporter getCommPortReporter();

    PortDriver getPortDriver();

    kernel.views.DeviceRegistry getDeviceRegistryView();

    DeviceRegistry getDeviceRegistryController();

    TDKLambdaPowerSupplyFactory getPowerSupplyFactory();

    ModbusConnector getModbusConnector();

    PVCiPressureGaugeFactory getPressureGaugeFactory();

    kernel.views.VariableProviderRegistry getVariableProvidersView();

    VariableProviderContainer
    getVariableProvidersController();

    TaskRunner getTaskRunner();

    void setTaskRunner(TaskRunner runner);
}
