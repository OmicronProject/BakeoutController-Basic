package unit.ui.controllers.device_setup_controller.handle_go_button_clicked;

import javafx.scene.text.Text;
import org.jetbrains.annotations.Contract;
import org.junit.Test;

import java.io.IOException;
import java.util.Optional;

import static org.junit.Assert.fail;

/**
 * Tests that a message is thrown if the pressure gauge cannot be created
 */
public final class PressureGaugeIOExceptionTest extends
        HandleGoButtonClickedTestCase {

    private static final String queryForMessage = "#pvci-io-exception-message";

    @Contract(" -> !null")
    @Override
    public ExpectationsForMockFactory getMockingExpectations(){
        return new IOExceptionExpectations();
    }

    @Test
    public void testClick(){
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
                oneOf(pressureGaugeFactory).makePressureGauge();
                will(throwException(new IOException("Kaboom")));
            } catch (Exception error){
                error.printStackTrace();
            }
        }
    }
}
