package unit.ui;

import devices.PowerSupply;
import kernel.Kernel;
import kernel.controllers.PVCiPressureGaugeFactory;
import kernel.controllers.VoltageSetPointAlgorithm;
import kernel.views.DeviceContainer;
import kernel.controllers.TDKLambdaPowerSupplyFactory;
import kernel.views.CommPortReporter;
import kernel.views.VariableProviderContainer;
import kernel.views.variables.*;
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

    private volatile DeviceContainer mockDeviceContainerView;

    private volatile VariableProviderContainer mockVariableProviderContainer;

    private volatile MockVoltageProvider mockVoltageProvider =
            new MockVoltageProvider();

    private volatile VoltageSetPointAlgorithm mockAlgorithm;

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
    public DeviceContainer deviceRegistryView(){
        if(mockDeviceContainerView == null){
            mockDeviceContainerView = mockingContext().mock(
                    DeviceContainer.class
            );
        }
        return mockDeviceContainerView;
    }

    @Bean
    @Scope("singleton")
    public VariableProviderContainer variableProviderRegistry(){
        if (mockVariableProviderContainer == null){
            mockVariableProviderContainer = mockingContext().mock(
                    VariableProviderContainer.class
            );
        }
        return mockVariableProviderContainer;
    }

    @Bean
    @Scope("singleton")
    public PowerSupply powerSupply(){
        return mockingContext().mock(PowerSupply.class);
    }

    @Bean
    @Scope("singleton")
    public PressureProvider pressureProvider(){
        return mockingContext().mock(PressureProvider.class);
    }

    @Bean
    @Scope("singleton")
    public VoltageProvider voltageProvider(){
        return mockVoltageProvider;
    }

    /**
     * @return A mock kernel
     */
    @Bean
    @Scope("singleton")
    public Kernel kernel() throws Exception {
        mockKernel = mockingContext().mock(Kernel.class);
        mockingContext().checking(new ExpectationsForKernel());
        return mockKernel;
    }

    @Bean
    @Scope("singleton")
    public VoltageSetPointAlgorithm algorithm(){
        mockAlgorithm = mockingContext().mock(VoltageSetPointAlgorithm.class);
        return mockAlgorithm;
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
        public ExpectationsForKernel() throws Exception {
            expectationsForPortReporter();
            expectationsForSerialPortNames();
            expectationsForFactory();
            expectationsForPressureGaugeFactory();
            expectationsForDeviceRegistryView();
            expectationsForVariableProvider();
            expectationsForGetPressureProvider();
            expectationsForGetVoltageProvider();
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

        private void expectationsForGetPressureProvider() throws Exception {
            allowing(variableProviderRegistry()).getPressureProvider();
            will(returnValue(pressureProvider()));

            allowing(variableProviderRegistry()).hasPressureProvider();
            will(returnValue(Boolean.TRUE));

            allowing(pressureProvider()).setPollingInterval(
                    with(any(Duration.class))
            );
            allowing(pressureProvider()).addOnChangeListener(
                    with(any(VariableChangeEventListener.class))
            );
        }

        private void expectationsForGetVoltageProvider() throws Exception {
            allowing(variableProviderRegistry()).getVoltageProvider();
            will(returnValue(voltageProvider()));
            allowing(variableProviderRegistry()).hasVoltageProvider();
            will(returnValue(Boolean.TRUE));
        }
    }
}
