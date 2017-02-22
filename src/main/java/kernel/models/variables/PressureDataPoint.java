package kernel.models.variables;

import kernel.views.variables.Pressure;

import java.util.Date;

/**
 * Contains a standalone implementation of the Pressure variable
 */
public class PressureDataPoint implements Pressure {
    private final Date date;
    private final Float value;

    public PressureDataPoint(Date date, Float value){
        this.date = date;
        this.value = value;
    }

    @Override
    public Float getValue(){
        return this.value;
    }

    @Override
    public Date getDate(){
        return this.date;
    }
}
