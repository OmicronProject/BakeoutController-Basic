package kernel.controllers;

import devices.PowerSupply;
import gnu.io.PortInUseException;

import java.io.IOException;

/**
 * Created by mkononen on 07/02/17.
 */
public interface TDKLambdaPowerSupplyFactory {
    void setPortName(String portName);
    PowerSupply getPowerSupply() throws PortInUseException, IOException;
}
