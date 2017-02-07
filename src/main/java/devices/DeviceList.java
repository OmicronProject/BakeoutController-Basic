package devices;

import kernel.views.DeviceListEntry;
import kernel.views.DeviceReporter;

import java.util.List;
import java.util.Vector;

/**
 * Implements a list that states which devices are available to this
 * application
 */
public final class DeviceList implements DeviceReporter {
    private final List<DeviceListEntry> deviceList;

    public DeviceList(){
        deviceList = new Vector<>();
        deviceList.add(
                new TDKLambdaPowerSupplyListEntry()
        );
    }

    @Override public List<DeviceListEntry> getRS232Devices(){
        return deviceList;
    }
}
