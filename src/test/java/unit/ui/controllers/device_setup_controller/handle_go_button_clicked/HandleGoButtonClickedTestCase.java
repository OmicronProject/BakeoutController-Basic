package unit.ui.controllers.device_setup_controller.handle_go_button_clicked;

import javafx.scene.control.ComboBox;
import kernel.controllers.TDKLambdaPowerSupplyFactory;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import ui.controllers.DeviceSetupController;
import unit.ui.controllers.device_setup_controller.DeviceSetupControllerTestCase;

/**
 * Base class for tests of
 * {@link DeviceSetupController#handleGoButtonClicked()}
 */
public abstract class HandleGoButtonClickedTestCase extends
        DeviceSetupControllerTestCase {

    protected static final String expectedPortName = "/dev/ttyUSB0";
    protected ComboBox<String> portSelector;

    @Before
    public void setMockery(){
        applicationContext.getBean(Mockery.class).checking(
                getMockingExpectations()
        );
    }

    @Before
    public void selectExpectedPort(){
        portSelector = lookup(queryForPortSelector).query();
        portSelector.getSelectionModel().select(expectedPortName);
    }

    protected abstract ExpectationsForMockFactory getMockingExpectations();

    protected abstract class ExpectationsForMockFactory extends Expectations {
        protected final TDKLambdaPowerSupplyFactory factory;

        public ExpectationsForMockFactory(){
            factory = applicationContext.getBean(
                    TDKLambdaPowerSupplyFactory.class
            );
            expectationsForSetPortName();
        }

        private void expectationsForSetPortName(){
            oneOf(factory).setPortName(expectedPortName);
        }
    }
}
