package unit.ui.user_interface_launcher;

import org.junit.Test;
import ui.UserInterfaceLauncher;

import static junit.framework.TestCase.assertNotNull;

/**
 * Tests the null-argument constructor for {@link UserInterfaceLauncher}
 */
public final class NoArgumentConstructor extends
        UserInterfaceLauncherTestCase {
    @Test
    public void testNoArgumentConstructor(){
        UserInterfaceLauncher launcher = new UserInterfaceLauncher();
        assertNotNull(launcher);
    }
}
