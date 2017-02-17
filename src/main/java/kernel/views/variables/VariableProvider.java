package kernel.views.variables;

import java.time.Duration;
import java.util.List;

/**
 * Provides a measured variable of a type T as a function of time. A data
 * series is built up by polling.
 */
public interface VariableProvider<T> {
    List<T> getValues();

    Duration getLengthOfHistory();

    Integer getNumberOfDataPoints();

    Boolean isProvidingVariables();

    void setNumberOfDataPoints(Integer numberOfDataPoints);

    void clearHistory();

    Duration getPollingInterval();

    void setPollingInterval(Duration pollingInterval);

    void addOnChangeListener(VariableChangeEventListener<T> listener);

    void removeOnChangeListener(VariableChangeEventListener<T> listener);
}