package kernel.models.variables;

import devices.PressureGauge;
import kernel.views.variables.Pressure;
import kernel.views.variables.VariableChangeEventListener;
import kernel.views.variables.VariableProvider;
import org.jetbrains.annotations.Contract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.*;

/**
 * Provides the pressure measured by the PVCi Pressure gauge
 */
public class PressureProvider implements VariableProvider<Pressure> {

    private PressureGauge pressureGauge;

    private final List<Pressure> pressures = new LinkedList<>();

    private final Set<VariableChangeEventListener<Pressure>> listeners;

    private volatile Integer numberOfDataPoints = 10;

    private volatile Boolean isPollingThreadAlive = Boolean.FALSE;

    private volatile Duration pollingInterval = Duration.ofMillis(1000);

    public PressureProvider(PressureGauge gauge){
        this.pressureGauge = gauge;

        Thread pollingThread = new PressurePollingThread();
        listeners = new HashSet<>();

        isPollingThreadAlive = Boolean.TRUE;
        pollingThread.start();
    }

    @Override
    public List<Pressure> getValues(){
        return pressures;
    }

    @Override
    public Boolean isProvidingVariables(){
        return isPollingThreadAlive;
    }

    @Override
    public void clearHistory(){
        pressures.clear();
    }

    @Override
    public Integer getNumberOfDataPoints(){
        return this.numberOfDataPoints;
    }

    @Override
    public void setNumberOfDataPoints(Integer numberOfDataPoints){
        this.numberOfDataPoints = numberOfDataPoints;
    }

    @Override
    public Duration getLengthOfHistory(){
        return pollingInterval.multipliedBy(numberOfDataPoints);
    }

    @Override
    public Duration getPollingInterval(){
        return pollingInterval;
    }

    @Override
    public void setPollingInterval(Duration pollingInterval){
        this.pollingInterval = pollingInterval;
    }

    @Override
    public void addOnChangeListener(VariableChangeEventListener<Pressure>
                                                listener){
        listeners.add(listener);
    }

    @Override
    public void removeOnChangeListener(VariableChangeEventListener<Pressure>
                                                   listener){
        listeners.remove(listener);
    }

    private class PressurePollingThread extends Thread {
        private final Logger log = LoggerFactory.getLogger(
                PressurePollingThread.class
        );

        @Override
        public void run(){
            while (this.isAlive()) {
                Float pressureValue = getPressure();
                Pressure dataPoint = makeDataPoint(pressureValue);
                pressures.add(dataPoint);
                updateListeners(dataPoint);
                checkPressureListSize();
                waitForPollingInterval();
            }
        }

        private void checkPressureListSize(){
            if (pressures.size() > numberOfDataPoints){
                removeTailOfHistory();
            }
        }

        private void removeTailOfHistory(){
            log.debug("Detected list of pressures greater than allowed. " +
                    "Removing last element.");
            int INDEX_OF_FIRST_ELEMENT = 0;

            pressures.remove(INDEX_OF_FIRST_ELEMENT);
        }

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

        private void updateListeners(Pressure newPressure){
            for(VariableChangeEventListener<Pressure> listener: listeners){
                listener.onChange(newPressure);
            }
        }

        private void handleException(Exception error){
            log.warn(
                    "Attempting to get pressure returned error {}", error
            );
            isPollingThreadAlive = Boolean.FALSE;
        }

        @Contract("_ -> !null")
        private Pressure makeDataPoint(Float pressure){
            Date rightNow = new Date();

            return new PressureDataPoint(rightNow, pressure);
        }

        private void waitForPollingInterval(){
            try {
                sleep(pollingInterval.toMillis());
            } catch (InterruptedException error){
                log.debug("Thread interrupted");
                isPollingThreadAlive = Boolean.FALSE;
            }
        }
    }
}

