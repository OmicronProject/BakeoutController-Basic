package unit.devices.tdk_lambda_power_supply;

import devices.PowerSupply;
import devices.TDKLambdaPowerSupply;
import org.junit.Test;

import java.io.IOException;

/**
 * Contains unit tests for {@link TDKLambdaPowerSupply#reset()}
 */
public final class Reset extends TDKLambdaPowerSupplyTestCase {
    @Test
    public void testReset() throws IOException {
        this.powerSupply.reset();
        assertCorrectMessage(PowerSupply.RESET_COMMAND);
    }
}
