package ui.controllers;

import devices.PowerSupply;
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
}
