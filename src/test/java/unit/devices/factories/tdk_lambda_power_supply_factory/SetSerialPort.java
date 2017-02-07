package unit.devices.factories.tdk_lambda_power_supply_factory;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Created by mkononen on 06/02/17.
 */
public final class SetSerialPort extends TDKLambdaPowerSupplyFactoryTestCase {

    @Test
    public void setSerialPort(){
        deviceFactory.setSerialPort(mockSerialPort);
        assertEquals(mockSerialPort, deviceFactory.getSerialPort());
    }
}
