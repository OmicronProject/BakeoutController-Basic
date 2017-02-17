package ui.controllers;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import kernel.Kernel;
import kernel.views.variables.Pressure;
import kernel.views.variables.VariableChangeEventListener;
import kernel.views.variables.VariableProvider;
import org.springframework.beans.factory.annotation.Autowired;
import ui.Controller;

import java.time.Duration;

/**
 * Responsible for updating the results panel with results of the bakeout
 */
@Controller
public class ResultController {

    private static final Duration pressurePollingInterval =
            Duration.ofMillis(500);

    @Autowired
    private Kernel kernel;

    @FXML private volatile Text reportedPressure;

    @FXML public void initialize(){
        VariableProvider<Pressure> provider = kernel.getVariableProvidersView()
                .getPressureProvider();

        provider.setPollingInterval(pressurePollingInterval);
        provider.addOnChangeListener(
                new PressureChangeHandler(reportedPressure)
        );
    }

    public static class PressureChangeHandler implements
            VariableChangeEventListener<Pressure> {
        private final Text pressureText;

        public PressureChangeHandler(Text pressureText){
            this.pressureText = pressureText;
        }

        @Override
        public void onChange(Pressure newPressure){
            this.pressureText.setText(getMessageToWrite(newPressure));
        }

        private String getMessageToWrite(Pressure newPressure){
            return newPressure.getValue().toString();
        }
    }

}
