package kernel.views;

/**
 * Describes a method for reporting voltage
 */
public interface VoltageReporter {

    /**
     * @return The current measured voltage coming out of the power supply
     */
    Double getVoltage();
}
