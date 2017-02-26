package ui.controllers;

import devices.PowerSupply;
import devices.PressureGauge;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import kernel.Kernel;
import kernel.views.DeviceContainer;
import org.springframework.beans.factory.annotation.Autowired;
import ui.Controller;

/**
 * Controls a menu that shows the list of devices currently connected
 */
@Controller
public class DeviceListController {

    /**
     * The kernel to which this controller is attached
     */
    @Autowired
    private Kernel kernel;

    /**
     * A spot in which to write in data regarding which devices are available
     */
    @FXML private GridPane deviceList;

    /**
     * Handler called when the refresh button is clicked.
     */
    @FXML public void handleRefreshButtonClicked(){
        DeviceContainer registry = kernel.getDeviceRegistryView();
        lookupPowerSupply(registry);
        lookupPressureGauge(registry);
    }

    /**
     * @return The attached kernel
     */
    public Kernel getKernel(){
        return kernel;
    }

    /**
     * @param kernel The kernel to which this controller is to be attached
     */
    public void setKernel(Kernel kernel){
        this.kernel = kernel;
    }

    /**
     * Look up a power supply, and notify the user if a power supply is found
     *
     * @param container The device container
     */
    private void lookupPowerSupply(DeviceContainer container){
        if(container.hasPowerSupply()){
            PowerSupply supply = container.getPowerSupply();
            DeviceListEntry entry = new DeviceListEntry(supply.getClass());
            deviceList.getChildren().add(entry);
        } else {
            deviceList.getChildren().add(new NoPowerSupplyText());
        }
    }

    /**
     * Look up a pressure gauge in the kernel, and notify the user if it is
     * found.
     * @param container The device container
     */
    private void lookupPressureGauge(DeviceContainer container){
        if(container.hasPressureGauge()){
            PressureGauge gauge = container.getPressureGauge();

            PressureGaugeEntry entry = new PressureGaugeEntry(
                    gauge.getClass());

            deviceList.getChildren().add(entry);
        } else {
            deviceList.getChildren().addAll(new NoPressureGaugeText());
        }
    }

    /**
     * A message indicating that the power supply was found
     */
    private class DeviceListEntry extends TilePane {

        /**
         * A javaFX id that uniquely identifies this message. This is used
         * for unit testing to indicate that the message was put on the UI.
         */
        private static final String id = "power-supply-message";

        /**
         * The text used to write the message
         */
        private Text typeText;

        /**
         * @param type The type of the power supply that was found
         */
        protected DeviceListEntry(Class type){
            this.setAlignment(Pos.CENTER);

            typeText = new Text();
            typeText.setText(type.toString());

            this.getChildren().add(typeText);
            this.setId(id);
        }
    }

    /**
     * A message indicating that no power supply was found
     */
    private class NoPowerSupplyText extends Text {

        /**
         * A javaFX id that uniquely identifies this message.
         */
        private static final String id = "no-power-supply-message";

        /**
         * Create the message
         */
        protected NoPowerSupplyText(){
            this.setText("No Power Supply");
            this.setFill(Color.RED);
            this.setId(id);
        }
    }

    /**
     * An entry indicating that the pressure gauge was found
     */
    private class PressureGaugeEntry extends TilePane {

        /**
         * The javaFX id of this message
         */
        private static final String id = "pressure-gauge-message";

        /**
         * The field representing the message
         */
        private Text text;

        /**
         * @param type The class representing the pressure gauge type
         */
        protected PressureGaugeEntry(Class type){
            this.setAlignment(Pos.CENTER);

            text = new Text();
            text.setText(type.toString());

            this.getChildren().addAll(text);

            this.setId(id);
        }
    }

    /**
     * A message indicating that the pressure gauge was not found
     */
    private class NoPressureGaugeText extends Text {

        /**
         * The message id
         */
        private static final String id = "no-pressure-gauge-message";

        /**
         * Create an instance of this message
         */
        protected NoPressureGaugeText(){
            this.setText("No Pressure Gauge");
            this.setFill(Color.RED);
            this.setId(id);
        }
    }
}
