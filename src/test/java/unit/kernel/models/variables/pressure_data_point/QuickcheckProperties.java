package unit.kernel.models.variables.pressure_data_point;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import kernel.models.variables.PressureDataPoint;
import kernel.views.variables.Pressure;
import org.junit.runner.RunWith;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Contains unit tests for the data point checkable by QuickCheck
 */
@RunWith(JUnitQuickcheck.class)
public final class QuickcheckProperties extends PressureDataPointTestCase {
    @Property
    public void getValue(Date date, Float value){
        Pressure dataPoint = new PressureDataPoint(date, value);

        assertEquals(date, dataPoint.getDate());
    }

    @Property
    public void getDate(Date date, Float value){
        Pressure dataPoint = new PressureDataPoint(date, value);
        assertEquals(value, dataPoint.getValue());
    }
}
