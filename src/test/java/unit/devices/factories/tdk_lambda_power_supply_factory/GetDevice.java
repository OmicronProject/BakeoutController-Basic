package unit.devices.factories.tdk_lambda_power_supply_factory;

import exceptions.DeviceNotCreatedException;
import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Created by mkononen on 06/02/17.
 */
public final class GetDevice extends TDKLambdaPowerSupplyFactoryTestCase {

    @Before
    public void setExpectations() {
        context.checking(new ExpectationsForSerialPort());
    }

    @Before
    public void setUpDeviceFactory(){
        deviceFactory.setSerialPort(mockSerialPort);
        deviceFactory.setAddress(address);
    }

    @Test
    public void getDevice() throws DeviceNotCreatedException {
        assertNotNull(deviceFactory.getDevice());
    }

    private class ExpectationsForSerialPort extends Expectations {
        public ExpectationsForSerialPort() {
            oneOf(mockSerialPort).getCommunicator();
            will(returnValue(communicatorForDevice));
        }
    }
}
