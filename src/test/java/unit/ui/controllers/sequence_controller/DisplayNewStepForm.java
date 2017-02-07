package unit.ui.controllers.sequence_controller;

import javafx.scene.layout.Pane;
import org.junit.After;
import org.junit.Test;
import ui.controllers.SequenceController;

import static org.junit.Assert.assertNotNull;

/**
 * contains unit tests for {@link SequenceController#displayNewStepForm()}
 */
public final class DisplayNewStepForm extends SequenceControllerTestCase {
    private static final String queryForNewStepButton = "#new-step-button";
    private static final String queryForNewStepForm = "#new-step-form";
    private static final String queryForCloseButton =
            "#new-step-panel-close-button";

    @Test
    public void newStepForm(){
        clickOn(queryForNewStepButton);
        assertNotNull(lookupNewStepForm());
    }

    private Pane lookupNewStepForm(){
        return lookup(queryForNewStepForm).query();
    }

    @After
    public void closePanel(){
        clickOn(queryForCloseButton);
    }
}
