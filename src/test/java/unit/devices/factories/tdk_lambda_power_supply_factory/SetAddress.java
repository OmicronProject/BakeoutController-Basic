package unit.devices.factories.tdk_lambda_power_supply_factory;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by mkononen on 06/02/17.
 */
public final class SetAddress extends TDKLambdaPowerSupplyFactoryTestCase {
    private Integer newAddress = 11;

    @Test
    public void setAddress(){
        deviceFactory.setAddress(newAddress);
        assertEquals(newAddress, deviceFactory.getAddress());
    }
}
