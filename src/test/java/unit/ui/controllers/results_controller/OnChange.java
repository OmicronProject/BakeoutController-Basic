package unit.ui.controllers.results_controller;


import javafx.scene.text.Text;
import kernel.models.variables.PressureDataPoint;
import kernel.views.variables.Pressure;
import kernel.views.variables.VariableChangeEventListener;
import org.junit.Before;
import org.junit.Test;
import ui.controllers.ResultController;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Contains tests for {@link ui.controllers.ResultController#onChange(Pressure)}
 */
public final class OnChange extends ResultControllerTestCase {
        private static final Date dateOfPressure = new Date();

    private static final Float measuredPressure = 1.5f;

    private Pressure dataPoint;

    private ResultController.PressureChangeHandler handler;

    private static final Text report = new Text();

    @Before
    public void setDataPoint(){
        dataPoint = new PressureDataPoint(dateOfPressure, measuredPressure);
    }

    @Before
    public void setHandler(){
        handler = new ResultController.PressureChangeHandler(report);
    }

    @Test
    public void onChange(){
        handler.onChange(dataPoint);

        assertEquals(measuredPressure.toString(), report.getText());
    }

}
