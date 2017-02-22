package kernel.views;

import kernel.views.variables.Pressure;
import kernel.views.variables.VariableProvider;
import kernel.views.variables.Voltage;

/**
 * Created by mkononen on 16/02/17.
 */
public interface VariableProviderRegistry {
    VariableProvider<Pressure> getPressureProvider();

    Boolean hasPressureProvider();

    VariableProvider<Voltage> getVoltageProvider();

    Boolean hasVoltageProvider();
}
