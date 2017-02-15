package unit.ui.controllers.device_setup_controller.handle_go_button_clicked;

import gnu.io.UnsupportedCommOperationException;
import javafx.scene.text.Text;
import org.jetbrains.annotations.Contract;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.fail;

/**
 * Contains a test to check for the
 * {@link gnu.io.UnsupportedCommOperationException} error handler
 */
public final class UnsupportedCommOperationExceptionTest extends
        HandleGoButtonClickedTestCase {
    private static final String queryForMessage =
            "#comm-operation-exception-message";

    @Test
    public void clickGoButton(){
        clickOn(queryForGoButton);
        Optional<Text> message = lookup(queryForMessage).tryQuery();

        if(!message.isPresent()){
            fail(
                    String.format(
                            "No message found. Query used was %s",
                            queryForMessage
                    )
            );
        }
    }

    @Contract(" -> !null")
    @Override
    protected ExpectationsForMockFactory getMockingExpectations(){
        return new UnsupportedCommExceptionExpectations();
    }

    private class UnsupportedCommExceptionExpectations extends
            ExpectationsForMockFactory {
        public UnsupportedCommExceptionExpectations(){
            super();
            exceptionThrower();
        }

        private void exceptionThrower(){
            try {
                oneOf(factory).makePowerSupply();
                will(throwException(new UnsupportedCommOperationException()));

                oneOf(pressureGaugeFactory).makePressureGauge();
            } catch (Exception error){
                error.printStackTrace();
            }
        }
    }

}
