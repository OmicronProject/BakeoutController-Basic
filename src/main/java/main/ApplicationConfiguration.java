package main;

import kernel.ApplicationKernelFactory;
import kernel.Kernel;
import kernel.KernelFactory;
import kernel.serial_ports.PortDriver;
import kernel.serial_ports.RXTXPortDriver;
import kernel.serial_ports.comm_port_wrapper.JavaCommsAPIWrapper;
import kernel.serial_ports.comm_port_wrapper.PortIdentifierGetter;
import org.springframework.context.annotation.*;
import ui.UserInterfaceConfiguration;

/**
 * Inversion of Control (IOC) container for the application
 */
@Configuration
@Import(UserInterfaceConfiguration.class)
@Lazy
public class ApplicationConfiguration {

    /**
     * The name of the application
     */
    private static final String applicationName = "BakeoutController";

    /**
     * @return The name of the application
     */
    public static String getApplicationName(){
        return applicationName;
    }

    /**
     *
     * @return The wrapper for the Java Communications API
     */
    @Bean
    public static PortIdentifierGetter portWrapper(){
        return new JavaCommsAPIWrapper();
    }

    /**
     * @return The driver for the port
     */
    @Bean
    public static PortDriver portDriver(){
        return new RXTXPortDriver(
            getApplicationName(), portWrapper()
        );
    }

    /**
     * Bootstraps the Kernel and launches it
     * @return The kernel
     */
    @Bean
    @Scope("singleton")
    public static Kernel kernel(){
        KernelFactory kernelFactory = new ApplicationKernelFactory();
        kernelFactory.setPortDriver(portDriver());

        assert kernelFactory.canKernelBeStarted();

        return kernelFactory.getKernelInstance();
    }
}
