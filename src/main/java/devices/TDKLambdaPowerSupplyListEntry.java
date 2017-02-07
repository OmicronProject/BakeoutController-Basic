package devices;

import devices.factories.TDKLambdaPowerSupplyFactory;

/**
 * Created by mkononen on 06/02/17.
 */
public class TDKLambdaPowerSupplyListEntry extends StandaloneDeviceListEntry {
    private static final Class<TDKLambdaPowerSupply> deviceType =
            TDKLambdaPowerSupply.class;

    private static final String deviceName = "TDK Lambda Power Supply";

    private static final TDKLambdaPowerSupplyFactory deviceFactory = new
            TDKLambdaPowerSupplyFactory();

    public TDKLambdaPowerSupplyListEntry(){
        super(deviceType, deviceName, deviceFactory);
    }
}
