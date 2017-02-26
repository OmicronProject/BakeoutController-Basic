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
import kernel.controllers.PVCiPressureGaugeFactory;
import kernel.controllers.TDKLambdaPowerSupplyFactory;
import kernel.views.CommPortReporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ui.Controller;

import java.io.IOException;
import java.util.List;

/**
 * Controls the panel for the device setup tab. This panel is where a user
 * associates each required device with a port.
 */
@Controller
public class DeviceSetupController {

    /**
     * The log for the controller
     */
    private static final Logger log = LoggerFactory.getLogger(
            DeviceSetupController.class);

    /**
     * The kernel to which this controller is attached
     */
    @Autowired private Kernel kernel;

    /**
     * A drop-down menu in which the port for the power supply is selected
     */
    @FXML private ComboBox<String> tdkPortSelector;

    /**
     * A drop-down menu in which the port for the pressure gauge can be
     * selected
     */
    @FXML private ComboBox<String> pvciPortSelector;

    /**
     * A field in which the status of device setup is reported
     */
    @FXML private Text statusReportField;

    /**
     * Start the port selector
     */
    @FXML public void initialize(){
        initializePortList();
    }

    /**
     * Describes what happens when the user clicks the go button. Set up the
     * devices.
     */
    @FXML public void handleGoButtonClicked(){
        configurePowerSupply();
        configurePressureGauge();
    }

    /**
     * Select and populate the drop-down menus for active ports.
     */
    private void initializePortList(){
        ObservableList<String> portsInBox = tdkPortSelector.getItems();
        ObservableList<String> portsInPVCiSelector = pvciPortSelector
                .getItems();

        CommPortReporter reporter = kernel.getCommPortReporter();

        List<String> serialPortNames = reporter.getSerialPortNames();

        portsInBox.addAll(serialPortNames);
        portsInPVCiSelector.addAll(serialPortNames);

        tdkPortSelector.getSelectionModel().select(0);
        pvciPortSelector.getSelectionModel().select(0);
    }

    /**
     * Set up the power supply.
     */
    private void configurePowerSupply(){
        String portName = tdkPortSelector.getSelectionModel().getSelectedItem();

        TDKLambdaPowerSupplyFactory factory = kernel.getPowerSupplyFactory();
        factory.setPortName(portName);

        try {
            factory.makePowerSupply();
        } catch (IOException error){
            handleIOException();
        } catch (UnsupportedCommOperationException error){
            handleUnsupportedCommException();
        } catch (PortInUseException error){
            handlePortInUseException();
        } catch (DeviceAlreadyCreatedException error){
            handleDeviceAlreadyCreatedException();
        }
    }

    /**
     * write a message indicating that an {@link IOException } was thrown.
     */
    private void handleIOException(){
        writeErrorMessage(
                "Port was opened, but unable to establish device I/O",
                "io-exception-message"
        );
    }

    /**
     * If an {@link UnsupportedCommOperationException } was thrown, inform
     * the user that this exception occured.
     */
    private void handleUnsupportedCommException(){
        writeErrorMessage(
                "Unable to set parameters to establish serial port " +
                        "connection",
                "comm-operation-exception-message"
        );
    }

    /**
     * Write a message indicating that a {@link PortInUseException} was thrown.
     */
    private void handlePortInUseException(){
        writeErrorMessage(
                "Unable to acquire port. Port is already in use.",
                "port-in-use-exception-message"
        );
    }

    /**
     * Write an error message to the
     * {@link DeviceSetupController#statusReportField}
     * @param text The message to write
     * @param fieldId A unique ID for the message, used in unit testing to
     *                determine whether the message was written
     */
    private void writeErrorMessage(String text, String fieldId){
        statusReportField.setText(text);
        statusReportField.setFill(Color.RED);
        statusReportField.setId(fieldId);
    }

    /**
     * If the power supply was already created, a
     * {@link DeviceAlreadyCreatedException} will be thrown. This method
     * informs the user that this error was thrown.
     */
    private void handleDeviceAlreadyCreatedException(){
        statusReportField.setText(
                "Attempted to create power supply twice"
        );
        statusReportField.setId(
                "device-created-exception-message"
        );
    }

    /**
     * Set up the PVCi pressure gauge
     */
    private void configurePressureGauge(){
        String portName = pvciPortSelector.getSelectionModel()
                .getSelectedItem();

        PVCiPressureGaugeFactory factory = kernel.getPressureGaugeFactory();

        factory.setPortName(portName);
        factory.setAddress(2);

        try {
            factory.makePressureGauge();
        } catch (IOException error){
            handlePVCiIOException(error);
        }
    }

    /**
     * If the PVCi pressure gauge was not created, an {@link IOException}
     * will be thrown. This method handles the error
     * @param error The error thrown
     */
    private void handlePVCiIOException(Throwable error){
        log.error("Setup thew exception", error);
        writeErrorMessage(
                "Unable to establish communication with Pressure Gauge",
                "pvci-io-exception-message"
        );
    }
}
