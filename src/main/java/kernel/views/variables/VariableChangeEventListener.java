package kernel.views.variables;

/**
 * Defines the contract for a listener that can listen to variable changes.
 */
public interface VariableChangeEventListener<T extends Variable> {

    /**
     * Method called when the variable changes
     *
     * @param newValue The new value of the variable
     */
    void onChange(T newValue);
}
