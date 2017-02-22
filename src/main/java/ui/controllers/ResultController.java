package ui.controllers;

import exceptions.NonNegativeDurationException;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.text.Text;
import kernel.Kernel;
import kernel.views.variables.Pressure;
import kernel.views.variables.VariableChangeEventListener;
import kernel.views.variables.VariableProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ui.Controller;

import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.Duration;

/**
 * Responsible for updating the results panel with results of the bakeout
 */
@Controller
public class ResultController {

    private static final Logger log = LoggerFactory.getLogger
            (ResultController.class);

    private static final Duration pressurePollingInterval =
            Duration.ofMillis(1000);

    @Autowired
    private Kernel kernel;

    @FXML private volatile Text reportedPressure;

    @FXML private LineChart<String, Float> pressureChart;

    private XYChart.Series<String, Float> pressureSeries;

    @FXML public void handleRefreshButtonClicked(){
        if (kernel.getVariableProvidersView().hasPressureProvider()){
            log.info("Found pressure provider. Using this for reports");
            configureVariableProvider();
        }
    }

    @FXML public void initialize(){
        if (kernel.getVariableProvidersView().hasPressureProvider()){
            log.info("Found pressure provider. Reporting");
            configureVariableProvider();
        }

        configurePressureSeries();
    }

    private void configureVariableProvider(){
        VariableProvider<Pressure> provider = kernel
                .getVariableProvidersView().getPressureProvider();

        try {
            provider.setPollingInterval(pressurePollingInterval);
        } catch (NonNegativeDurationException error){
            error.printStackTrace();
        }

        provider.addOnChangeListener(
                new PressureChangeHandler(reportedPressure, pressureSeries)
        );
    }

    private void configurePressureSeries(){
        pressureSeries = new XYChart.Series<>();
        pressureSeries.setName("Pressure / mBar");

        pressureChart.getData().add(pressureSeries);
    }

    public static class PressureChangeHandler implements
            VariableChangeEventListener<Pressure> {
        private static final Logger log = LoggerFactory.getLogger
                (PressureChangeHandler.class);

        private final Text pressureText;
        private final XYChart.Series<String, Float> pressureSeries;

        public PressureChangeHandler(
                Text pressureText,
                XYChart.Series<String, Float> pressureSeries){
            this.pressureText = pressureText;
            this.pressureSeries = pressureSeries;
        }

        @Override
        public void onChange(Pressure newPressure){
            log.debug("ResultController received pressure change event");

            if (this.pressureText != null) {
                this.pressureText.setText(getMessageToWrite(newPressure));
            } else {
                log.info("Pressure reporting filed is null");
            }

            updateChart(newPressure);
        }

        private String getMessageToWrite(Pressure newPressure){
            return newPressure.getValue().toString();
        }

        private void updateChart(Pressure newPressure){

            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

            String date;
            try {
                date = formatter.format(newPressure.getDate());
            } catch (DateTimeException error){
                log.error("Formatter threw error", error);
                return;
            }

            Double value = newPressure.getValue() * 1e10;

            this.pressureSeries.getData().add(
                    new XYChart.Data<>(date, value.floatValue())
            );

            checkSizeListCorrect();
        }

        private void checkSizeListCorrect(){
            Integer dataPointNumber = this.pressureSeries.getData().size();

            if (dataPointNumber > 30){
                this.pressureSeries.getData().remove(0);
            }
        }
    }

}
