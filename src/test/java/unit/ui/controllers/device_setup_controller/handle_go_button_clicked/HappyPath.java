package unit.ui.controllers.device_setup_controller.handle_go_button_clicked;

import org.jetbrains.annotations.Contract;
import org.junit.Test;
import ui.controllers.DeviceSetupController;

import static org.junit.Assert.assertEquals;

/**
 * Contains unit tests for {@link DeviceSetupController#handleGoButtonClicked()}
 */
public final class HappyPath extends
        HandleGoButtonClickedTestCase {

    @Test
    public void clickGoButton(){
        assertEquals(
                expectedPortName,
                portSelector.getSelectionModel().getSelectedItem()
        );

        clickOn(queryForGoButton);
    }

    @Contract(" -> !null")
    @Override
    protected ExpectationsForMockFactory getMockingExpectations(){
        return new HappyPathExpectations();
    }

    private class HappyPathExpectations extends ExpectationsForMockFactory {
        public HappyPathExpectations(){
            super();

            expectationsForGetPowerSupply();
        }

        private void expectationsForGetPowerSupply(){
            try {
                oneOf(factory).makePowerSupply();
                oneOf(pressureGaugeFactory).makePressureGauge();
            } catch (Exception error){
                error.printStackTrace();
            }
        }
    }
}
