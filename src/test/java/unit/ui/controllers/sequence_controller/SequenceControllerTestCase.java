package unit.ui.controllers.sequence_controller;

import org.junit.Before;
import unit.ui.controllers.ControllersTestCase;

/**
 * Base class for testing {@link ui.controllers.SequenceController}
 */
public abstract class SequenceControllerTestCase extends ControllersTestCase {
    private static final String queryForSequenceTab = "#sequence-tab";

    protected static final String queryForVoltageTextField =
            "#voltage-text-field";

    protected static final String queryForVoltageSlider =
            "#voltage-slider";

    protected static final String queryForPressureTextField =
            "#pressure-text-field";

    protected static final String queryForPressureSlider =
            "#pressure-slider";

    protected static final String queryForGoButton = "#go-button";

    @Before
    public void navigateToSequenceTab(){
        clickOn(queryForSequenceTab);
    }

}
