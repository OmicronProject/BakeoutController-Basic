package unit.kernel.modbus.modbus_connection_manager;

import com.pholser.junit.quickcheck.From;
import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import kernel.modbus.ModBusConnectionManager;
import kernel.modbus.ModbusConnector;
import kernel.modbus.ModbusPortConfiguration;
import org.junit.runner.RunWith;
import unit.kernel.modbus.StandaloneModbusPortConfigurationGenerator;

import static org.junit.Assert.assertEquals;

/**
 * Contains the tests runnable with a given set of properties
 */
@RunWith(JUnitQuickcheck.class)
public final class QuickCheckProperties extends
        ModbusConnectionManagerTestCase {

    @Property
    public void portConfiguration(
            @From(StandaloneModbusPortConfigurationGenerator.class)
            ModbusPortConfiguration config){
        ModbusConnector manager = new ModBusConnectionManager();
        manager.setPortConfiguration(config);

        assertEquals(
                config,
                manager.getPortConfiguration()
        );
    }
}
