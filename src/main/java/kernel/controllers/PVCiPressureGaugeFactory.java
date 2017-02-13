package kernel.controllers;

import devices.PressureGauge;
import exceptions.DeviceNotCreatedException;
import kernel.Kernel;

/**
 * Created by mkononen on 10/02/17.
 */
public interface PVCiPressureGaugeFactory {

    Kernel getKernel();

    void setKernel(Kernel kernel);

    Integer getAddress();

    void setAddress(Integer address);

    String getPortName();

    void setPortName(String portName);

    PressureGauge getPressureGauge() throws DeviceNotCreatedException;
}
