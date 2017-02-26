package kernel.models.variables;

import kernel.views.variables.Pressure;

import java.util.Date;

/**
 * Contains a standalone implementation of the Pressure variable
 */
public class PressureDataPoint implements Pressure {
    /**
     * The date at which this data point was recorded
     */
    private final Date date;

    /**
     * The current value of the pressure in the prep chamber
     */
    private final Float value;

    /**
     * @param date The date at which the pressure was measured
     * @param value The value of the pressure
     */
    public PressureDataPoint(Date date, Float value){
        this.date = date;
        this.value = value;
    }

    /**
     * @return The value of the pressure
     */
    @Override
    public Float getValue(){
        return this.value;
    }

    /**
     * @return the date at which the pressure was measured
     */
    @Override
    public Date getDate(){
        return this.date;
    }
}
