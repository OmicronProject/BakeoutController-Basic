package unit.kernel.models.pvci_pressure_gauge_factory;

import kernel.Kernel;
import kernel.controllers.PVCiPressureGaugeFactory;
import kernel.modbus.ModbusConnector;
import kernel.views.DeviceRegistry;
import org.junit.Before;
import unit.kernel.models.ModelsTestCase;

/**
 * Base class for unit tests of {@link kernel.models.PVCiPressureGaugeFactory}
 */
public abstract class PVCiPressureGaugeFactoryTestCase extends ModelsTestCase {
    protected final Kernel mockKernel = context.mock(Kernel.class);

    protected PVCiPressureGaugeFactory factory;

    protected DeviceRegistry mockRegistry = context.mock(
            DeviceRegistry.class
    );

    protected ModbusConnector mockConnector = context.mock(
            ModbusConnector.class
    );

    @Before
    public void setFactory(){
        factory = new kernel.models.PVCiPressureGaugeFactory();
        factory.setKernel(mockKernel);
    }
}
