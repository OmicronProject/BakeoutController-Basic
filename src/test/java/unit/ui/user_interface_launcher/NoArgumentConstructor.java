package unit.ui.user_interface_launcher;

import org.junit.Test;
import ui.JavaFXGraphicalUserInterfaceLauncher;

import static junit.framework.TestCase.assertNotNull;

/**
 * Tests the null-argument constructor for {@link JavaFXGraphicalUserInterfaceLauncher}
 */
public final class NoArgumentConstructor extends
        UserInterfaceLauncherTestCase {
    @Test
    public void testNoArgumentConstructor(){
        JavaFXGraphicalUserInterfaceLauncher launcher = new JavaFXGraphicalUserInterfaceLauncher();
        assertNotNull(launcher);
    }
}
