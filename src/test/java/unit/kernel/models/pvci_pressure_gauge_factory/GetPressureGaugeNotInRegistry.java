package unit.kernel.models.pvci_pressure_gauge_factory;

import devices.PressureGauge;
import kernel.controllers.DeviceRegistry;
import kernel.modbus.ModbusPortConfiguration;
import kernel.models.PVCiPressureGaugeFactory;
import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Contains a unit test for {@link PVCiPressureGaugeFactory#getPressureGauge()}
 * where there is no previous instance of a pressure gauge in the device
 * registry.
 */
public final class GetPressureGaugeNotInRegistry extends
        PVCiPressureGaugeFactoryTestCase {

    private DeviceRegistry mockController = context.mock(DeviceRegistry.class,
            "mockController");

    private PressureGauge mockPressureGauge = context.mock(
            PressureGauge.class);

    @Before
    public void setContext(){
        context.checking(new ExpectationsForTest());
    }

    @Test
    public void getPressureGauge(){
        assertNotNull(factory.getPressureGauge());
    }

    private class ExpectationsForTest extends Expectations {
        public ExpectationsForTest(){
            oneOf(mockKernel).getDeviceRegistryView();
            will(returnValue(mockRegistry));

            oneOf(mockKernel).getDeviceRegistryController();
            will(returnValue(mockController));

            oneOf(mockKernel).getModbusConnector();
            will(returnValue(mockConnector));

            oneOf(mockRegistry).hasPressureGauge();
            will(returnValue(Boolean.FALSE));

            oneOf(mockController).setPressureGauge(
                    with(any(PressureGauge.class))
            );

            oneOf(mockConnector).setPortConfiguration(
                    with(any(ModbusPortConfiguration.class))
            );

            oneOf(mockRegistry).getPressureGauge();
            will(returnValue(mockPressureGauge));
        }
    }
}
