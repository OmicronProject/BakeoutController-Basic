package kernel.controllers;

/**
 * Created by mkononen on 23/02/17.
 */
public interface VoltageSetPointAlgorithm extends ControlAlgorithm {
    Double getDesiredVoltage();

    void setDesiredVoltage(Double desiredVoltage);

    Float getPressureUpperBound();

    void setPressureUpperBound(Float pressureUpperBound);

    Integer getMaximumIterations();

    void setMaximumIterations(Integer maximumIterations);
}
