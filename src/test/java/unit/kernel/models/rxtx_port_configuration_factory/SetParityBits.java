package unit.kernel.models.rxtx_port_configuration_factory;

import kernel.models.RXTXPortConfigurationFactory;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Contains unit tests for
 * {@link RXTXPortConfigurationFactory#setParityBits(Integer)}
 */
public final class SetParityBits extends RXTXPortConfigurationFactoryTestCase {
    @Test
    public void setParityBits(){
        this.portConfigurationFactory.setParityBits(parityBits);
        finishConstruction();

        assertEquals(
                (int) parityBits,
                this.portConfigurationFactory.getConfiguration()
                        .getParityBits()
        );
    }

    private void finishConstruction(){
        this.portConfigurationFactory.setBaudRate(baudRate);
        this.portConfigurationFactory.setStopBits(stopBits);
        this.portConfigurationFactory.setDataBits(dataBits);
    }
}
