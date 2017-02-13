package kernel.controllers;

import devices.PowerSupply;
import devices.PressureGauge;
import devices.RS232Device;

/**
 * Created by mkononen on 03/02/17.
 */
public interface DeviceRegistry {
    void setPowerSupply(PowerSupply powerSupply);

    void setPressureGauge(PressureGauge pressureGauge);
}
