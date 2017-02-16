package ui.controllers;

import devices.PressureGauge;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import kernel.Kernel;
import kernel.views.DeviceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ui.Controller;

import java.time.Duration;

/**
 * Responsible for updating the results panel with results of the bakeout
 */
@Controller
public class ResultController {

    private static final Duration pressurePollingInterval =
            Duration.ofMillis(500);

    @Autowired
    private Kernel kernel;

    @FXML private volatile Text reportedPressure;

    @FXML public void initialize(){
        Thread reportingThread = new LivePressureReporter(
                kernel.getDeviceRegistryView(), pressurePollingInterval
        );

        reportingThread.start();
    }


    private class LivePressureReporter extends Thread {
        private final Logger log = LoggerFactory.getLogger(
                LivePressureReporter.class
        );

        private final DeviceRegistry registry;
        private final Duration pollingInterval;

        private final Paint initialFill = reportedPressure.getFill();

        public LivePressureReporter(
                DeviceRegistry registry,
                Duration pollingInterval
        ){
            this.registry = registry;
            this.pollingInterval = pollingInterval;
        }

        @Override
        public void run(){
            log.info("Started pressure reporting thread");
            while (true) {
                if (hasPressureGauge()) {
                    log.debug("Reporting pressure");
                    setMessageToWrite();
                    resetTextColor();
                }
                sleepForPollingInterval();
            }
        }

        private Boolean hasPressureGauge(){
            return kernel.getDeviceRegistryView().hasPressureGauge();
        }

        private void sleepForPollingInterval(){
            log.debug("Command to sleep issued. Going to sleep");
            try {
                sleep(pollingInterval.toMillis());
            } catch (InterruptedException error){
                log.debug(
                        "Live pressure reporter interrupted. Calling handler"
                );
                handleInterruptedException(error);
            }
        }

        private void handleInterruptedException(InterruptedException error){
            log.info(
                    "Recieved InterruptedException {}. Closing thread.",
                    error
            );
        }

        private void setMessageToWrite(){
            reportedPressure.setText(getMessageToWrite());
        }

        private String getMessageToWrite(){
            PressureGauge gauge = registry.getPressureGauge();
            String message = "Not available";

            try {
                message = gauge.getPressure().toString();
            } catch (Exception error) {
                handleException(error);
            }

            return message;
        }

        private void handleException(Throwable error){
            log.warn(
                    "Attempting to get pressure returned error {}", error
            );

            setTextToRed();
        }

        private void setTextToRed(){
            reportedPressure.setFill(Color.RED);
        }

        private void resetTextColor(){
            reportedPressure.setFill(initialFill);
        }
    }
}
