package unit.kernel.models.pvci_pressure_gauge_factory;

import devices.PressureGauge;
import kernel.controllers.DeviceRegistry;
import kernel.modbus.ModbusPortConfiguration;
import kernel.models.PVCiPressureGaugeFactory;
import net.wimpi.modbus.io.ModbusTransaction;
import net.wimpi.modbus.msg.ModbusResponse;
import net.wimpi.modbus.msg.ReadInputRegistersRequest;
import net.wimpi.modbus.msg.ReadInputRegistersResponse;
import org.jmock.Expectations;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

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

    private ModbusTransaction mockTransaction = context.mock(
            ModbusTransaction.class
    );

    @Before
    public void setContext() throws Exception {
        context.checking(new ExpectationsForTest());
    }

    @Test
    public void getPressureGauge() throws IOException {
        assertNotNull(factory.getPressureGauge());
    }

    private class ExpectationsForTest extends Expectations {
        public ExpectationsForTest() throws Exception {
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

            oneOf(mockConnector).getTransactionForRequest(
                    with(any(ReadInputRegistersRequest.class))
            );
            will(returnValue(mockTransaction));

            oneOf(mockTransaction).getResponse();
            will(returnValue(new ReadInputRegistersResponse()));

            oneOf(mockTransaction).execute();

            oneOf(mockConnector).parseStringFromResponse(with(any
                    (ModbusResponse.class)));
            will(returnValue("iGC3"));

        }
    }
}
