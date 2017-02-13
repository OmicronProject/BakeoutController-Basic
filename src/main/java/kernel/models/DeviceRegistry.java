package kernel.models;

import devices.PowerSupply;
import devices.PressureGauge;

/**
 * Contains a registry for devices
 */
public final class DeviceRegistry implements CombinedDeviceRegistry {
    private PowerSupply powerSupply;
    private PressureGauge pressureGauge;

    private Boolean powerSupplyWasSet;
    private Boolean pressureGaugeWasSet;

    public DeviceRegistry(){
        this.powerSupplyWasSet = Boolean.FALSE;
        this.pressureGaugeWasSet = Boolean.FALSE;
    }

    @Override public PowerSupply getPowerSupply(){
        return powerSupply;
    }

    @Override public void setPowerSupply(PowerSupply powerSupply){
        this.powerSupply = powerSupply;
        this.powerSupplyWasSet = Boolean.TRUE;
    }

    @Override public Boolean hasPowerSupply(){
        return this.powerSupplyWasSet;
    }

    @Override public PressureGauge getPressureGauge(){
        return pressureGauge;
    }

    @Override public void setPressureGauge(PressureGauge pressureGauge){
        this.pressureGauge = pressureGauge;
        pressureGaugeWasSet = Boolean.TRUE;
    }

    @Override public Boolean hasPressureGauge(){
        return pressureGaugeWasSet;
    }
}
