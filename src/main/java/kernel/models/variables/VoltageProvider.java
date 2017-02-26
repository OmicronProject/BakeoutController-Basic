package kernel.models.variables;

import devices.PowerSupply;
import exceptions.NegativeDurationException;
import kernel.Kernel;
import kernel.views.variables.Variable;
import kernel.views.variables.VariableChangeEventListener;
import kernel.views.variables.Voltage;
import org.jetbrains.annotations.Contract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;
import java.util.*;

/**
 * Contains an implementation of a provider to retrieve the voltage being
 * sent to the power supply
 */
public class VoltageProvider implements kernel.views.variables.VoltageProvider
{

    /**
     * The power supply to use for measuring the voltage
     */
    private PowerSupply powerSupply;

    /**
     * The recorded voltages
     */
    private final List<Voltage> voltages = new LinkedList<>();

    /**
     * {@link Boolean#FALSE} if the voltage polling thread is not providing
     * variables, otherwise {@link Boolean#TRUE}
     */
    private volatile Boolean isThreadAlive = Boolean.FALSE;

    /**
     * The number of data points to store in the provider
     */
    private volatile Integer numberOfDataPoints = 10;

    /**
     * The duration between polling of the power supply to determine the
     * measured voltage.
     */
    private volatile Duration pollingInterval = Duration.ofMillis(1000);

    /**
     * The listeners that are to be notified of changes
     */
    private final Set<VariableChangeEventListener<Voltage>> listeners = new
            HashSet<>();

    /**
     *
     * @param powerSupply The power supply to use for getting the voltage
     * @param kernel The kernel to which this provider is attached
     */
    public VoltageProvider(PowerSupply powerSupply, Kernel kernel){
        this.powerSupply = powerSupply;
        isThreadAlive = Boolean.TRUE;
        kernel.getTaskRunner().execute(new VoltagePollingTask());
    }

    /**
     * @return The measured voltages
     */
    @Override
    public List<Voltage> getValues(){
        return voltages;
    }

    /**
     * @return {@link Boolean#FALSE} if not providing variables, else
     * {@link Boolean#TRUE}
     */
    @Override
    public Boolean isProvidingVariables(){
        return isThreadAlive;
    }

    /**
     * Clear the list of recorded voltages
     */
    @Override
    public void clearHistory(){
        voltages.clear();
    }

    /**
     * @return The number of data points in this provider
     */
    @Override
    public Integer getNumberOfDataPoints(){
        return numberOfDataPoints;
    }

    /**
     * @param numberOfDataPoints The desired number of data points
     */
    @Override
    public void setNumberOfDataPoints(Integer numberOfDataPoints){
        this.numberOfDataPoints = numberOfDataPoints;
    }

    /**
     *
     * @param interval The desired polling interval
     * @throws NegativeDurationException If the desired interval is negative
     */
    @Override
    public void setPollingInterval(Duration interval) throws
            NegativeDurationException {
        if (interval.isNegative()){
            throw new NegativeDurationException(interval);
        }
        pollingInterval = interval;
    }

    /**
     * @return The length of time that is recorded by this provider
     */
    @Override
    public Duration getLengthOfHistory(){
        return pollingInterval.multipliedBy(numberOfDataPoints);
    }

    /**
     * @return The duration between polling cycles
     */
    @Override
    public Duration getPollingInterval(){
        return this.pollingInterval;
    }

    /**
     * @param listener The listener to remove
     */
    @Override
    public void removeOnChangeListener(
            VariableChangeEventListener<Voltage> listener
    ){
        listeners.remove(listener);
    }

    /**
     * Add a listener to be notified
     * @param listener The listener to add
     */
    @Override
    public void addOnChangeListener(
            VariableChangeEventListener<Voltage> listener
    ){
        listeners.add(listener);
    }

    /**
     * The task used to retrieve the voltage
     */
    private class VoltagePollingTask implements Runnable {

        /**
         * The log to which information regarding the state of this polling
         * task will be written.
         */
        private final Logger log = LoggerFactory.getLogger
                (VoltagePollingTask.class);

        /**
         * Run the task
         */
        @Override
        public void run(){
            while (isThreadAlive){
                Double voltage = getVoltage();
                Voltage dataPoint = makeDataPoint(voltage);
                voltages.add(dataPoint);
                updateListeners(dataPoint);
                checkVoltageListSize();
                waitForPollingInterval();
            }
        }

        /**
         * @return The measured voltage from the {@link PowerSupply} managed
         * by this task
         */
        private Double getVoltage(){
            Double voltage;

            try {
                voltage = powerSupply.getMeasuredVoltage();
                isThreadAlive = Boolean.TRUE;
            } catch (IOException error){
                handleIOException(error);
                voltage = voltages.get(0).getValue();
            }

            return voltage;
        }

        /**
         * Handles an {@link IOException} thrown if the voltage cannot be
         * retrieved
         *
         * @param error The error thrown while getting the voltage
         */
        private void handleIOException(IOException error){
            log.warn("Attempting to get measured voltage returned error {}",
                    error);

            isThreadAlive = Boolean.FALSE;
        }

        /**
         * Returns a variable for the {@link Double} representing the
         * voltage that was returned
         * @param measuredVoltage The measured voltage
         * @return a {@link Voltage} representing what was measured
         */
        @Contract("_ -> !null")
        private Voltage makeDataPoint(Double measuredVoltage){
            return new VoltageDataPoint(new Date(), measuredVoltage);
        }

        /**
         * Notify the registered listeners that the variable changed
         * @param dataPoint The new data point with which to call
         * {@link VariableChangeEventListener#onChange(Variable)}
         */
        private void updateListeners(Voltage dataPoint){
            for (VariableChangeEventListener<Voltage> listener: listeners){
                listener.onChange(dataPoint);
            }
        }

        /**
         * Check that the size of the list is correct. If too big, remove
         * the first element of the list
         */
        private void checkVoltageListSize(){
            if (voltages.size() > numberOfDataPoints){
                removeTailOfHistory();
            }
        }

        /**
         * Remove the first element of the pressure list
         */
        private void removeTailOfHistory(){
            log.debug("Detected more voltages than allowed data points, " +
                    "removing last element");
            voltages.remove(0);
        }

        /**
         * Wait for the polling interval. If interrupted, shut the polling
         * thread down and notify the {@link VoltageProvider} that the
         * thread was stopped.
         */
        private void waitForPollingInterval(){
            try {
                Thread.sleep(pollingInterval.toMillis());
            } catch (InterruptedException error){
                log.debug("Voltage polling task interrupted");
                isThreadAlive = Boolean.FALSE;
            }
        }
    }
}
