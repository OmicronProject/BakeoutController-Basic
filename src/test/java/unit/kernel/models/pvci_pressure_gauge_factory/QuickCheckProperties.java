package unit.kernel.models.pvci_pressure_gauge_factory;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Contains Quickcheck tests for {@link kernel.models.PVCiPressureGaugeFactory}
 */
@RunWith(JUnitQuickcheck.class)
public final class QuickCheckProperties extends
        PVCiPressureGaugeFactoryTestCase {
    @Property
    public void address(Integer address){
        factory.setAddress(address);
        assertEquals(
                address,
                factory.getAddress()
        );
    }

    @Property
    public void portName(String portName){
        factory.setPortName(portName);
        assertEquals(
                portName,
                factory.getPortName()
        );
    }
}
