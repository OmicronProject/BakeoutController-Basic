package kernel.controllers;

import devices.PowerSupply;
import devices.PressureGauge;
import devices.RS232Device;

/**
 * Controller to modify values in the kernel's
 * {@link kernel.models.DeviceRegistry}. This is a singleton in which
 * representations of connected devices are held.
 */
public interface DeviceRegistry {
    /**
     * @param powerSupply The power supply to enter into the
     * {@link DeviceRegistry}
     */
    void setPowerSupply(PowerSupply powerSupply);

    /**
     * @param pressureGauge The pressure gauge to enter into the registry
     */
    void setPressureGauge(PressureGauge pressureGauge);
}
