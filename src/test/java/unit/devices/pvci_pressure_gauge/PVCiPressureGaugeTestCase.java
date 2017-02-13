package unit.devices.pvci_pressure_gauge;

import com.pholser.junit.quickcheck.generator.GenerationStatus;
import com.pholser.junit.quickcheck.generator.Generator;
import com.pholser.junit.quickcheck.random.SourceOfRandomness;
import devices.PVCiPressureGauge;
import devices.PressureGauge;
import kernel.modbus.ModbusConnector;
import org.junit.Before;
import unit.devices.DevicesTestCase;

/**
 * Base class for unit tests of {@link devices.PVCiPressureGauge}
 */
public abstract class PVCiPressureGaugeTestCase extends DevicesTestCase {
    protected final ModbusConnector mockModbusConnector = context.mock(
            ModbusConnector.class
    );

    protected static final Integer address = 11;

    protected PressureGauge pressureGauge;

    @Before
    public void createPressureGauge(){
        pressureGauge = new PVCiPressureGauge(address, mockModbusConnector);
    }

    public class PVCiPressureGaugeGenerator extends
            Generator<PVCiPressureGauge> {

        public PVCiPressureGaugeGenerator(){
            super(PVCiPressureGauge.class);
        }

        @Override
        public PVCiPressureGauge generate(SourceOfRandomness randomness,
                                          GenerationStatus status){
            int address = randomness.nextInt();

            return new PVCiPressureGauge(address, mockModbusConnector);
        }
    }
}
