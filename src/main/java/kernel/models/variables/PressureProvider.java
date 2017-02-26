package kernel.models.variables;

import devices.PressureGauge;
import exceptions.NegativeDurationException;
import kernel.Kernel;
import kernel.views.variables.Pressure;
import kernel.views.variables.Variable;
import kernel.views.variables.VariableChangeEventListener;
import kernel.views.variables.VariableProvider;
import org.jetbrains.annotations.Contract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.*;

/**
 * Provides the pressure measured by the PVCi Pressure gauge. Responsible
 * for polling the PVCi pressure gauge and retrieving the pressure.
 */
public class PressureProvider implements VariableProvider<Pressure> {

    /**
     * The pressure gauge with which the pressure is to be measured
     */
    private PressureGauge pressureGauge;

    /**
     * The list of pressure that was previously measured
     */
    private final List<Pressure> pressures = new LinkedList<>();

    /**
     * The set of listeners that needs to be notified of a change in the
     * measured pressure
     */
    private final Set<VariableChangeEventListener<Pressure>> listeners = new
            HashSet<>();

    /**
     * The number of data points to be stored in the history of this provider
     */
    private volatile Integer numberOfDataPoints = 10;

    /**
     * {@link Boolean#TRUE} if the thread is currently polling for changes
     * in pressure, otherwise {@link Boolean#FALSE}
     */
    private volatile Boolean isPollingThreadAlive = Boolean.FALSE;

    /**
     * The amount of time that the provider will wait before asking for the
     * pressure again.
     */
    private volatile Duration pollingInterval = Duration.ofMillis(1000);

    /**
     * @param gauge The pressure gauge to use for making measurements
     * @param kernel The kernel to use
     */
    public PressureProvider(PressureGauge gauge, Kernel kernel){
        this.pressureGauge = gauge;
        Runnable pressureTask = new PressurePollingTask();
        isPollingThreadAlive = Boolean.TRUE;
        kernel.getTaskRunner().execute(pressureTask);
    }

    /**
     * @return The list of values recorded by this variable provider
     */
    @Override
    public List<Pressure> getValues(){
        return pressures;
    }

    /**
     * @return {@link Boolean#TRUE} if it is and {@link Boolean#FALSE} if not
     */
    @Override
    public Boolean isProvidingVariables(){
        return isPollingThreadAlive;
    }

    /**
     * Remove all entries in this variable provider
     */
    @Override
    public void clearHistory(){
        pressures.clear();
    }

    /**
     * @return The number of data points in this provider's history
     */
    @Override
    public Integer getNumberOfDataPoints(){
        return this.numberOfDataPoints;
    }

    /**
     * @param numberOfDataPoints The number of data points that need to be
     *                           recorded.
     * @implNote When a new data point is recorded, a check is made to see
     * if the number of data points exceeds this maximum. If it does, the
     * oldest entry is removed from the list
     */
    @Override
    public void setNumberOfDataPoints(Integer numberOfDataPoints) {
        this.numberOfDataPoints = numberOfDataPoints;
    }

    /**
     * @return The length of time for which this provider is recording the
     * variable history
     */
    @Override
    public Duration getLengthOfHistory(){
        return pollingInterval.multipliedBy(numberOfDataPoints);
    }

    /**
     * @return The time between which the program is asking for the pressure
     */
    @Override
    public Duration getPollingInterval(){
        return pollingInterval;
    }

    /**
     * @param pollingInterval The desired polling interval
     * @throws NegativeDurationException if the interval is attempted to be
     * set to a negative value.
     */
    @Override
    public void setPollingInterval(Duration pollingInterval) throws
            NegativeDurationException {
        if (pollingInterval.isNegative()){
            throw new NegativeDurationException(pollingInterval);
        }
        this.pollingInterval = pollingInterval;
    }

    /**
     * Add a listener to the list of listeners. This provider will then
     * notify the listeners that the variable changed.
     * @param listener The listener to add
     */
    @Override
    public void addOnChangeListener(
            VariableChangeEventListener<Pressure> listener){
        listeners.add(listener);
    }

    /**
     * @param listener The listener to remove from the list
     */
    @Override
    public void removeOnChangeListener(
            VariableChangeEventListener<Pressure> listener){
        listeners.remove(listener);
    }

    /**
     * Describes a task for polling the pressure gauge
     */
    private class PressurePollingTask implements Runnable {

        /**
         * The log to use for writing data
         */
        private final Logger log = LoggerFactory.getLogger(
                PressurePollingTask.class
        );

        /**
         * Run the loop.
         */
        @Override
        public void run(){
            while (isPollingThreadAlive) {
                Float pressureValue = getPressure();
                Pressure dataPoint = makeDataPoint(pressureValue);
                pressures.add(dataPoint);
                updateListeners(dataPoint);
                checkPressureListSize();
                waitForPollingInterval();
            }
        }

        /**
         * Check that the pressure list is of the correct size. If not,
         * remove the last element of the pressure list
         */
        private void checkPressureListSize(){
            if (pressures.size() > numberOfDataPoints){
                removeTailOfHistory();
            }
        }

        /**
         * Remove the first element from the pressure list
         */
        private void removeTailOfHistory(){
            log.debug("Detected list of pressures greater than allowed. " +
                    "Removing last element.");
            int INDEX_OF_FIRST_ELEMENT = 0;

            pressures.remove(INDEX_OF_FIRST_ELEMENT);
        }

        /**
         * @return The current pressure
         */
        private Float getPressure(){
            Float pressure;

            try {
                pressure = pressureGauge.getPressure();
                isPollingThreadAlive = Boolean.TRUE;
            } catch (Exception error){
                handleException(error);
                pressure = pressures.get(0).getValue();
            }

            return pressure;
        }

        /**
         * Update the listeners with the new pressure value
         * @param newPressure The value with which to call each listener's
         * {@link VariableChangeEventListener#onChange(Variable)} method
         */
        private void updateListeners(Pressure newPressure){
            for(VariableChangeEventListener<Pressure> listener: listeners){
                listener.onChange(newPressure);
            }
        }

        /**
         * Handle an exception thrown while trying to get the pressure from
         * the pressure gauge
         * @param error The exception thrown
         */
        private void handleException(Exception error){
            log.error(
                    "Attempting to get pressure returned error {}", error
            );
            isPollingThreadAlive = Boolean.FALSE;
        }

        /**
         * Using the value of {@link Float} type returned by
         * {@link PressureGauge#getPressure()}, create a data point of type
         * {@link Variable}
         * @param pressure The pressure returned by the pressure gauge
         * @return The pressure variable point
         */
        @Contract("_ -> !null")
        private Pressure makeDataPoint(Float pressure){
            Date rightNow = new Date();

            return new PressureDataPoint(rightNow, pressure);
        }

        /**
         * Wait for the polling interval. If the thread is interrupted, stop
         * the thread.
         */
        private void waitForPollingInterval(){
            try {
                Thread.sleep(pollingInterval.toMillis());
            } catch (InterruptedException error){
                log.info("Thread interrupted");
                isPollingThreadAlive = Boolean.FALSE;
            }
        }
    }
}
