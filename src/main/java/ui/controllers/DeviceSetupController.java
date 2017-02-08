package ui.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import kernel.Kernel;
import kernel.controllers.TDKLambdaPowerSupplyFactory;
import kernel.views.CommPortReporter;
import org.springframework.beans.factory.annotation.Autowired;
import ui.Controller;

/**
 * Controls the device setup
 */
@Controller
public class DeviceSetupController {
    @Autowired
    private Kernel kernel;

    @FXML private ComboBox<String> portSelector;

    @FXML
    public void initialize(){
        initializePortList();
    }

    @FXML
    public void handleGoButtonClicked(){
        configurePowerSupply();
    }

    private void initializePortList(){
        ObservableList<String> portsInBox = portSelector.getItems();
        CommPortReporter reporter = kernel.getCommPortReporter();

        portsInBox.addAll(reporter.getSerialPortNames());

        portSelector.getSelectionModel().select(0);
    }

    private void configurePowerSupply(){
        String portName = portSelector.getSelectionModel().getSelectedItem();

        TDKLambdaPowerSupplyFactory factory = kernel.getPowerSupplyFactory();
        factory.setPortName(portName);
    }
}
