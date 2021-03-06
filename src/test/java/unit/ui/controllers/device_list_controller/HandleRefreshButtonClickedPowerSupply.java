package unit.ui.controllers.device_list_controller;

import javafx.scene.text.Text;
import kernel.views.DeviceContainer;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.fail;

/**
 * Contains unit tests for handling the refresh button being clicked when
 * there is a power supply present in the device registry
 */
public final class HandleRefreshButtonClickedPowerSupply extends
        DeviceListControllerTestCase {

    @Before
    public void setUpForNoPowerSupply() {
        applicationContext.getBean(Mockery.class).checking(
                new ExpectationsForNoPowerSupply()
        );
    }

    @Test
    public void testNoPowerSupply() {

        clickOn(queryForRefreshButton);

        Optional<Text> message = lookup(queryForNoPowerSupply).tryQuery();

        if (!message.isPresent()) {
            fail("The test did not make the no-power-supply message appear");
        }
    }


    private class ExpectationsForNoPowerSupply extends Expectations {
        public ExpectationsForNoPowerSupply() {
            oneOf(applicationContext.getBean(DeviceContainer.class))
                    .hasPowerSupply();
            will(returnValue(Boolean.FALSE));

            oneOf(applicationContext.getBean(DeviceContainer.class))
                    .hasPressureGauge();
            will(returnValue(Boolean.FALSE));
        }
    }
}
