package unit.devices.factories.tdk_lambda_power_supply_factory;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by mkononen on 06/02/17.
 */
public final class GetSerialPort extends TDKLambdaPowerSupplyFactoryTestCase {
    @Before
    public void setSerialPort(){
        deviceFactory.setSerialPort(mockSerialPort);
    }

    @Test
    public void getSerialPort(){
        assertEquals(mockSerialPort, deviceFactory.getSerialPort());
    }
}
