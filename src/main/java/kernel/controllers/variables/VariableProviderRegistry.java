package kernel.controllers.variables;

import kernel.views.variables.Pressure;
import kernel.views.variables.VariableProvider;

/**
 * Created by mkononen on 16/02/17.
 */
public interface VariableProviderRegistry {
    void setPressureProvider(VariableProvider<Pressure> pressureProvider);

}
