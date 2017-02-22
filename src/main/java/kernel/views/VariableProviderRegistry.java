package kernel.views;

import kernel.views.variables.Pressure;
import kernel.views.variables.VariableProvider;

/**
 * Created by mkononen on 16/02/17.
 */
public interface VariableProviderRegistry {
    VariableProvider<Pressure> getPressureProvider();

    Boolean hasPressureProvider();
}
