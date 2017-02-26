package kernel.models.variables;

import kernel.views.variables.Voltage;
import java.util.Date;

/**
 * Describes a data point to measure voltage as a function of time
 */
public class VoltageDataPoint implements Voltage {

    /**
     * The date at which the voltage was recorded
     */
    private final Date date;

    /**
     * The value of the voltage that was recorded
     */
    private final Double value;

    /**
     *
     * @param date The date at which the voltage was recorded
     * @param value The value of the recorded voltage
     */
    public VoltageDataPoint(Date date, Double value){
        this.date = date;
        this.value = value;
    }

    /**
     *
     * @return The date
     */
    @Override
    public Date getDate(){
        return this.date;
    }

    /**
     *
     * @return The value
     */
    @Override
    public Double getValue(){
        return this.value;
    }
}
