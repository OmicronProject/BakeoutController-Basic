package ui.controllers;

import exceptions.NegativeDurationException;
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

    private static final Integer maximumNumberOfDataPointsInCharts = 30;

    /**
     * The kernel to which this controller is attached
     */
    @Autowired private Kernel kernel;

    /**
     * The field to which the pressure from the PVCi pressure gauge is written
     */
    @FXML private volatile Text reportedPressure;

    /**
     * The field to which the voltage is written
     */
    @FXML private volatile Text reportedVoltage;

    /**
     * The chart where the pressure is written
     */
    @FXML private LineChart<String, Float> pressureChart;

    /**
     * The chart where the voltage is written
     */
    @FXML private LineChart<String, Float> voltageChart;

    /**
     * The data series to which pressure data is written
     */
    private XYChart.Series<String, Float> pressureSeries;

    /**
     * The data series to which the voltage is written
     */
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
        } catch (NegativeDurationException error){
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
        } catch (NegativeDurationException error){
            error.printStackTrace();
        }

        provider.addOnChangeListener(new PressureChangeHandler(this));
    }

    /**
     * Set up the voltage series
     */
    private void configureVoltageSeries(){
        voltageSeries = new XYChart.Series<>();
        voltageSeries.setName("Voltage / V");

        voltageChart.getData().add(voltageSeries);
    }

    /**
     * Set up the pressure series
     */
    private void configurePressureSeries(){
        pressureSeries = new XYChart.Series<>();
        pressureSeries.setName("Pressure / mBar");

        pressureChart.getData().add(pressureSeries);
    }

    /**
     * Recieves a change event from the {@link VoltageProvider}, and carries
     * out the required updates to synchronize the UI with this change
     */
    private class VoltageChangeHandler implements
            VariableChangeEventListener<Voltage> {

        /**
         * The executor in which the change handler is running. This
         * executor must be able to run tasks on the JavaFX UI thread.
         */
        private Executor taskExecutor;

        /**
         * @param taskExecutor The task runner in which the change handler
         *                     will run
         */
        public VoltageChangeHandler(Executor taskExecutor){
            this.taskExecutor = taskExecutor;
        }

        /**
         * @param newVoltage The new value of the voltage
         */
        @Override
        public void onChange(Voltage newVoltage){
            if (voltageChart.isVisible() && reportedVoltage.isVisible()) {
                Task<Void> task = new UpdateVoltageTask(newVoltage);
                this.taskExecutor.execute(task);
            }
        }
    }

    /**
     * Handles changes to the pressure
     */
    private class PressureChangeHandler implements
            VariableChangeEventListener<Pressure> {

        /**
         * The task executor for the JavaFX UI thread
         */
        private Executor taskExecutor;

        /**
         * @param taskExecutor The executor for the JavaFX thread
         */
        public PressureChangeHandler(Executor taskExecutor){
            this.taskExecutor = taskExecutor;
        }

        /**
         * Handles the change event
         * @param newPressure The new value of the pressure
         */
        @Override
        public void onChange(Pressure newPressure){
            if (pressureChart.isVisible() && reportedPressure.isVisible()) {
                Task<Void> task = new UpdatePressureTask(newPressure);
                this.taskExecutor.execute(task);
            }
        }
    }

    /**
     * Handles changes to the pressure on the JavaFX thread
     */
    private class UpdatePressureTask extends Task<Void> {
        /**
         * The new value of the pressure
         */
        private Pressure newPressure;

        /**
         * @param newPressure The new pressure
         */
        public UpdatePressureTask(Pressure newPressure){
            this.newPressure = newPressure;
        }

        /**
         * Update the pressure text field and write the value to the chart
         * @return null
         */
        @Override
        public Void call(){
            updatePressureText();
            updatePressureChart();
            return null;
        }

        /**
         * Sets the text to the new pressure
         */
        private void updatePressureText(){
            reportedPressure.setText(newPressure.getValue().toString());
        }

        /**
         * Update the chart
         * @throws DateTimeException If the date cannot be formatted to the
         * required format string
         */
        private void updatePressureChart() throws DateTimeException {
            String xValue = getDateFromPressure();
            Float yValue = newPressure.getValue();
            XYChart.Data<String, Float> dataPoint = new XYChart.Data<>(
                    xValue, yValue);

            pressureSeries.getData().add(dataPoint);
            ResultController.checkSeriesSizeCorrect(pressureSeries);
        }

        /**
         * @return The date string from the date at which the pressure was
         * recorded
         * @throws DateTimeException if the date cannot be formatted
         */
        @NotNull
        private String getDateFromPressure() throws DateTimeException {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            return formatter.format(newPressure.getDate());
        }
    }

    /**
     * The task to update the voltage, to be executed on the javaFX UI thread
     */
    private class UpdateVoltageTask extends Task<Void> {
        /**
         * The new value of the voltage
         */
        private Voltage newVoltage;

        /**
         * @param newVoltage The new voltage value
         */
        public UpdateVoltageTask(Voltage newVoltage){
            this.newVoltage = newVoltage;
        }

        /**
         * Update the text and chart for the new voltage
         * @return null
         */
        @Override
        public Void call(){
            updateVoltageText();
            updateVoltageChart();
            return null;
        }

        /**
         * Set the place for voltage to be reported to the new value
         */
        private void updateVoltageText(){
            reportedVoltage.setText(newVoltage.getValue().toString());
        }

        /**
         * Write the new voltage value to the chart
         */
        private void updateVoltageChart(){
            String xValue = getDateFromVoltage();
            Float yValue = newVoltage.getValue().floatValue();

            XYChart.Data<String, Float> dataPoint = new XYChart.Data<>(
                    xValue, yValue
            );

            voltageSeries.getData().add(dataPoint);
            ResultController.checkSeriesSizeCorrect(voltageSeries);
        }

        /**
         * @return The formatted date for the voltage
         */
        @NotNull
        private String getDateFromVoltage(){
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            return formatter.format(newVoltage.getDate());
        }
    }

    /**
     * Check that the data series is not too big. If it is, remove the first
     * element.
     *
     * @param series The data series whose size is to be checked
     */
    private static void checkSeriesSizeCorrect(XYChart.Series series){
        Integer dataPointNumber = series.getData().size();

        if (dataPointNumber > maximumNumberOfDataPointsInCharts){
            series.getData().remove(0);
        }
    }
}
