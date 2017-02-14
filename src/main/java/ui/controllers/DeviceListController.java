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
import kernel.views.DeviceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import ui.Controller;

/**
 * Controls a menu that shows the list of devices currently connected
 */
@Controller
public class DeviceListController {
    @Autowired
    private Kernel kernel;

    @FXML private GridPane deviceList;

    @FXML public void handleRefreshButtonClicked(){
        DeviceRegistry registry = kernel.getDeviceRegistryView();
        lookupPowerSupply(registry);
        lookupPressureGauge(registry);
    }

    private void lookupPowerSupply(DeviceRegistry registry){
        if(registry.hasPowerSupply()){
            PowerSupply supply = registry.getPowerSupply();

            DeviceListEntry entry = new DeviceListEntry(supply.getClass());

            deviceList.getChildren().add(entry);
        } else {
            deviceList.getChildren().add(new NoPowerSupplyText());
        }
    }

    private void lookupPressureGauge(DeviceRegistry registry){
        if(registry.hasPressureGauge()){
            PressureGauge gauge = registry.getPressureGauge();

            PressureGaugeEntry entry = new PressureGaugeEntry(
                    gauge.getClass());

            deviceList.getChildren().add(entry);
        } else {
            deviceList.getChildren().addAll(new NoPressureGaugeText());
        }
    }

    private class DeviceListEntry extends TilePane {
        private static final String id = "power-supply-message";

        private Text typeText;

        protected DeviceListEntry(Class type){
            this.setAlignment(Pos.CENTER);

            typeText = new Text();
            typeText.setText(type.toString());

            this.getChildren().add(typeText);

            this.setId(id);
        }
    }

    private class NoPowerSupplyText extends Text {
        private static final String id = "no-power-supply-message";

        protected NoPowerSupplyText(){
            this.setText("No Power Supply");
            this.setFill(Color.RED);
            this.setId(id);
        }
    }

    private class PressureGaugeEntry extends TilePane {
        private static final String id = "pressure-gauge-message";
        private Text text;

        protected PressureGaugeEntry(Class type){
            this.setAlignment(Pos.CENTER);

            text = new Text();
            text.setText(type.toString());

            this.getChildren().addAll(text);

            this.setId(id);
        }
    }

    private class NoPressureGaugeText extends Text {
        private static final String id = "no-pressure-gauge-message";

        protected NoPressureGaugeText(){
            this.setText("No Pressure Gauge");
            this.setFill(Color.RED);
            this.setId(id);
        }
    }
}
