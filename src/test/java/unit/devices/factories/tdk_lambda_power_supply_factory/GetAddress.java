package unit.devices.factories.tdk_lambda_power_supply_factory;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by mkononen on 06/02/17.
 */
public final class GetAddress extends TDKLambdaPowerSupplyFactoryTestCase {
    private final Integer newAddress = 11;

    @Before
    public void setAddress(){
        deviceFactory.setAddress(newAddress);
    }
    @Test
    public void getAddress(){
        assertEquals(newAddress, deviceFactory.getAddress());
    }
}
