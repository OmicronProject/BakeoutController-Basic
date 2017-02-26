package kernel.views;

import devices.PowerSupply;
import devices.PressureGauge;
import devices.RS232Device;

/**
 * Describes the view for a container for devices.
 */
public interface DeviceContainer {

    /**
     * @return The power supply
     */
    PowerSupply getPowerSupply();

    /**
     * @return {@link Boolean#TRUE} if a power supply was placed into the
     * container, and {@link Boolean#FALSE} if not
     */
    Boolean hasPowerSupply();

    /**
     * @return The pressure gauge
     */
    PressureGauge getPressureGauge();

    /**
     * @return {@link Boolean#TRUE} if a pressure gauge was placed into the
     * container and {@link Boolean#FALSE} if not
     */
    Boolean hasPressureGauge();
}
