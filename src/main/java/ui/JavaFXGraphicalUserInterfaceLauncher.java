package ui;

import javafx.application.Application;
import javafx.stage.Stage;
import main.ApplicationConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Main entry point for the UI
 */
public class JavaFXGraphicalUserInterfaceLauncher extends Application
        implements UserInterfaceLauncher {
    private final ApplicationContext context;

    /**
     * Creates the launcher with the default Application context.
     */
    public JavaFXGraphicalUserInterfaceLauncher(){
        context = new AnnotationConfigApplicationContext(
            ApplicationConfiguration.class
        );
    }

    public JavaFXGraphicalUserInterfaceLauncher(ApplicationContext context){
        this.context = context;
    }

    /**
     * Starts the application
     *
     * @param stage The stage in which to start the application
     * @throws Exception If the application could not be started
     */
    @Override
    public void start(Stage stage) throws Exception {

        UserInterfaceConfiguration userInterface = context.getBean(
            UserInterfaceConfiguration.class
        );

        userInterface.setPrimaryStage(stage);
        userInterface.application().loadFXML();
        userInterface.application().show();
    }

    /**
     *
     * @param commandLineArguments The command line arguments with which the
     *                             application
     *                             ({@link main.Main#main(String[])}) was
     */
    @Override
    public void launchWithDefaultStage(String[] commandLineArguments){
        launch(this.getClass(), commandLineArguments);
    }
}
