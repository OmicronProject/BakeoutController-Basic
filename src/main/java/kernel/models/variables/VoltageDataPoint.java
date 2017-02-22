package kernel.models.variables;

import kernel.views.variables.Voltage;

import java.util.Date;

/**
 * Created by mkononen on 22/02/17.
 */
public class VoltageDataPoint implements Voltage {
    private final Date date;
    private final Double value;

    public VoltageDataPoint(Date date, Double value){
        this.date = date;
        this.value = value;
    }

    @Override
    public Date getDate(){
        return this.date;
    }

    @Override
    public Double getValue(){
        return this.value;
    }
}
