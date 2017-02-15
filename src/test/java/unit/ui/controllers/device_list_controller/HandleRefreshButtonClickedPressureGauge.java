package unit.ui.controllers.device_list_controller;

import devices.PowerSupply;
import devices.PressureGauge;
import javafx.scene.text.Text;
import kernel.views.DeviceRegistry;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.fail;

/**
 * Tests that {@link ui.controllers.DeviceListController} correctly reports
 * a pressure gauge to be present if one exists
 */
public class HandleRefreshButtonClickedPressureGauge extends
        DeviceListControllerTestCase {

    @Before
    public void setContext(){
        applicationContext.getBean(Mockery.class).checking(
                new ExpectationsForTest()
        );
    }

    @Test
    public void testPressureGauge(){
        clickOn(queryForRefreshButton);

        Optional<Text> message = lookup(queryForPressureGaugeMessage)
                .tryQuery();

        if (!message.isPresent()){
            String failMessage = String.format(
                    "The test did not make the message with id %s appear",
                    queryForPressureGaugeMessage
            );
            fail(failMessage);
        }
    }

    private class ExpectationsForTest extends Expectations {

        private final DeviceRegistry registry = applicationContext.getBean(
                DeviceRegistry.class
        );

        private final PressureGauge gauge = applicationContext.getBean
                (Mockery.class).mock(PressureGauge.class);

        public ExpectationsForTest(){
            oneOf(registry).hasPowerSupply();
            will(returnValue(Boolean.FALSE));

            oneOf(registry).hasPressureGauge();
            will(returnValue(Boolean.TRUE));

            oneOf(registry).getPressureGauge();
            will(returnValue(gauge));
        }
    }
}
