package unit.ui.controllers.results_controller;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import kernel.models.variables.VoltageDataPoint;
import kernel.views.variables.Voltage;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertThat;


/**
 * Replicates bug where variable change in a hidden tab in a chart causes an
 * IllegalStateException to be thrown
 */
public final class TabSwitchOnChange extends ResultControllerTestCase {

    private static final Voltage newVoltageDataPoint = new VoltageDataPoint(
            new Date(), 10.0
    );

    @Test
    public void testTabSwitchOnChange(){
        clickOn(queryForPressureTab);
        mockVoltageProvider.addValue(newVoltageDataPoint);
        clickOn(queryForVoltageTab);

        List<Double> pressuresOnChart = getVoltagesOnChart();

        assertThat(pressuresOnChart, contains(newVoltageDataPoint.getValue()));
    }

    private List<Double> getVoltagesOnChart(){
        List<Double> voltages = new ArrayList<>();

        LineChart<String, Double> voltageChart = lookup
                (queryForPressureChart).query();

        LineChart.Series<String, Double> voltageSeries = voltageChart
                .getData().get(0);

        for (XYChart.Data<String, Double> voltageDataPoint: voltageSeries
                .getData()){

            voltages.add(voltageDataPoint.getYValue());
        }

        return voltages;
    }
}
