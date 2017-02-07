package unit.devices.factories.tdk_lambda_power_supply_factory;

import devices.PowerSupply;
import devices.factories.DeviceFactory;
import devices.factories.TDKLambdaPowerSupplyFactory;
import kernel.serial_ports.SerialPort;
import org.junit.Before;
import unit.devices.factories.FactoriesTestCase;

/**
 * Base class for test cases of
 * {@link devices.factories.TDKLambdaPowerSupplyFactory}
 */
public abstract class TDKLambdaPowerSupplyFactoryTestCase extends
        FactoriesTestCase {
    protected SerialPort mockSerialPort;
    protected Integer address = 6;
    protected DeviceFactory<PowerSupply> deviceFactory;

    @Before
    public void setDeviceFactory(){
        deviceFactory = new TDKLambdaPowerSupplyFactory();
    }

    @Before
    public void setMockSerialPort(){
        mockSerialPort = context.mock(SerialPort.class);
    }

    @Before
    public void setMockCommunicator(){
        communicatorForDevice.setInputStreamData("OK");
    }
}
