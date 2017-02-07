package unit.ui.user_interface_configuration;

/**
 * Contains unit tests for {@link UserInterfaceConfiguration#fxmlLoader()}
 */
public final class FXMLLoader extends UserInterfaceConfigurationTestCase {
    public void fxmlLoaderError(){
        this.applicationContext.getBean(ui.FXMLLoader.class);
    }
}
