package kernel.serial_ports;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Describes how to get input and output streams for a port
 */
public interface PortCommunicator {

    /**
     * @return A stream useful for reading from the port
     */
    InputStream getInputStream() throws IOException;

    /**
     * @return A stream useful for writing to the port
     */
    OutputStream getOutputStream() throws IOException;
}
