package kernel.controllers;

import devices.PowerSupply;
import gnu.io.PortInUseException;
import gnu.io.UnsupportedCommOperationException;
import kernel.Kernel;

import java.io.IOException;

/**
 * Created by mkononen on 07/02/17.
 */
public interface TDKLambdaPowerSupplyFactory {
    Kernel getKernel();
    void setKernel(Kernel kernel);
    void setPortName(String portName);
    PowerSupply getPowerSupply() throws PortInUseException, IOException,
            UnsupportedCommOperationException;
}
