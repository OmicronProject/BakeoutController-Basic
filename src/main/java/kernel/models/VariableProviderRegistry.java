package kernel.models;

import kernel.views.variables.Pressure;
import kernel.views.variables.VariableProvider;
import kernel.views.variables.Voltage;

/**
 * Responsible for storing variable providers
 */
public class VariableProviderRegistry implements
        CombinedVariableProviderRegistry {
    private VariableProvider<Pressure> pressureVariableProvider;

    private VariableProvider<Voltage> voltageVariableProvider;

    @Override
    public VariableProvider<Pressure> getPressureProvider(){
        return pressureVariableProvider;
    }

    @Override
    public void setPressureProvider(VariableProvider<Pressure> provider){
        this.pressureVariableProvider = provider;
    }

    @Override
    public Boolean hasPressureProvider(){
        return pressureVariableProvider != null;
    }

    @Override
    public VariableProvider<Voltage> getVoltageProvider(){
        return voltageVariableProvider;
    }

    @Override
    public void setVoltageProvider(VariableProvider<Voltage> voltageProvider){
        this.voltageVariableProvider = voltageProvider;
    }

    @Override
    public Boolean hasVoltageProvider(){
        return voltageVariableProvider != null;
    }
}
