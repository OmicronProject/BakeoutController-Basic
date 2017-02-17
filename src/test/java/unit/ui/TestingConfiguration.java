package unit.ui;

import devices.PowerSupply;
import kernel.Kernel;
import kernel.controllers.PVCiPressureGaugeFactory;
import kernel.views.DeviceRegistry;
import kernel.controllers.TDKLambdaPowerSupplyFactory;
import kernel.views.CommPortReporter;
import kernel.views.VariableProviderRegistry;
import kernel.views.variables.Pressure;
import kernel.views.variables.VariableChangeEventListener;
import kernel.views.variables.VariableProvider;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.springframework.context.annotation.*;
import ui.UserInterfaceConfiguration;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for producing mock beans for the dependencies for this
 * application
 */
@Configuration
@Import(UserInterfaceConfiguration.class)
@Lazy
public class TestingConfiguration {

    /**
     * A stub representing {@link kernel.Kernel}
     */
    private volatile Kernel mockKernel;

    /**
     * The context in which mock beans are to be created
     */
    private volatile Mockery mockingContext;

    /**
     *
     */
    private volatile List<String> portList;

    private volatile DeviceRegistry mockDeviceRegistryView;

    private volatile VariableProviderRegistry mockVariableProviderRegistry;

    /**
     * @return The context in which mockery is to take place
     */
    @Bean
    @Scope("singleton")
    public Mockery mockingContext(){
        if(mockingContext == null) {
            mockingContext = new SynchronizedJUnit4Mockery();
        }
        return mockingContext;
    }

    /**
     * @return A list containing data of serial ports
     */
    @Bean
    @Scope("singleton")
    public List<String> testData(){
        portList = new ArrayList<>();
        portList.add("/dev/ttyUSB0");
        return portList;
    }

    /**
     * @return A mock reporter for returning serial port names
     */
    @Bean
    @Scope("singleton")
    public CommPortReporter portReporter(){
        return mockingContext().mock(CommPortReporter.class);
    }

    @Bean
    @Scope("singleton")
    public TDKLambdaPowerSupplyFactory tdkLambdaPowerSupplyFactory(){
        return mockingContext().mock(TDKLambdaPowerSupplyFactory.class);
    }

    @Bean
    @Scope("singleton")
    public PVCiPressureGaugeFactory pvCiPressureGaugeFactory(){
        return mockingContext().mock(PVCiPressureGaugeFactory.class);
    }

    @Bean
    @Scope("singleton")
    public DeviceRegistry deviceRegistryView(){
        if(mockDeviceRegistryView == null){
            mockDeviceRegistryView = mockingContext().mock(
                    DeviceRegistry.class
            );
        }
        return mockDeviceRegistryView;
    }

    @Bean
    @Scope("singleton")
    public VariableProviderRegistry variableProviderRegistry(){
        if (mockVariableProviderRegistry == null){
            mockVariableProviderRegistry = mockingContext().mock(
                    VariableProviderRegistry.class
            );
        }
        return mockVariableProviderRegistry;
    }

    @Bean
    @Scope("singleton")
    public PowerSupply powerSupply(){
        return mockingContext().mock(PowerSupply.class);
    }

    @Bean
    @Scope("singleton")
    public VariableProvider<Pressure> pressureProvider(){
        return mockingContext().mock(VariableProvider.class);
    }

    /**
     * @return A mock kernel
     */
    @Bean
    @Scope("singleton")
    public Kernel kernel(){
        mockKernel = mockingContext().mock(Kernel.class);
        mockingContext().checking(new ExpectationsForKernel());
        return mockKernel;
    }

    /**
     * The mockery that is to be used. This is an extension of the
     * traditional {@link JUnit4Mockery}, but with a {@link Synchroniser} to
     * manage access to mock objects across threads
     */
    private class SynchronizedJUnit4Mockery extends JUnit4Mockery {
        /**
         * Creates the mockery
         */
        public SynchronizedJUnit4Mockery(){
            setThreadingPolicy(new Synchroniser());
        }
    }

    /**
     * Defines the default allowed behaviour out of the stubbed-out
     * {@link kernel.Kernel}.
     */
    private class ExpectationsForKernel extends Expectations {
        /**
         * Set up the required behaviours
         */
        public ExpectationsForKernel(){
            expectationsForPortReporter();
            expectationsForSerialPortNames();
            expectationsForFactory();
            expectationsForPressureGaugeFactory();
            expectationsForDeviceRegistryView();
            expectationsForVariableProvider();
            expectationsForGetPressureProvider();
        }

        /**
         * Defines allowed behaviour for {@link Kernel#getCommPortReporter()}
         */
        private void expectationsForPortReporter(){
            allowing(mockKernel).getCommPortReporter();
            will(returnValue(portReporter()));
        }

        /**
         * Defines allowed behaviour for
         * {@link CommPortReporter#getSerialPortNames()}
         */
        private void expectationsForSerialPortNames() {
            allowing(portReporter()).getSerialPortNames();
            will(returnValue(testData()));
        }

        private void expectationsForFactory(){
            allowing(mockKernel).getPowerSupplyFactory();
            will(returnValue(tdkLambdaPowerSupplyFactory()));
        }

        private void expectationsForDeviceRegistryView(){
            allowing(mockKernel).getDeviceRegistryView();
            will(returnValue(deviceRegistryView()));
        }

        private void expectationsForPressureGaugeFactory(){
            allowing(mockKernel).getPressureGaugeFactory();
            will(returnValue(pvCiPressureGaugeFactory()));
        }

        private void expectationsForVariableProvider(){
            allowing(mockKernel).getVariableProvidersView();
            will(returnValue(variableProviderRegistry()));
        }

        private void expectationsForGetPressureProvider(){
            allowing(variableProviderRegistry()).getPressureProvider();
            will(returnValue(pressureProvider()));

            allowing(pressureProvider()).setPollingInterval(
                    with(any(Duration.class))
            );
            allowing(pressureProvider()).addOnChangeListener(
                    with(any(VariableChangeEventListener.class))
            );
        }
    }
}
