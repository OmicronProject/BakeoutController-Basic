package unit.ui.controllers.results_controller;

import kernel.views.VariableProviderRegistry;
import kernel.views.variables.VoltageProvider;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import unit.ui.MockVoltageProvider;
import unit.ui.controllers.ControllersTestCase;

/**
 * Base class for testing {@link ui.controllers.ResultController}
 */
public abstract class ResultControllerTestCase extends ControllersTestCase {

    protected static final String queryForResultsTab = "#results-tab";
    protected static final String queryForPressureTab = "#pressure-tab";
    protected static final String queryForVoltageTab = "#voltage-tab";
    protected static final String queryForPressureChart = "#pressure-chart";

    protected final MockVoltageProvider mockVoltageProvider =
            (MockVoltageProvider) applicationContext.getBean(
                    VoltageProvider.class
            );

    @Before
    public void navigateToResultsTab(){
        clickOn(queryForResultsTab);
    }

    @Before
    public void setMockVoltageProvider(){
        applicationContext.getBean(Mockery.class).checking(
                new ExpectationsForTest()
        );
    }



    private class ExpectationsForTest extends Expectations {
        public ExpectationsForTest(){
            allowing(applicationContext.getBean(VariableProviderRegistry.class)
            ).getVoltageProvider();
            will(returnValue(mockVoltageProvider));
        }
    }
}
