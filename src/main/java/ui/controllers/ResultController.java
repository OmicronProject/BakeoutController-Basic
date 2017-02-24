package ui.controllers;

import exceptions.NonNegativeDurationException;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.text.Text;
import kernel.Kernel;
import kernel.views.variables.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ui.Controller;

import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.Duration;
import java.util.concurrent.Executor;

/**
 * Responsible for updating the results panel with results of the bakeout
 */
@Controller
public class ResultController implements Executor {

    /**
     * The log for the controller to use
     */
    private static final Logger log = LoggerFactory.getLogger
            (ResultController.class);

    /**
     * The duration between calls to get the pressure from the Pressure
     * VariableProvider
     */
    private static final Duration pressurePollingInterval =
            Duration.ofMillis(1000);

    /**
     * The duration between calls to get the voltae
     */
    private static final Duration voltagePollingInterval =
            Duration.ofMillis(1000);

    /**
     * The application kernel
     */
    @Autowired
    private Kernel kernel;

    @FXML private volatile Text reportedPressure;

    @FXML private volatile Text reportedVoltage;

    @FXML private LineChart<String, Float> pressureChart;

    @FXML private LineChart<String, Float> voltageChart;

    private XYChart.Series<String, Float> pressureSeries;

    private XYChart.Series<String, Float> voltageSeries;

    /**
     * Find the pressure and voltage providers, and attach a listener to
     * both of them
     */
    @FXML public void initialize(){
        configurePressureSeries();
        if (kernel.getVariableProvidersView().hasPressureProvider()){
            log.info("Found pressure provider. Reporting");
            configurePressureProvider();
        }

        configureVoltageSeries();
        if (kernel.getVariableProvidersView().hasVoltageProvider()){
            log.info("Found voltage provider. Reporting");
            configureVoltageProvider();
        }
    }

    /**
     * Called if the refresh button is clicked on the UI
     */
    @FXML
    public void handleRefreshButtonClicked(){
        if (kernel.getVariableProvidersView().hasPressureProvider()){
            log.info("Found pressure provider. Using this for reports");
            configurePressureProvider();
        }
        if (kernel.getVariableProvidersView().hasVoltageProvider()){
            log.info("Found voltage provider. Using this for reports");
            configureVoltageProvider();
        }
    }

    /**
     * Run the command given to it on the JavaFX application thread
     * @param command The command to run
     */
    @Override
    public void execute(@NotNull Runnable command){
        Platform.runLater(command);
    }

    /**
     * Set up the voltage provider
     */
    private void configureVoltageProvider(){
        VariableProvider<Voltage> provider = kernel.getVariableProvidersView()
                .getVoltageProvider();

        try {
            provider.setPollingInterval(voltagePollingInterval);
        } catch (NonNegativeDurationException error){
            error.printStackTrace();
        }

        provider.addOnChangeListener(new VoltageChangeHandler(this));
    }

    /**
     * Set up the pressure provider
     */
    private void configurePressureProvider(){
        VariableProvider<Pressure> provider = kernel
                .getVariableProvidersView().getPressureProvider();

        try {
            provider.setPollingInterval(pressurePollingInterval);
        } catch (NonNegativeDurationException error){
            error.printStackTrace();
        }

        provider.addOnChangeListener(new PressureChangeHandler(this));
    }

    private void configureVoltageSeries(){
        voltageSeries = new XYChart.Series<>();
        voltageSeries.setName("Voltage / V");

        voltageChart.getData().add(voltageSeries);
    }

    private void configurePressureSeries(){
        pressureSeries = new XYChart.Series<>();
        pressureSeries.setName("Pressure / mBar");

        pressureChart.getData().add(pressureSeries);
    }

    private class VoltageChangeHandler implements
            VariableChangeEventListener<Voltage> {
        private Executor taskExecutor;

        public VoltageChangeHandler(Executor taskExecutor){
            this.taskExecutor = taskExecutor;
        }

        @Override
        public void onChange(Voltage newVoltage){
            Task<Void> task = new UpdateVoltageTask(newVoltage);
            this.taskExecutor.execute(task);
        }
    }

    private class PressureChangeHandler implements
            VariableChangeEventListener<Pressure> {
        private Executor taskExecutor;

        public PressureChangeHandler(Executor taskExecutor){
            this.taskExecutor = taskExecutor;
        }

        @Override
        public void onChange(Pressure newPressure){
            Task<Void> task = new UpdatePressureTask(newPressure);
            this.taskExecutor.execute(task);
        }
    }


    private class UpdatePressureTask extends Task<Void> {
        private Pressure newPressure;

        public UpdatePressureTask(Pressure newPressure){
            this.newPressure = newPressure;
        }

        @Override
        public Void call(){
            updatePressureText();
            updatePressureChart();
            return null;
        }

        private void updatePressureText(){
            reportedPressure.setText(newPressure.getValue().toString());
        }

        private void updatePressureChart() throws DateTimeException {
            String xValue = getDateFromPressure();
            Float yValue = newPressure.getValue();
            XYChart.Data<String, Float> dataPoint = new XYChart.Data<>(
                    xValue, yValue);

            pressureSeries.getData().add(dataPoint);
            ResultController.checkSeriesSizeCorrect(pressureSeries);
        }

        @NotNull
        private String getDateFromPressure() throws DateTimeException {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            return formatter.format(newPressure.getDate());
        }
    }

    private class UpdateVoltageTask extends Task<Void> {
        private Voltage newVoltage;

        public UpdateVoltageTask(Voltage newVoltage){
            this.newVoltage = newVoltage;
        }

        @Override
        public Void call(){
            updateVoltageText();
            updateVoltageChart();
            return null;
        }

        private void updateVoltageText(){
            reportedVoltage.setText(newVoltage.getValue().toString());
        }

        private void updateVoltageChart(){
            String xValue = getDateFromVoltage();
            Float yValue = newVoltage.getValue().floatValue();

            XYChart.Data<String, Float> dataPoint = new XYChart.Data<>(
                    xValue, yValue
            );

            voltageSeries.getData().add(dataPoint);
            ResultController.checkSeriesSizeCorrect(voltageSeries);
        }

        private String getDateFromVoltage(){
            return newVoltage.getDate().toString();
        }
    }

    private static void checkSeriesSizeCorrect(XYChart.Series series){
        Integer dataPointNumber = series.getData().size();

        if (dataPointNumber > 30){
            series.getData().remove(0);
        }
    }
}
