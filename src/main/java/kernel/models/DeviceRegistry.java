package kernel.models;

import devices.PowerSupply;

/**
 * Contains a registry for devices
 */
public class DeviceRegistry implements kernel.controllers.DeviceRegistry,
        kernel.views.DeviceRegistry {
    private PowerSupply powerSupply;
    private Boolean powerSupplyWasSet;

    public DeviceRegistry(){
        this.powerSupplyWasSet = Boolean.FALSE;
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
}
