package kernel.models;

import devices.PowerSupply;
import devices.PressureGauge;
import org.jetbrains.annotations.Contract;

/**
 * The data structure containing instances of devices. Once constructed,
 * devices should be written here, in order to be available to other
 * objects in this application.
 *
 * @implNote Device types are currently hard-coded. I'd love to find a way
 * to generalize this out to multiple types with generics, with something
 * similar to Spring's IOC containers.
 */
public final class DeviceRegistry implements CombinedDeviceRegistry {

    /**
     * The power supply to share
     */
    private PowerSupply powerSupply;

    /**
     * The pressure gauge to share
     */
    private PressureGauge pressureGauge;

    /**
     * True if the power supply was set, else false
     */
    private Boolean powerSupplyWasSet = Boolean.FALSE;

    /**
     * True if the pressure gauge was set, else false
     */
    private Boolean pressureGaugeWasSet = Boolean.FALSE;

    /**
     * @return The power supply managed here
     */
    @Override public PowerSupply getPowerSupply(){
        return powerSupply;
    }

    /**
     * @param powerSupply The power supply to enter into this registry.
     */
    @Override public void setPowerSupply(PowerSupply powerSupply){
        this.powerSupply = powerSupply;
        this.powerSupplyWasSet = Boolean.TRUE;
    }

    /**
     * @return True if the power supply has been set, else false.
     */
    @Contract(pure = true)
    @Override public Boolean hasPowerSupply(){
        return this.powerSupplyWasSet;
    }

    /**
     * @return The current pressure gauge
     */
    @Override public PressureGauge getPressureGauge(){
        return pressureGauge;
    }

    /**
     * @param pressureGauge The pressure gauge to enter into the registry
     */
    @Override public void setPressureGauge(PressureGauge pressureGauge){
        this.pressureGauge = pressureGauge;
        pressureGaugeWasSet = Boolean.TRUE;
    }

    /**
     * @return True if the pressure gauge exists, else false.
     */
    @Contract(pure = true)
    @Override public Boolean hasPressureGauge(){
        return pressureGaugeWasSet;
    }
}
