package unit.ui.controllers.modals.new_device_controller;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import org.junit.After;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.fail;

/**
 *
 */
public final class HandleCloseButtonClicked extends
        NewDeviceControllerTestCase {

    private static final String queryForNewDeviceForm = "#newDeviceForm";

    @Test
    public void testCloseButton(){
        clickOn(queryForCloseButton);

        Optional<GridPane> newDeviceForm = lookup(
                queryForNewDeviceForm
        ).tryQuery();

        newDeviceForm.ifPresent(
                form -> fail(
                        "Clicking the close button did not close the form"
                )
        );

    }

    @Override
    @After
    public void closeNewDevicePanel(){
        Optional<Button> closeButton = lookup(queryForCloseButton).tryQuery();

        if(closeButton.isPresent()) {
            clickOn(closeButton.get());
            fail("The panel was not closed by pressing the close button");
        }
    }
}
