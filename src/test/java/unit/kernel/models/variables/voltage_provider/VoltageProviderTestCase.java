package unit.kernel.models.variables.voltage_provider;

import devices.PowerSupply;
import kernel.Kernel;
import kernel.controllers.TaskRunner;
import kernel.models.variables.VoltageProvider;
import kernel.views.variables.VariableProvider;
import kernel.views.variables.Voltage;
import org.jetbrains.annotations.Contract;
import org.jmock.Expectations;
import org.junit.Before;
import unit.kernel.models.variables.VariablesTestCase;

/**
 * Base class for unit tests of {@link kernel.models.variables.VoltageProvider}
 */
public abstract class VoltageProviderTestCase extends VariablesTestCase {
    protected VariableProvider<Voltage> provider;

    protected PowerSupply mockPowerSupply = context.mock(PowerSupply.class);

    protected Kernel mockKernel = context.mock(Kernel.class);

    protected TaskRunner mockTaskRunner = context.mock(TaskRunner.class);

    @Before
    public void setUp(){
        context.checking(getExpectations());
        setProvider();
    }

    @Contract(" -> !null")
    protected abstract ExpectationsForTest getExpectations();


    private void setProvider(){
        provider = new VoltageProvider(mockPowerSupply, mockKernel);
    }

    protected class ExpectationsForTest extends Expectations {
        public ExpectationsForTest(){
            expectationsForKernel();
            expectationsForTaskRunner();
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
