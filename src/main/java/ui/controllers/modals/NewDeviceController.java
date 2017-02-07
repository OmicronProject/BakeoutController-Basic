package ui.controllers.modals;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import kernel.Kernel;
import kernel.controllers.ConnectionRequestFactory;
import kernel.controllers.DeviceConnector;
import kernel.controllers.RS232PortConfigurationFactory;
import kernel.serial_ports.PortConfiguration;
import kernel.views.CommPortReporter;
import kernel.views.ConnectionRequest;
import kernel.views.DeviceListEntry;
import org.springframework.beans.factory.annotation.Autowired;
import ui.Controller;

/**
 * Contains a controller for the form to connect Devices
 */
@Controller
public class NewDeviceController {

    /**
     * The kernel to use for communicating with hardware
     */
    @Autowired private Kernel kernel;

    /**
     * The selector for devices
     */
    @FXML private ComboBox<DeviceListEntry> deviceSelector;

    /**
     * A selector for available ports
     */
    @FXML private ComboBox<String> portSelector;

    @FXML private ComboBox<Integer> baudRateSelector;

    @FXML private ComboBox<Integer> paritySelector;

    @FXML private ComboBox<Integer> stopBitSelector;

    @FXML private ComboBox<Integer> dataBitsSelector;

    @FXML private TextField addressField;

    /**
     * If the "close" button has been clicked, close the window that this
     * controls
     * @param event The event broadcast by JavaFX stating that the button
     *              was closed
     */
    @FXML public void handleCloseButtonClicked(ActionEvent event){
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    /**
     * Runs when the controller is first loaded. Fetches information from
     * the kernel.
     */
    @FXML
    public void initialize(){
        initializePortList();
        initializeDeviceList();
    }

    /**
     *
     */
    @FXML public void handleRefreshButtonClicked(ActionEvent event){
        updatePortList();
    }

    @FXML public void handleDeviceSelected(ActionEvent event){
        DeviceListEntry entry = deviceSelector.getSelectionModel()
                .getSelectedItem();
        PortConfiguration defaultConfiguration = entry
                .getDefaultConfiguration();

        clearConnectionDetailsSelectors();
        populateConnectionDetailsSelectors(defaultConfiguration);
        selectDefaultConnectionDetails(defaultConfiguration);
        checkAddressField(entry);
    }

    @FXML public void handleConnectButtonClicked(ActionEvent event){
        DeviceListEntry entry = deviceSelector.getSelectionModel()
                .getSelectedItem();
        String portName = portSelector.getSelectionModel().getSelectedItem();
        PortConfiguration config = assemblePortConfigurationFromSelectors();
        Integer address = Integer.parseInt(addressField.getText());

        ConnectionRequest request = assembleConnectionRequest(entry, portName,
                config, address);

        DeviceConnector connector = kernel.getDeviceConnector();

        connector.setConnectionRequest(request);

        handleConnection(connector);
    }

    private void initializePortList(){
        ObservableList<String> portsInBox = portSelector.getItems();
        CommPortReporter reporter = kernel.getCommPortReporter();

        for(String portName: reporter.getSerialPortNames()){
            portsInBox.add(portName);
        }
    }

    private void updatePortList(){
        portSelector.getItems().addAll(
                kernel.getCommPortReporter().getSerialPortNames()
        );
    }

    private void initializeDeviceList(){
        deviceSelector.getItems().addAll(
                kernel.getDeviceReporter().getRS232Devices()
        );
    }

    private void populateConnectionDetailsSelectors(
            PortConfiguration configuration
    ){
        baudRateSelector.getItems().add(configuration.getBaudRate());
        paritySelector.getItems().add(configuration.getParityBits());
        stopBitSelector.getItems().add(configuration.getStopBits());
        dataBitsSelector.getItems().addAll(configuration.getDataBits());
    }

    private void clearConnectionDetailsSelectors(){
        baudRateSelector.getItems().removeAll();
        paritySelector.getItems().removeAll();
        stopBitSelector.getItems().removeAll();
        dataBitsSelector.getItems().removeAll();
    }

    private void selectDefaultConnectionDetails(
            PortConfiguration defaultConfiguration
    ){
        baudRateSelector.getSelectionModel().select(
                (Integer) defaultConfiguration.getBaudRate()
        );
        paritySelector.getSelectionModel().select(
                (Integer) defaultConfiguration.getParityBits()
        );
        stopBitSelector.getSelectionModel().select(
                (Integer) defaultConfiguration.getStopBits()
        );
        dataBitsSelector.getSelectionModel().select(
                (Integer) defaultConfiguration.getDataBits()
        );
    }

    private void checkAddressField(DeviceListEntry entry){
        if(entry.requiresAddress()){
            addressField.setDisable(false);
        }
    }


    private PortConfiguration assemblePortConfigurationFromSelectors(){
        RS232PortConfigurationFactory configMaker = kernel
                .getRS232PortConfigurationFactory();

        configMaker.setBaudRate(
                baudRateSelector.getSelectionModel().getSelectedItem()
        );
        configMaker.setDataBits(
                dataBitsSelector.getSelectionModel().getSelectedItem()
        );
        configMaker.setParityBits(
                paritySelector.getSelectionModel().getSelectedItem()
        );
        configMaker.setStopBits(
                stopBitSelector.getSelectionModel().getSelectedItem()
        );

        return configMaker.getConfiguration();
    }

    private ConnectionRequest assembleConnectionRequest(
            DeviceListEntry entry, String portName,
            PortConfiguration portConfiguration, Integer address
    ){
        ConnectionRequestFactory factory = kernel
                .getConnectionRequestFactory();

        factory.setAddress(address);
        factory.setPortName(portName);
        factory.setDeviceListEntry(entry);
        factory.setPortConfiguration(portConfiguration);

        return factory.getConnectionRequest();
    }

    private void handleConnection(DeviceConnector connector){
        try {
            connector.connect();
        } catch (Exception error){
            throw new RuntimeException(error);
        }
    }
}
