package kernel.models;

import kernel.views.variables.Pressure;
import kernel.views.variables.VariableProvider;

/**
 * Responsible for storing variable providers
 */
public class VariableProviderRegistry implements
        CombinedVariableProviderRegistry {
    private VariableProvider<Pressure> pressureVariableProvider;

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
}
