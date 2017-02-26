package kernel.views;

import kernel.views.variables.Pressure;
import kernel.views.variables.VariableProvider;
import kernel.views.variables.Voltage;

/**
 * Describes a container for variables
 */
public interface VariableProviderContainer {

    /**
     * @return A provider for the pressure
     */
    VariableProvider<Pressure> getPressureProvider();

    /**
     * @return {@link Boolean#TRUE} if a pressure provider has been set,
     * otherwise {@link Boolean#FALSE}
     */
    Boolean hasPressureProvider();

    /**
     * @return A provider for voltage
     */
    VariableProvider<Voltage> getVoltageProvider();

    /**
     * @return {@link Boolean#TRUE} if a voltage provider has been provided,
     * otherwise {@link Boolean#FALSE}
     */
    Boolean hasVoltageProvider();
}
