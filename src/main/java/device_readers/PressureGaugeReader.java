package device_readers;

import devices.PressureGauge;

import java.time.Duration;
import java.util.List;

/**
 * Created by mkononen on 16/02/17.
 */
public interface PressureGaugeReader extends Runnable {
    Boolean canRun();

    List<PressureDataPoint> getPressureReadings();

    Duration getPollingInterval();

    void setPollingInterval(Duration pollingInterval);

    Duration getRecordedHistory();

    void setRecordedHistory(Duration history);

    Integer getNumberOfPointsInBuffer();

    void setNumberOfPointsInBuffer(Integer numberOfPoints);

    PressureGauge getPressureGauge();

    void setPressureGauge(PressureGauge gauge);
}