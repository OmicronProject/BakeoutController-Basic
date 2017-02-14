package unit.ui.controllers.device_setup_controller.handle_go_button_clicked;

import javafx.scene.text.Text;
import org.jetbrains.annotations.Contract;
import org.junit.Test;
import ui.controllers.DeviceSetupController;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.fail;

/**
 * Contains a test for {@link DeviceSetupController#handleGoButtonClicked()}
 * if a {@link IOException} is thrown
 */
public final class PowerSupplyIOExceptionTest extends HandleGoButtonClickedTestCase {
    private static final String queryForMessage = "#io-exception-message";

    @Contract(" -> !null")
    @Override
    protected ExpectationsForMockFactory getMockingExpectations(){
        return new IOExceptionExpectations();
    }

    @Test
    public void clickGoButton(){
        clickOn(queryForGoButton);

        Optional<Text> message = lookup(queryForMessage).tryQuery();

        if (!message.isPresent()){
            fail(
                    String.format(
                            "Did not find the message. I used the query %s",
                            queryForMessage
                    )
            );
        }
    }

    private class IOExceptionExpectations extends ExpectationsForMockFactory {
        public IOExceptionExpectations(){
            super();
            thrownExpectation();
        }

        private void thrownExpectation(){
            try {
                oneOf(factory).makePowerSupply();
                will(throwException(new IOException("Inordinate")));
                oneOf(pressureGaugeFactory).makePressureGauge();
            } catch (Exception error){
                error.printStackTrace();
            }
        }
    }
}
