package unit.ui.controllers.sequence_controller;

import exceptions.UnableToRunControlAlgorithmException;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import kernel.Kernel;
import kernel.controllers.VoltageSetPointAlgorithm;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests that the code is run correctly if the go button is clicked
 */
public final class HandleGoButtonClicked extends SequenceControllerTestCase {
    private static final Double desiredPressure = 1e-8;
    private static final Double desiredVoltage = 5.0;

    @Before
    public void typeInParameters(){
        clickOn(queryForPressureTextField);
        write(desiredPressure.toString());
        press(KeyCode.ENTER);

        clickOn(queryForVoltageTextField);
        write(desiredVoltage.toString());
        press(KeyCode.ENTER);
    }

    @Test
    public void handleGoButtonClicked() throws Exception {
        applicationContext.getBean(Mockery.class).checking(
                new ExpectationsForTest()
        );

        Button goButton = lookup(queryForGoButton).query();
        clickOn(goButton);
        assertTrue(goButton.isDisabled());
    }

    @Test
    public void handleGoButtonClickedException() throws Exception {
        applicationContext.getBean(Mockery.class).checking(
                new ExpectationForException()
        );
        Button goButton = lookup(queryForGoButton).query();
        clickOn(goButton);
        assertFalse(goButton.isDisabled());
    }

    public class ExpectationsForTest extends Expectations {
        protected final VoltageSetPointAlgorithm mockAlgorithm =
                applicationContext.getBean(VoltageSetPointAlgorithm.class);

        protected final Kernel mockKernel =
                applicationContext.getBean(Kernel.class);

        public ExpectationsForTest() throws Exception {
            oneOf(mockAlgorithm).setKernel(mockKernel);
            oneOf(mockAlgorithm).setDesiredVoltage(
                    with(is(closeTo(desiredVoltage, 1e-2)))
            );
            oneOf(mockAlgorithm).setMaximumIterations(
                    with(any(Integer.class))
            );
            oneOf(mockAlgorithm).setPressureUpperBound(
                    with(any(Float.class))
            );
            algorithmRunExpectation();
        }

        protected void algorithmRunExpectation() throws Exception {
            oneOf(mockAlgorithm).run();
        }
    }

    public class ExpectationForException extends ExpectationsForTest {
        public ExpectationForException() throws Exception {
            super();
        }

        @Override
        protected void algorithmRunExpectation() throws Exception {
            oneOf(mockAlgorithm).run();
            will(throwException(new UnableToRunControlAlgorithmException(
                    "Test exception"
            )));
        }
    }
}
