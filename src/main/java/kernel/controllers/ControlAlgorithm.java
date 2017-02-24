package kernel.controllers;

import exceptions.UnableToRunControlAlgorithmException;
import kernel.Kernel;

/**
 * Created by mkononen on 23/02/17.
 */
public interface ControlAlgorithm {
    Boolean isRunning();

    Kernel getKernel();

    void setKernel(Kernel kernel);

    void run() throws UnableToRunControlAlgorithmException;

    Double getProgress();
}
