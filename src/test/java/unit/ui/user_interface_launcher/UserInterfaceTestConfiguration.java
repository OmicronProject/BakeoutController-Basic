package unit.ui.user_interface_launcher;

import org.jmock.Expectations;
import org.springframework.context.annotation.*;
import ui.FXMLStage;
import ui.UserInterfaceConfiguration;
import unit.ui.TestingConfiguration;

import java.io.IOException;

import static org.junit.Assert.fail;

/**
 * A configuration that overrides the
 * {@link UserInterfaceConfiguration#application()} bean in order to return
 * a mock application. This is done to check that the
 * {@link ui.UserInterfaceLauncher} configures the application correctly.
 */
@Configuration
@Import(UserInterfaceConfiguration.class)
@Lazy
public class UserInterfaceTestConfiguration extends TestingConfiguration {
    private volatile FXMLStage mockApplication;

    @Bean
    @Scope("singleton")
    public FXMLStage application(){
        mockApplication = mockingContext().mock(FXMLStage.class);
        mockingContext().checking(new ExpectationsForApplication());
        return mockApplication;
    }


    private class ExpectationsForApplication extends Expectations {
        public ExpectationsForApplication(){
            expectationsForApplication();
        }

        private void expectationsForApplication(){
            try {
                allowing(mockApplication).loadFXML();
            } catch (IOException error){
                fail("Failed mock initialization. This shouldn't happen");
            }
            allowing(mockApplication).show();
        }
    }
}
