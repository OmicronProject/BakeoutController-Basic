package unit.devices;

import kernel.serial_ports.PortCommunicator;

/**
 * Describes a test fixture that can both function as a
 * {@link kernel.serial_ports.PortCommunicator}, and allow a test to read
 * the results of what was written to the OutputStream.
 *
 * This is meant to serve as a representation of what the device will be
 * writing to.
 *
 * The data that the device reads can be edited, and the data that the
 * device wrote can be retrieved
 */
public interface DeviceCommunicator extends PortCommunicator {
    /**
     * @param dataForDeviceToRead The data that the input stream will read
     */
    void setInputStreamData(String dataForDeviceToRead);

    /**
     * @return The data that the device wrote to its output stream
     */
    String getOutputStreamData();

    /**
     * Reset the streams to their initial configuration
     */
    void clear();
}
