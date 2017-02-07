package unit.devices.tdk_lambda_power_supply;

import devices.PowerSupply;
import exceptions.ResponseNotOKException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.fail;

/**
 * Contains unit tests for
 * {@link devices.TDKLambdaPowerSupply#setVoltage(double)}
 */
public final class SetVoltage extends TDKLambdaPowerSupplyTestCase {
    private final Double newVoltage = 5.0;

    @Before
    public void setUpCommunicator(){
        this.communicatorForDevice.setInputStreamData("OK");
    }

    @Test
    public void testSetVoltage(){
        try {
            happyPath();
            assertCorrectMessage(
                String.format(PowerSupply.SET_VOLTAGE_COMMAND, newVoltage)
            );
        }
        catch (IOException error) {
            fail(error.getMessage());
        }
    }

    private void happyPath() throws IOException {
        this.powerSupply.setVoltage(newVoltage);
    }

    @Test(expected = ResponseNotOKException.class)
    public void testSetVoltageError() throws IOException {
        this.communicatorForDevice.setInputStreamData(errorMessage);
        this.powerSupply.setVoltage(newVoltage);
    }

}
