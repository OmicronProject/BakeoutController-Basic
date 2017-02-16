package device_readers;

import java.util.Date;

/**
 * Created by mkononen on 16/02/17.
 */
public interface PressureDataPoint {
    Float getPressure();

    Date getDate();
}
