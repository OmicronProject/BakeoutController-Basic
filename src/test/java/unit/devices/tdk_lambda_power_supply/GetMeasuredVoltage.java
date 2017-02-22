package unit.devices.tdk_lambda_power_supply;

import devices.TDKLambdaPowerSupply;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Contains unit tests for {@link TDKLambdaPowerSupply#getMeasuredVoltage()}
 */
public final class GetMeasuredVoltage extends TDKLambdaPowerSupplyTestCase {
    private final Double measuredVoltage = 3.0;

    @Before
    public void setUpCommunicator(){
        this.communicatorForDevice.setInputStreamData(measuredVoltage.toString());
    }

    @Test
    public void getMeasuredVoltage() throws IOException {
        Double voltage = this.powerSupply.getMeasuredVoltage();
        assertEquals(measuredVoltage, voltage);
    }
}
