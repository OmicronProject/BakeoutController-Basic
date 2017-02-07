package unit.ui.controllers.sequence_controller;

import org.junit.Before;
import unit.ui.controllers.ControllersTestCase;

/**
 * Base class for testing {@link ui.controllers.SequenceController}
 */
public abstract class SequenceControllerTestCase extends ControllersTestCase {
    private static final String queryForSequenceTab = "#sequence-tab";

    @Before
    public void navigateToSequenceTab(){
        clickOn(queryForSequenceTab);
    }

}
