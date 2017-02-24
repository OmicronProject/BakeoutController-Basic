package unit.ui;

import kernel.views.variables.VariableChangeEventListener;
import kernel.views.variables.Voltage;
import kernel.views.variables.VoltageProvider;

import java.time.Duration;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by mkononen on 24/02/17.
 */
public class MockVoltageProvider implements VoltageProvider {
    private final List<Voltage> values = new LinkedList<>();
    private Duration pollingInterval = Duration.ofMillis(10);

    private final List<VariableChangeEventListener<Voltage>> listeners =
            new LinkedList<>();

    public MockVoltageProvider(){
    }

    @Override
    public List<Voltage> getValues(){
        return values;
    }

    @Override
    public Boolean isProvidingVariables(){ return Boolean.TRUE; }

    @Override
    public void setPollingInterval(Duration pollingInterval){
        this.pollingInterval = pollingInterval;
    }

    @Override
    public Duration getPollingInterval(){
        return pollingInterval;
    }

    @Override
    public void setNumberOfDataPoints(Integer numberOfDataPoints){
        // Do nothing
    }

    @Override
    public Integer getNumberOfDataPoints(){
        return 2;
    }

    public void addValue(Voltage newValue){
        values.add(newValue);

        for(VariableChangeEventListener<Voltage> listener: listeners){
            listener.onChange(newValue);
        }
    }

    @Override
    public Duration getLengthOfHistory(){
        return this.pollingInterval.multipliedBy(getNumberOfDataPoints());
    }

    @Override
    public void clearHistory(){
        values.clear();
    }

    @Override
    public void addOnChangeListener(
            VariableChangeEventListener<Voltage> listener
    ){
        listeners.add(listener);
    }

    @Override
    public void removeOnChangeListener(
            VariableChangeEventListener listener
    ){
        listeners.remove(listener);
    }

    public List<VariableChangeEventListener<Voltage>> getListeners(){
        return this.listeners;
    }
}