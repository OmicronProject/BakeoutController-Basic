package kernel.models;

import exceptions.StopIteration;
import exceptions.UnableToRunControlAlgorithmException;
import kernel.Kernel;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;
import java.util.Optional;

/**
 * Created by mkononen on 23/02/17.
 */
public class VoltageSetPointAlgorithm implements kernel.controllers.VoltageSetPointAlgorithm {
    private static final Logger log = LoggerFactory.getLogger
            (VoltageSetPointAlgorithm.class);
    private Double desiredVoltage = 28.0;
    private Float pressureUpperBound = 1e-9f;

    private Integer maximumIterations = 100;

    private Boolean isRunning = Boolean.FALSE;

    private Kernel kernel;

    @Override
    public void run() throws UnableToRunControlAlgorithmException {
        assertCanRun();
        isRunning = Boolean.TRUE;

        VoltageSetPointTask task;

        try {
            task = new VoltageSetPointTask(
                kernel, maximumIterations, pressureUpperBound
            );
        } catch (IOException error){
            log.error("Unable to create voltage setpoint task", error);
            throw new UnableToRunControlAlgorithmException(
                    "Task creation threw IOException"
            );
        }

        kernel.getTaskRunner().execute(task);
    }

    @Override
    public Kernel getKernel(){
        return kernel;
    }

    @Override
    public void setKernel(Kernel kernel){
        this.kernel = kernel;
    }

    @Override
    public Boolean isRunning(){
        return isRunning;
    }

    @Override
    public Double getDesiredVoltage(){
        return desiredVoltage;
    }

    @Override
    public void setDesiredVoltage(Double desiredVoltage){
        this.desiredVoltage = desiredVoltage;
    }

    @Override
    public Float getPressureUpperBound(){
        return pressureUpperBound;
    }

    @Override
    public void setPressureUpperBound(Float pressureUpperBound){
        this.pressureUpperBound = pressureUpperBound;
    }

    @Override
    public Integer getMaximumIterations(){
        return maximumIterations;
    }

    @Override
    public void setMaximumIterations(Integer maximumIterations){
        this.maximumIterations = maximumIterations;
    }

    private void assertCanRun() throws UnableToRunControlAlgorithmException {
        Boolean canRun = hasKernel() &&
                hasPowerSupply() &&
                hasPressureGauge();

        if (!canRun){
            throw new UnableToRunControlAlgorithmException("Unable to start " +
                    "control algorithm");
        }
    }

    @NotNull
    @Contract(pure = true)
    private Boolean hasKernel(){
        return this.kernel != null;
    }

    @NotNull
    private Boolean hasPowerSupply(){
        if (hasKernel()){
            return this.kernel.getDeviceRegistryView().hasPowerSupply();
        } else {
            return Boolean.FALSE;
        }
    }

    private Boolean hasPressureGauge(){
        if (hasKernel()){
            return this.kernel.getDeviceRegistryView().hasPressureGauge();
        } else {
            return Boolean.FALSE;
        }
    }

    @Override
    public Double getProgress(){
        Double voltage;
        try {
            voltage = kernel.getDeviceRegistryView().getPowerSupply()
                    .getMeasuredVoltage();
        } catch (IOException error){
            voltage = 0.0;
        }

        return voltage/desiredVoltage;
    }

    public static class VoltageSetPointTask implements Runnable {
        private static final Logger log = LoggerFactory.getLogger
                (VoltageSetPointTask.class);

        private final Kernel kernel;

        private final Integer maximumIterations;

        private final Double startingVoltage;

        private final Double voltageIncrement = 0.1;

        private final Duration waitTime = Duration.ofSeconds(10);

        private final Float maximumPressure;

        public VoltageSetPointTask(
                Kernel kernel, Integer maximumIterations,
                Float maximumPressure
        )
                throws IOException {
            this.kernel = kernel;
            this.maximumIterations = maximumIterations;
            this.maximumPressure = maximumPressure;

            startingVoltage = kernel.getDeviceRegistryView().getPowerSupply
                    ().getMeasuredVoltage();
        }

        @Override
        public void run(){
            log.debug("Started setpoint task, with starting voltage {}",
                    startingVoltage);
            Double measuredVoltage = startingVoltage;
            Integer iterationNumber = 0;

            turnPowerSupplyOn();

            while (shouldLoop(measuredVoltage, iterationNumber)) {
                try {
                    measuredVoltage = getVoltage();
                    setDeviceVoltage(measuredVoltage + voltageIncrement);
                } catch (IOException error){
                    handleIOException(error);
                    break;
                }

                try {
                    waitForPressureToDropBelowMinimum();
                } catch (StopIteration error){
                    break;
                }

                try {
                    waitForActionInterval();
                } catch (InterruptedException error){
                    log.info("Voltage setter interrupted");
                    break;
                }
                iterationNumber++;
            }

            turnPowerSupplyOff();
        }

        @NotNull
        @Contract(pure = true)
        private Boolean shouldLoop(
                Double measuredVoltage, Integer iterationNumber
        ){
            Boolean hasExceededIterations =
                    iterationNumber <= maximumIterations;

            Boolean hasExceededDesiredVoltage = measuredVoltage >
                    startingVoltage;

            return hasExceededDesiredVoltage || hasExceededIterations;
        }

        private Double getVoltage() throws IOException {
            return kernel.getDeviceRegistryView().getPowerSupply()
                    .getMeasuredVoltage();
        }

        private void turnPowerSupplyOn() {
            try {
                kernel.getDeviceRegistryView().getPowerSupply().outputOn();
                log.debug("Power supply turned on.");
            } catch (IOException error){
                log.error("Unable to switch on power supply.", error);
            }
        }

        private void turnPowerSupplyOff(){
            try {
                kernel.getDeviceRegistryView().getPowerSupply().outputOff();
                log.debug("Power supply turned off.");
            } catch (IOException error) {
                log.error("Unable to switch off power supply.", error);
            }
        }

        private void setDeviceVoltage(Double newVoltage) throws IOException {
            log.debug("Setting power supply voltage to {}", newVoltage);
            kernel.getDeviceRegistryView().getPowerSupply().setVoltage(newVoltage);
        }

        private void waitForActionInterval() throws InterruptedException {
            log.debug("Waiting for {} milliseconds", waitTime.toMillis());
            Thread.sleep(waitTime.toMillis());
        }

        private void waitForPressureToDropBelowMinimum() throws StopIteration {
            Integer iterationNumber = 0;
            Optional<Float> pressure = getMeasuredPressure();

            if (!pressure.isPresent()){
                log.debug("Returned pressure is null. Resuming algorithm.");
                throw new StopIteration();
            }

            while (
                    pressure.get() > maximumPressure &&
                    iterationNumber < maximumIterations) {
                try {
                    waitForActionInterval();
                } catch (InterruptedException error) {
                    break;
                }

                pressure = getMeasuredPressure();
                iterationNumber++;
                log.debug("Measured pressure {}, iteration {}",
                        pressure.get(), iterationNumber);
            }
        }

        private Optional<Float> getMeasuredPressure(){
            try {
                return Optional.of(
                        kernel.getDeviceRegistryView().getPressureGauge()
                                .getPressure()
                );
            } catch (Exception error){
                log.error("Attempting to get pressure returned error {}",
                        error);
                return Optional.empty();
            }
        }

        private static void handleIOException(IOException error){
            log.error("Attempt to get voltage threw IOException",
                    error);
        }
    }
}
