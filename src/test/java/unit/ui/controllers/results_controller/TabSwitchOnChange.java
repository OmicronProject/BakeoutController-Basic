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

        waitForFXAppToRun();

        List<Double> voltagesOnChart = getVoltagesOnChart();

        assertThat(voltagesOnChart, contains(newVoltageDataPoint.getValue()
                .floatValue()));
    }

    private static void waitForFXAppToRun(){
        try {
            Thread.sleep(100);
        } catch (InterruptedException error){
            // Return to finish test
        }
    }

    private List<Double> getVoltagesOnChart(){
        List<Double> voltages = new ArrayList<>();

        LineChart<String, Double> voltageChart = lookup
                (queryForVoltageChart).query();

        LineChart.Series<String, Double> voltageSeries = voltageChart
                .getData().get(0);

        for (XYChart.Data<String, Double> voltageDataPoint: voltageSeries
                .getData()){

            voltages.add(voltageDataPoint.getYValue());
        }

        return voltages;
    }
}
