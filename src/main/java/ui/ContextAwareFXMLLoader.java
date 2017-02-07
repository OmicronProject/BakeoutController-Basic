package ui;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Define a loader for FXML that is aware of the current Spring application
 * context. This loader uses {@link ApplicationContext#getBean(Class)} to
 * load its controllers, and the Beans that control the UI have been
 * initialized in the Spring IOC container
 */
public class ContextAwareFXMLLoader extends javafx.fxml.FXMLLoader implements
        ApplicationContextAware, FXMLLoader {

    /**
     * Sets the controller factory to use
     * {@link ApplicationContext#getBean(Class)}
     * @param context The new context that is set by Spring
     */
    @Override public void setApplicationContext(ApplicationContext context){
        this.setControllerFactory(context::getBean);
    }
}
