package kernel.views;

import devices.PowerSupply;
import devices.RS232Device;

/**
 * Created by mkononen on 07/02/17.
 */
public interface DeviceRegistry {
    PowerSupply getPowerSupply();

    Boolean hasPowerSupply();
}
