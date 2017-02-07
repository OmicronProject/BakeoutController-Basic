package unit.devices.tdk_lambda_power_supply;

import devices.TDKLambdaPowerSupply;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Contains unit tests for {@link TDKLambdaPowerSupply#getDeviceAddress()}
 */
public final class GetDeviceAddress extends TDKLambdaPowerSupplyTestCase {
    @Test
    public void getDeviceAddress(){
        assertEquals(
            this.deviceAddress, this.powerSupply.getDeviceAddress()
        );
    }
}
