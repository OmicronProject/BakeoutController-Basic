package unit.ui.controllers.device_setup_controller.handle_go_button_clicked;

import gnu.io.PortInUseException;
import javafx.scene.text.Text;
import org.jetbrains.annotations.Contract;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.fail;

/**
 * Checks that the {@link PortInUseException} handler works correctly.
 */
public final class PortInUseExceptionTest extends
        HandleGoButtonClickedTestCase {
    private static final String queryForMessage =
            "#port-in-use-exception-message";

    @Contract(" -> !null")
    @Override
    protected ExpectationsForMockFactory getMockingExpectations(){
        return new ExceptionExpectations();
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

    private class ExceptionExpectations extends ExpectationsForMockFactory {
        public ExceptionExpectations(){
            super();
            thrownExpectation();
        }

        private void thrownExpectation(){
            try {
                oneOf(factory).makePowerSupply();
                will(throwException(new PortInUseException()));
                oneOf(pressureGaugeFactory).makePressureGauge();
            } catch (Exception error){
                error.printStackTrace();
            }
        }
    }
}
