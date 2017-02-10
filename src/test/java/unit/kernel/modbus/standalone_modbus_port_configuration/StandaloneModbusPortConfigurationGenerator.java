package unit.kernel.modbus.standalone_modbus_port_configuration;

import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;
import kernel.modbus.ModbusPortConfiguration;
import kernel.modbus.StandaloneModbusPortConfiguration;

import java.nio.charset.StandardCharsets;

/**
 * Generator for port configurations
 */
public class StandaloneModbusPortConfigurationGenerator extends
        Generator<ModbusPortConfiguration> {

    /**
     * The maximum length of the PortName string to be made
     */
    private static final int maximumPortNameStringLengthInBytes = 100;

    /**
     * Register this generator with Quickcheck
     */
    public StandaloneModbusPortConfigurationGenerator(){
        super(ModbusPortConfiguration.class);
    }

    /**
     * Generate a new configuration from randomly-generated data
     * @param rngesus A wrapper for a Random Number Generator provided by
     *                Quickcheck. Based on this, an object is created
     * @param status A reporter for the current status of the generation.
     *               This reports, for example, how many attempts were made
     *               to generate a test fixture
     * @return A randomly-generated port configuration
     */
    @Override public ModbusPortConfiguration generate(
            SourceOfRandomness rngesus,
            GenerationStatus status
    ){
        String portName = new String(rngesus.nextBytes(
                rngesus.nextInt(0, maximumPortNameStringLengthInBytes)
        ), StandardCharsets.UTF_8);

        ModbusPortConfiguration config = new
                StandaloneModbusPortConfiguration();

        config.setPortName(portName);
        config.setBaudRate(
                getNonNegativeInteger(rngesus)
        );
        config.setParityBits(
                getNonNegativeInteger(rngesus)
        );
        config.setEncoding(ModbusPortConfiguration.ASCII_ENCODING);
        config.setDataBits(getNonNegativeInteger(rngesus));
        config.setStopBits(getNonNegativeInteger(rngesus));

        return config;
    }

    /**
     * @param rngesus The random number generator to use
     * @return A randomly-generated non-negative integer
     */
    private static int getNonNegativeInteger(SourceOfRandomness rngesus){
            return rngesus.nextInt(0, Integer.MAX_VALUE);
    }
}
