package unit.devices.tdk_lambda_power_supply;

import devices.PowerSupply;
import devices.TDKLambdaPowerSupply;
import org.junit.Test;

import java.io.IOException;

/**
 * Contains unit tests for {@link TDKLambdaPowerSupply#outputOff()}
 */
public final class OutputOff extends TDKLambdaPowerSupplyTestCase {
    @Test
    public void testOutputOff() throws IOException {
        this.powerSupply.outputOff();
        assertCorrectMessage(
            String.format(PowerSupply.SET_OUTPUT_COMMAND, PowerSupply.OFF)
        );
    }
}
