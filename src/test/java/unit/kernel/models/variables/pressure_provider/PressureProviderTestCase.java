package unit.kernel.models.variables.pressure_provider;

import devices.PressureGauge;
import kernel.Kernel;
import kernel.controllers.TaskRunner;
import kernel.models.variables.PressureProvider;
import kernel.views.variables.Pressure;
import kernel.views.variables.VariableProvider;
import org.jmock.Expectations;
import org.junit.Before;
import unit.kernel.models.variables.VariablesTestCase;

/**
 * Base class for unit tests of
 * {@link kernel.models.variables.PressureProvider}
 */
public abstract class PressureProviderTestCase extends VariablesTestCase {
    protected final PressureGauge pressureGauge = context.mock(
            PressureGauge.class
    );

    protected final Kernel mockKernel = context.mock(
            Kernel.class
    );

    protected final TaskRunner mockTaskRunner = context.mock(
            TaskRunner.class
    );

    protected VariableProvider<Pressure> provider;

    protected abstract ExpectationsForPressureProvider getExpectations()
            throws Exception;

    @Before
    public void setUp() throws Exception {
        setContext();
        setProvider();
    }

    private void setProvider(){
        provider = new PressureProvider(pressureGauge, mockKernel);
    }

    private void setContext() throws Exception {
        context.checking(getExpectations());
    }

    protected class ExpectationsForPressureProvider extends Expectations {
        public ExpectationsForPressureProvider() throws Exception {
            expectationsForPressureGauge();
            expectationsForKernel();
            expectationsForTaskRunner();
        }

        private void expectationsForPressureGauge() throws Exception {
            allowing(pressureGauge).getPressure();
        }

        private void expectationsForKernel(){
            oneOf(mockKernel).getTaskRunner();
            will(returnValue(mockTaskRunner));
        }

        private void expectationsForTaskRunner(){
            oneOf(mockTaskRunner).execute(with(any(Runnable.class)));
        }
    }
}
