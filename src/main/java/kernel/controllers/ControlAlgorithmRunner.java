package kernel.controllers;

/**
 * Describes a control algorithm
 */
public interface ControlAlgorithmRunner extends TaskRunner {
    void execute(ControlAlgorithm controlAlgorithm);
}
