package kernel.models;

import kernel.controllers.ConnectionRequestFactory;
import kernel.serial_ports.PortConfiguration;
import kernel.views.ConnectionRequest;
import kernel.views.DeviceListEntry;

/**
 * Created by mkononen on 07/02/17.
 */
public class StandaloneConnectionRequest implements ConnectionRequest,
        ConnectionRequestFactory {
    private DeviceListEntry deviceListEntry;
    private Integer address;
    private String portName;
    private PortConfiguration portConfiguration;

    public StandaloneConnectionRequest(){}

    public StandaloneConnectionRequest(
        DeviceListEntry deviceListEntry, Integer address, String portName
    ){
        this.deviceListEntry = deviceListEntry;
        this.address = address;
        this.portName = portName;
    }

    @Override public DeviceListEntry getDeviceListEntry(){
            return deviceListEntry;
    }

    @Override public void setDeviceListEntry(DeviceListEntry entry){
        this.deviceListEntry = entry;
    }

    @Override public Integer getAddress(){
        return this.address;
    }

    @Override public void setAddress(Integer address){
        this.address = address;
    }

    @Override public String getPortName(){
        return this.portName;
    }

    @Override public void setPortName(String portName){
        this.portName = portName;
    }

    @Override public PortConfiguration getPortConfiguration(){
        return this.portConfiguration;
    }

    @Override public void setPortConfiguration(PortConfiguration
                                                       configuration){
        this.portConfiguration = configuration;
    }

    @Override public ConnectionRequest getConnectionRequest(){
        return this;
    }
}
