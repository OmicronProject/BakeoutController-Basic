package unit.kernel.modbus.standalone_modbus_port_configuration;

import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import kernel.modbus.ModbusPortConfiguration;
import kernel.modbus.StandaloneModbusPortConfiguration;
import net.wimpi.modbus.util.SerialParameters;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import unit.kernel.modbus.ModbusTestCase;
import unit.kernel.modbus.StandaloneModbusPortConfigurationGenerator;

import static org.junit.Assert.assertEquals;

/**
 * Base class for unit tests of
 * {@link kernel.modbus.StandaloneModbusPortConfiguration}
 */
@RunWith(JUnitQuickcheck.class)
public final class StandaloneModbusPortConfigurationTestCase extends
        ModbusTestCase {

    /**
     * A configuration to play with, and use in each test
     */
    private final ModbusPortConfiguration config =
            new StandaloneModbusPortConfiguration();

    @Rule
    public final ExpectedException expectedException =
            ExpectedException.none();

    @Property
    public void portName(String portName){
        config.setPortName(portName);

        assertEquals(portName, config.getPortName());
    }

    @Property
    public void stopBits(int stopBits){
        config.setStopBits(stopBits);

        assertEquals(stopBits, config.getStopBits());
    }

    @Property
    public void parityBits(int parityBits){
        config.setParityBits(parityBits);
        assertEquals(parityBits, config.getParityBits());
    }

    @Property
    public void dataBits(int dataBits){
        config.setDataBits(dataBits);
        assertEquals(dataBits, config.getDataBits());
    }

    @Property
    public void baudRateValidArgument(int baudRate) {
        if (baudRate > 0) {
            config.setBaudRate(baudRate);
            assertEquals(baudRate, config.getBaudRate());
        }
    }

    @Property
    public void baudRateInvalidArgument(int baudRate){
        if (baudRate < 0){
            expectedException.expect(IllegalArgumentException.class);
            config.setBaudRate(baudRate);
        }
    }

    @Property
    public void encoding(String encoding){
        config.setEncoding(encoding);
        assertEquals(encoding, config.getEncoding());
    }

    @Property
    public void serialParameters(
            @From(StandaloneModbusPortConfigurationGenerator.class)
                    ModbusPortConfiguration config
    ){
        SerialParameters parameters = config.getSerialParameters();

        assertEquals(
                config.getBaudRate(),
                parameters.getBaudRate()
        );
        assertEquals(
                config.getDataBits(),
                parameters.getDatabits()
        );
        assertEquals(
                config.getStopBits(),
                parameters.getStopbits()
        );
        assertEquals(
                config.getPortName(),
                parameters.getPortName()
        );
        assertEquals(
                config.getParityBits(),
                parameters.getParity()
        );
        assertEquals(
                config.getStopBits(),
                parameters.getStopbits()
        );
    }
}
