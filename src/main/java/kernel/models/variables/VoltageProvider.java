package kernel.models.variables;

import devices.PowerSupply;
import kernel.Kernel;
import kernel.views.variables.VariableChangeEventListener;
import kernel.views.variables.VariableProvider;
import kernel.views.variables.Voltage;
import org.jetbrains.annotations.Contract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;
import java.util.*;

/**
 * Provides voltages
 */
public class VoltageProvider implements VariableProvider<Voltage> {
    private PowerSupply powerSupply;

    private final List<Voltage> voltages = new LinkedList<>();

    private volatile Boolean isThreadAlive = Boolean.FALSE;

    private volatile Integer numberOfDataPoints = 10;

    private volatile Duration pollingInterval = Duration.ofMillis(1000);

    private final Set<VariableChangeEventListener<Voltage>> listeners = new
            HashSet<>();

    public VoltageProvider(PowerSupply powerSupply, Kernel kernel){
        this.powerSupply = powerSupply;
        isThreadAlive = Boolean.TRUE;
        kernel.getTaskRunner().execute(new VoltagePollingTask());
    }

    @Override
    public List<Voltage> getValues(){
        return voltages;
    }

    @Override
    public Boolean isProvidingVariables(){
        return isThreadAlive;
    }

    @Override
    public void clearHistory(){
        voltages.clear();
    }

    @Override
    public Integer getNumberOfDataPoints(){
        return numberOfDataPoints;
    }

    @Override
    public void setNumberOfDataPoints(Integer numberOfDataPoints){
        this.numberOfDataPoints = numberOfDataPoints;
    }

    @Override
    public void setPollingInterval(Duration interval){
        pollingInterval = interval;
    }

    @Override
    public Duration getLengthOfHistory(){
        return pollingInterval.multipliedBy(numberOfDataPoints);
    }

    @Override
    public Duration getPollingInterval(){
        return this.pollingInterval;
    }

    @Override
    public void removeOnChangeListener(VariableChangeEventListener<Voltage>
                                                   listener){
        listeners.remove(listener);
    }

    @Override
    public void addOnChangeListener(VariableChangeEventListener<Voltage>
                                                listener){
        listeners.add(listener);
    }

    private class VoltagePollingTask implements Runnable {
        private final Logger log = LoggerFactory.getLogger
                (VoltagePollingTask.class);

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

        private void handleIOException(IOException error){
            log.warn("Attempting to get measured voltage returned error {}",
                    error);

            isThreadAlive = Boolean.FALSE;
        }

        @Contract("_ -> !null")
        private Voltage makeDataPoint(Double measuredVoltage){
            return new VoltageDataPoint(new Date(), measuredVoltage);
        }

        private void updateListeners(Voltage dataPoint){
            for (VariableChangeEventListener<Voltage> listener: listeners){
                listener.onChange(dataPoint);
            }
        }

        private void checkVoltageListSize(){
            if (voltages.size() > numberOfDataPoints){
                removeTailOfHistory();
            }
        }

        private void removeTailOfHistory(){
            log.debug("Detected more voltages than allowed data points, " +
                    "removing last element");
            voltages.remove(0);
        }

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
