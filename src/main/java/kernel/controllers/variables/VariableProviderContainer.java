package kernel.controllers.variables;

import kernel.views.variables.Pressure;
import kernel.views.variables.VariableProvider;
import kernel.views.variables.Voltage;

/**
 * Contains a database of variable providers. This particular interface
 * specifies the setters which enable control over the
 * {@link kernel.models.VariableProviderContainer}. The intention of such a
 * design is to decouple mutation of the kernel's variable providers with
 * access.
 */
public interface VariableProviderContainer {
    /**
     * @param pressureProvider The new pressure provider that will be used
     *                         in order to retrieve the pressure in the
     *                         preparation chamber
     */
    void setPressureProvider(VariableProvider<Pressure> pressureProvider);

    /**
     * @param voltageProvider The new voltage provider to use. This variable
     *                        provider retrieves the voltage going into the
     *                        power supply
     */
    void setVoltageProvider(VariableProvider<Voltage> voltageProvider);
}
