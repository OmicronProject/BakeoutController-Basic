package unit.ui.controllers.results_controller;


import javafx.scene.chart.XYChart;
import javafx.scene.text.Text;
import kernel.models.variables.PressureDataPoint;
import kernel.views.variables.Pressure;
import org.junit.Before;
import org.junit.Test;
import ui.controllers.ResultController;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Contains tests for
 * {@link ui.controllers.ResultController.PressureChangeHandler#onChange(Pressure)}
 */
public final class OnChange extends ResultControllerTestCase {
        private static final Date dateOfPressure = new Date();

    private static final Float measuredPressure = 1.5f;

    private Pressure dataPoint;

    private ResultController.PressureChangeHandler handler;

    private static final Text report = new Text();

    private static final XYChart.Series<String, Float> pressureSeries =
            new XYChart.Series<>();

    @Before
    public void setDataPoint(){
        dataPoint = new PressureDataPoint(dateOfPressure, measuredPressure);
    }

    @Before
    public void setHandler(){
        handler = new ResultController.PressureChangeHandler(
                report, pressureSeries
        );
    }

    @Test
    public void onChange(){
        handler.onChange(dataPoint);
        assertEquals(measuredPressure.toString(), report.getText());
        assertEquals(
                measuredPressure,
                pressureSeries.getData().get(0).getYValue() / 1e10,
                1e-6
        );
    }

}
