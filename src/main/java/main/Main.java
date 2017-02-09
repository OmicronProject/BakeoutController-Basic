package main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ui.JavaFXGraphicalUserInterfaceLauncher;
import ui.UserInterfaceLauncher;

/**
 * The main runner for the application
 */
public final class Main {
    /**
     * The application log
     */
    private static Logger log = LoggerFactory.getLogger(Main.class);

    /**
     * The global Spring context defining all accessible beans
     */
    private static final ApplicationContext context =
            new AnnotationConfigApplicationContext(
                    ApplicationConfiguration.class
            );

    public static void main(String[] args){
        log.info("Started Logger");
        UserInterfaceLauncher launcher =
                new JavaFXGraphicalUserInterfaceLauncher(
                        context
                );
        launcher.launchWithDefaultStage(args);
    }
}
