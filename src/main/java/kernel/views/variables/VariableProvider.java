package kernel.views.variables;

import exceptions.NegativeDurationException;

import java.time.Duration;
import java.util.List;

/**
 * Provides a measured variable of a type T as a function of time. A data
 * series is built up by polling.
 */
public interface VariableProvider<T extends Variable> {

    /**
     * @return The history of values recorded by this provider
     */
    List<T> getValues();

    /**
     * @return The amount of time for which this variable provider can
     * record variables
     */
    Duration getLengthOfHistory();

    /**
     * @return The number of data points in this provider
     */
    Integer getNumberOfDataPoints();

    /**
     * @return {@link Boolean#TRUE} if the provider is alive, otherwise
     * {@link Boolean#FALSE}
     */
    Boolean isProvidingVariables();

    /**
     * @param numberOfDataPoints The number of data points that the provider
     *                           will store
     */
    void setNumberOfDataPoints(Integer numberOfDataPoints);

    /**
     * Remove all data points from the provider
     */
    void clearHistory();

    /**
     * @return The length of the interval between polling for the variable
     */
    Duration getPollingInterval();

    /**
     * @param pollingInterval The desired polling interval
     * @throws NegativeDurationException If the duration is negative
     */
    void setPollingInterval(Duration pollingInterval) throws
            NegativeDurationException;

    /**
     * @param listener The listener to add
     */
    void addOnChangeListener(VariableChangeEventListener<T> listener);

    /**
     * @param listener The listener to remove
     */
    void removeOnChangeListener(VariableChangeEventListener<T> listener);
}
