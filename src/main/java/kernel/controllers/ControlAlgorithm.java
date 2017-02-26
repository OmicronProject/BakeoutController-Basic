package kernel.controllers;

import exceptions.UnableToRunControlAlgorithmException;
import kernel.Kernel;

/**
 * Base class for variable control sequences.
 */
public interface ControlAlgorithm {
    /**
     * @return {@link Boolean#TRUE} if the algorithm is running, and
     * {@link Boolean#FALSE} if not
     */
    Boolean isRunning();

    /**
     * @return The kernel to which this algorithm is currently attached
     */
    Kernel getKernel();

    /**
     * @param kernel The kernel to which this algorithm is to be attached
     */
    void setKernel(Kernel kernel);

    /**
     * Start the task
     *
     * @throws UnableToRunControlAlgorithmException if the task cannot be
     * started
     */
    void run() throws UnableToRunControlAlgorithmException;

    /**
     *
     * @return A number from 0 to 1 indicating how far along the task is.
     */
    Double getProgress();
}
