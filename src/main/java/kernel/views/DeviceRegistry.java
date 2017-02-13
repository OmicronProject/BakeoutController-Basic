package kernel.views;

import devices.PowerSupply;
import devices.PressureGauge;
import devices.RS232Device;

/**
 * Created by mkononen on 07/02/17.
 */
public interface DeviceRegistry {
    PowerSupply getPowerSupply();

    Boolean hasPowerSupply();

    PressureGauge getPressureGauge();

    Boolean hasPressureGauge();
}
