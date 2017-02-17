package unit.kernel.models.pvci_pressure_gauge_factory;

import com.ghgande.j2mod.modbus.io.ModbusSerialTransaction;
import com.ghgande.j2mod.modbus.io.ModbusTransaction;
import com.ghgande.j2mod.modbus.msg.*;
import devices.PressureGauge;
import kernel.controllers.DeviceRegistry;
import kernel.controllers.variables.VariableProviderRegistry;
import kernel.modbus.ModbusPortConfiguration;
import kernel.models.PVCiPressureGaugeFactory;
import kernel.views.variables.VariableProvider;
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

    private ModbusTransaction mockTransaction = new MockTransaction();

    private VariableProviderRegistry mockVariableRegistry = context.mock(
            VariableProviderRegistry.class
    );

    @Before
    public void setContext() throws Exception {
        context.checking(new ExpectationsForTest());
    }

    @Test
    public void getPressureGauge() throws IOException {
        assertNotNull(factory.getPressureGauge());
    }

    private class MockTransaction extends ModbusSerialTransaction {
        @Override
        public void execute(){
            // Don't actually run anything
        }

        @Override
        public ModbusResponse getResponse(){
            return new ReadWriteMultipleResponse();
        }
    }

    private class ExpectationsForTest extends Expectations {
        public ExpectationsForTest() throws Exception {
            expectationsForKernel();
            expectationsForDeviceRegistry();
            expectationsForModbusConnector();
            expectationsForMockController();
            expectationsForVariableRegistry();
        }

        private void expectationsForKernel(){
            oneOf(mockKernel).getDeviceRegistryView();
            will(returnValue(mockRegistry));

            oneOf(mockKernel).getDeviceRegistryController();
            will(returnValue(mockController));

            oneOf(mockKernel).getModbusConnector();
            will(returnValue(mockConnector));

            oneOf(mockKernel).getVariableProvidersController();
            will(returnValue(mockVariableRegistry));
        }

        private void expectationsForDeviceRegistry(){
            oneOf(mockRegistry).hasPressureGauge();
            will(returnValue(Boolean.FALSE));

            oneOf(mockRegistry).getPressureGauge();
            will(returnValue(mockPressureGauge));
        }

        private void expectationsForModbusConnector() throws Exception {
            oneOf(mockConnector).setPortConfiguration(
                    with(any(ModbusPortConfiguration.class))
            );

            oneOf(mockConnector).getTransactionForRequest(
                    with(any(ReadWriteMultipleRequest.class))
            );
            will(returnValue(mockTransaction));

            oneOf(mockConnector).parseStringFromResponse(with(any
                    (ModbusResponse.class)));
            will(returnValue("iGC3"));

        }

        private void expectationsForMockController() throws Exception {
            oneOf(mockController).setPressureGauge(
                    with(any(PressureGauge.class))
            );
        }

        private void expectationsForVariableRegistry(){
            oneOf(mockVariableRegistry).setPressureProvider(
                    with(any(VariableProvider.class))
            );
        }
    }
}
