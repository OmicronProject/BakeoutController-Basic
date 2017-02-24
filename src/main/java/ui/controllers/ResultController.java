package ui.controllers;

import exceptions.NonNegativeDurationException;
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

/**
 * Responsible for updating the results panel with results of the bakeout
 */
@Controller
public class ResultController {

    private static final Logger log = LoggerFactory.getLogger
            (ResultController.class);

    private static final Duration pressurePollingInterval =
            Duration.ofMillis(1000);

    private static final Duration voltagePollingInterval =
            Duration.ofMillis(1000);

    @Autowired
    private Kernel kernel;

    @FXML private volatile Text reportedPressure;

    @FXML private volatile Text reportedVoltage;

    @FXML private LineChart<String, Float> pressureChart;

    @FXML private LineChart<String, Float> voltageChart;

    private XYChart.Series<String, Float> pressureSeries;

    private XYChart.Series<String, Float> voltageSeries;

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

    private void configureVoltageProvider(){
        VariableProvider<Voltage> provider = kernel.getVariableProvidersView()
                .getVoltageProvider();

        try {
            provider.setPollingInterval(voltagePollingInterval);
        } catch (NonNegativeDurationException error){
            error.printStackTrace();
        }

        provider.addOnChangeListener(
                new VoltageChangeHandler(reportedVoltage, voltageSeries)
        );

        configureVoltageSeries();
    }

    private void configurePressureProvider(){
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

    private abstract static class ChangeHandler<T extends Variable> implements
            VariableChangeEventListener<T> {
        protected final Logger log = LoggerFactory.getLogger(
                ChangeHandler.class
        );

        private final Text report;
        private final XYChart.Series<String, Float> variableSeries;

        public ChangeHandler(Text report, XYChart.Series<String, Float>
                variableSeries){
            this.report = report;
            this.variableSeries = variableSeries;

            log.debug(
                    "Created Change handler of type {} with variable series " +
                            "{}", getVariableType(), variableSeries);
        }

        @NotNull
        protected abstract String getVariableType();

        protected Number getValueMultiplier(){
            return 1.0;
        }

        @Override
        public void onChange(T newVariable){
            log.debug("Controller for variable {} received change event for " +
                    "variable {}", getVariableType(), newVariable.toString());

            if (this.report != null){
                this.report.setText(getMessageToWrite(newVariable));
            } else {
                log.info("Variable Reporting field for variable type {} is " +
                        "null", getVariableType());
            }

            updateChart(newVariable);
        }

        private String getMessageToWrite(T variable){
            return variable.getValue().toString();
        }

        private void updateChart(T newVariable){

            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

            String date;
            try {
                date = formatter.format(newVariable.getDate());
            } catch (DateTimeException error){
                log.error("Formatter threw error", error);
                return;
            }

            Float valueFromData;
            try {
                valueFromData = ((Number) newVariable.getValue()).floatValue();
            } catch (ClassCastException error){
                log.error("Casting to Number yielded error", error);
                valueFromData = 0.0f;
            }

            Float value = getValueMultiplier().floatValue() * valueFromData;

            this.variableSeries.getData().add(
                    new XYChart.Data<>(date, value)
            );

            checkSizeListCorrect();
        }

        private void checkSizeListCorrect(){
            Integer dataPointNumber = this.variableSeries.getData().size();

            if (dataPointNumber > 30){
                this.variableSeries.getData().remove(0);
            }
        }
    }

    public static class PressureChangeHandler extends ChangeHandler<Pressure> {
        public PressureChangeHandler(
                Text pressureText,
                XYChart.Series<String, Float> pressureSeries
        ) {
            super(pressureText, pressureSeries);
        }

        @Override
        @NotNull
        protected String getVariableType() {
            return "Pressure";
        }

        @Override
        protected Number getValueMultiplier(){
            return 1e10;
        }
    }

    public static class VoltageChangeHandler extends ChangeHandler<Voltage> {
        public VoltageChangeHandler(
                Text voltageText,
                XYChart.Series<String, Float> voltageSeries
        ){
            super(voltageText, voltageSeries);
        }

        @Override
        @NotNull
        protected String getVariableType(){
            return "Voltage";
        }
    }
}
