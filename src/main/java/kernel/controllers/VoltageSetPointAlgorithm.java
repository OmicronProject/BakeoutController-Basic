package kernel.controllers;

/**
 * Describes an algorithm to bring the power supply voltage up to a
 * particular value, while keeping the pressure below a particular value
 */
public interface VoltageSetPointAlgorithm extends ControlAlgorithm {
    /**
     * @return The desired voltage
     */
    Double getDesiredVoltage();

    /**
     * @param desiredVoltage The voltage at which this algorithm is to
     *                       terminate
     */
    void setDesiredVoltage(Double desiredVoltage);

    /**
     * @return The maximum pressure allowed
     */
    Float getPressureUpperBound();

    /**
     * @param pressureUpperBound The maximum allowed pressure
     */
    void setPressureUpperBound(Float pressureUpperBound);

    /**
     * @return The maximum number of iterations that a particular step of
     * the algorithm will iterate over before throwing an error. This is
     * meant to provide an escape condition on the algorithm, ensuring that
     * it will terminate.
     */
    Integer getMaximumIterations();

    /**
     * @param maximumIterations The desired maximum number of iterations
     */
    void setMaximumIterations(Integer maximumIterations);
}
