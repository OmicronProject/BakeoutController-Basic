package kernel.controllers;

import kernel.serial_ports.PortConfiguration;

/**
 * Describes how to create port configurations
 */
public interface RS232PortConfigurationFactory {

    /**
     * @param baudRate The Baud rate to set
     */
    void setBaudRate(Integer baudRate);

    /**
     * @param dataBits The number of data bits that the configuration holds
     */
    void setDataBits(Integer dataBits);

    /**
     * @param stopBits The number of stop bits
     */
    void setStopBits(Integer stopBits);

    /**
     * @param parityBits The number of parity bits
     */
    void setParityBits(Integer parityBits);

    /**
     * @return A port configuration created with the appropriate values for
     * each parameter
     */
    PortConfiguration getConfiguration();
}
