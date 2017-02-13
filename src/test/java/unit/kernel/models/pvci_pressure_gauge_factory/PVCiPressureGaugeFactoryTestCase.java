package unit.kernel.models.pvci_pressure_gauge_factory;

import kernel.Kernel;
import kernel.controllers.PVCiPressureGaugeFactory;
import org.junit.Before;
import unit.kernel.models.ModelsTestCase;

/**
 * Base class for unit tests of {@link kernel.models.PVCiPressureGaugeFactory}
 */
public abstract class PVCiPressureGaugeFactoryTestCase extends ModelsTestCase {
    protected final Kernel mockKernel = context.mock(Kernel.class);

    protected PVCiPressureGaugeFactory factory;

    @Before
    public void setFactory(){
        factory = new kernel.models.PVCiPressureGaugeFactory();
        factory.setKernel(mockKernel);
    }
}
