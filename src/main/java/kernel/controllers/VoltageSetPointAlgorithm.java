package kernel.controllers;

/**
 * Created by mkononen on 23/02/17.
 */
public interface VoltageSetPointAlgorithm {
    Float getDesiredVoltage();

    void setDesiredVoltage(Float desiredVoltage);

    Float getUpperPressureBound();

    void setUpperPressureBound(Float upperPressureBound);
}
