package kernel.views.variables;

/**
 * Defines the contract for a listener that can listen to variable changes.
 */
public interface VariableChangeEventListener<T extends Variable> {
    void onChange(T newValue);
}
