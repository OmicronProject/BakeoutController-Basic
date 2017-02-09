package ui.controllers;

import exceptions.DeviceAlreadyCreatedException;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import kernel.Kernel;
import kernel.controllers.TDKLambdaPowerSupplyFactory;
import kernel.views.CommPortReporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ui.Controller;

import java.io.IOException;

/**
 * Controls the device setup
 */
@Controller
public class DeviceSetupController {
    private static Logger log = LoggerFactory.getLogger(
            DeviceSetupController.class);

    @Autowired
    private Kernel kernel;

    @FXML private ComboBox<String> portSelector;

    @FXML private Text statusReportField;

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

        try {
            factory.makePowerSupply();
        } catch (IOException error){
            handleIOException(error);
        } catch (UnsupportedCommOperationException error){
            handleUnsupportedCommException(error);
        } catch (PortInUseException error){
            handlePortInUseException(error);
        } catch (DeviceAlreadyCreatedException error){
            handleDeviceAlreadyCreatedException(error);
        }
    }

    private void handleIOException(IOException error){
        log.error("IO exception thrown", error);
        writeErrorMessage(
                "Port was opened, but unable to establish device I/O",
                "io-exception-message"
        );
    }

    private void handleUnsupportedCommException
            (UnsupportedCommOperationException error){
        writeErrorMessage(
                "Unable to set parameters to establish serial port " +
                        "connection",
                "comm-operation-exception-message"
        );
    }

    private void handlePortInUseException(PortInUseException error){
        writeErrorMessage(
                "Unable to acquire port. Port is already in use.",
                "port-in-use-exception-message"
        );
    }

    private void writeErrorMessage(String text, String fieldId){
        statusReportField.setText(text);
        statusReportField.setFill(Color.RED);
        statusReportField.setId(fieldId);
    }

    private void handleDeviceAlreadyCreatedException
            (DeviceAlreadyCreatedException error){
        statusReportField.setText(
                "Attempted to create power supply twice"
        );
        statusReportField.setId(
                "device-created-exception-message"
        );
    }
}
