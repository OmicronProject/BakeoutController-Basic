package unit.ui.user_interface_configuration;

import org.junit.Test;
import org.springframework.beans.factory.BeanCreationException;
import ui.FXMLStage;

import static org.junit.Assert.assertNotNull;

/**
 * Contains unit tests for {@link UserInterfaceConfiguration#application()}
 */
public final class Application extends UserInterfaceConfigurationTestCase {
    /**
     * Tests that attempting to create the Application outside the UI worker
     * thread will result in a {@link BeanCreationException}
     */
    @Test(expected = BeanCreationException.class)
    public void application(){
        assertNotNull(this.applicationContext.getBean(FXMLStage.class));
    }
}
