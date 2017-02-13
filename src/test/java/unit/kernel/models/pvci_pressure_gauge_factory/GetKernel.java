package unit.kernel.models.pvci_pressure_gauge_factory;

import kernel.models.PVCiPressureGaugeFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Contains unit tests for {@link PVCiPressureGaugeFactory#getKernel()}
 */
public final class GetKernel extends PVCiPressureGaugeFactoryTestCase {
    @Test
    public void getKernel(){
        assertEquals(mockKernel, factory.getKernel());
    }
}
