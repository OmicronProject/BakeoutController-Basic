package unit.devices.tdk_lambda_power_supply;

import devices.PowerSupply;
import devices.TDKLambdaPowerSupply;
import org.junit.Test;

import java.io.IOException;

/**
 * Contains unit tests for {@link TDKLambdaPowerSupply#outputOn()}
 */
public final class OutputOn extends TDKLambdaPowerSupplyTestCase {
    @Test
    public void testOutputOn() throws IOException {
        this.powerSupply.outputOn();
        assertCorrectMessage(
            String.format(PowerSupply.SET_OUTPUT_COMMAND, PowerSupply.ON)
        );
    }
}
