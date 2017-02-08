package unit.ui.controllers.device_setup_controller;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import kernel.controllers.TDKLambdaPowerSupplyFactory;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by mkononen on 07/02/17.
 */
public final class HandleGoButtonClicked extends
        DeviceSetupControllerTestCase {

    private Button goButton;
    private final String expectedPortName = "/dev/ttyUSB0";
    private ComboBox<String> portSelector;

    @Before
    public void setMockery(){
        applicationContext.getBean(Mockery.class).checking(
                new ExpectationsForMockFactory()
        );
    }

    @Before
    public void selectExpectedPort(){
        portSelector = lookup(queryForPortSelector).query();
        portSelector.getSelectionModel().select(expectedPortName);
    }

    @Before
    public void getGoButton(){
        goButton = lookup(queryForGoButton).query();
    }

    @Test
    public void clickGoButton(){
        assertEquals(
                expectedPortName,
                portSelector.getSelectionModel().getSelectedItem()
        );

        clickOn(queryForGoButton);
    }

    private class ExpectationsForMockFactory extends Expectations {
        public ExpectationsForMockFactory(){
            oneOf(applicationContext.getBean(
                    TDKLambdaPowerSupplyFactory.class
            )).setPortName(expectedPortName);
        }
    }
}
