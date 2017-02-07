package unit.ui.controllers.device_list_controller;

import devices.PowerSupply;
import javafx.scene.text.Text;
import kernel.views.DeviceRegistry;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.fail;

/**
 * Created by mkononen on 07/02/17.
 */
public final class HandleRefreshButtonClickedNoPowerSupply extends
        DeviceListControllerTestCase {

    @Before
    public void setUpForPowerSupply() {
        applicationContext.getBean(Mockery.class).checking(
                new ExpectationsForPowerSupply()
        );
    }

    @Test
    public void testPowerSupply() {
        clickOn(queryForRefreshButton);

        Optional<Text> message = lookup(queryForPowerSupply).tryQuery();

        if (!message.isPresent()) {
            fail("The test did not make the power supply message appear");
        }
    }

    private class ExpectationsForPowerSupply extends Expectations {
        public ExpectationsForPowerSupply() {
            oneOf(applicationContext.getBean(DeviceRegistry.class))
                    .hasPowerSupply();
            will(returnValue(Boolean.TRUE));

            oneOf(applicationContext.getBean(DeviceRegistry.class))
                    .getPowerSupply();
            will(returnValue(applicationContext.getBean(PowerSupply.class)));
        }
    }
}
