package kernel.serial_ports;

import gnu.io.PortInUseException;
import gnu.io.RXTXPort;
import gnu.io.UnsupportedCommOperationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Wraps the RXTX port
 */
final class RXTXPortWrapper implements SerialPort, PortCommunicator,
        PortConfiguration {

    /**
     * The application log to which logging parameters are to be written
     */
    private static Logger log = LoggerFactory.getLogger(RXTXPortWrapper.class);

    /**
     * The port that is to be wrapped
     */
    private RXTXPort port;

    /**
     * The name of the wrapped port. It is possible for this wrapper to
     * exist without a respective {@link RXTXPort}. This is because
     * instantiation of {@link RXTXPort} requires that the port is opened.
     */
    private final String portName;

    /**
     * True if the port is open and false if not
     */
    private boolean isPortOpen;

    /**
     * @param portName the name of the port to wrap.
     */
    RXTXPortWrapper(String portName){
        writeLogEntryForInitialization(portName);
        this.portName = portName;
    }

    /**
     * @return Something capable of retrieving an input and output stream
     * for communicating through the port
     */
    @Override public PortCommunicator getCommunicator(){
        return this;
    }

    /**
     * @return The input stream for the wrapped port
     * @throws IOException if the port is not open. The resulting
     * {@link InputStream} cannot be retrieved
     */
    @Override public InputStream getInputStream() throws IOException {
        assertPortOpen();
        writeLogEntryForInputStreamRequest();
        return this.port.getInputStream();
    }

    /**
     * @return The output stream for the wrapped port
     * @throws IOException if the port is not open. The port must be open
     * prior to retrieving the port's {@link OutputStream}
     */
    @Override public OutputStream getOutputStream() throws IOException {
        assertPortOpen();
        return this.port.getOutputStream();
    }

    /**
     * @return Something capable of expressing the port's configuration
     */
    @Override public PortConfiguration getConfig(){
        return this;
    }

    /**
     * @return The current baud rate set on the port
     */
    @Override public int getBaudRate(){
        return this.port.getBaudRate();
    }

    /**
     * @return The current number of stop bits on the port
     */
    @Override public int getStopBits(){
        return this.port.getStopBits();
    }

    /**
     * @return The number of data bits on the port
     */
    @Override public int getDataBits(){
        return this.port.getDataBits();
    }

    /**
     * @return The number of parity bits set on the port
     */
    @Override public int getParityBits(){
        return this.port.getParity();
    }

    /**
     * Sets the port configuration to a new config
     * @param newConfig the new parameters required of the port
     * @throws UnsupportedCommOperationException if it cannot be done
     */
    @Override public void setConfig(PortConfiguration newConfig) throws
            UnsupportedCommOperationException {
        int baudRate = newConfig.getBaudRate();
        int dataBits = newConfig.getDataBits();
        int stopBits = newConfig.getStopBits();
        int parityBits = newConfig.getParityBits();

        this.port.setSerialPortParams(
                baudRate, dataBits, stopBits, parityBits
        );

        writeLogEntryForParametersSet(baudRate, dataBits, stopBits,
                parityBits);
    }

    /**
     * Open the port
     * @throws PortInUseException if the port cannot be opened
     */
    @Override public void open() throws PortInUseException {
        if (!this.isPortOpen){
            this.port = new RXTXPort(this.portName);
            this.isPortOpen = true;
            writeLogEntryForPortOpen();
        }
    }

    /**
     * @return true if the port is open, otherwise false
     */
    @Override public boolean isPortOpen(){
        return this.isPortOpen;
    }

    /**
     * closes the port
     */
    @Override public void close(){
        if (this.isPortOpen){
            this.port.close();
            this.isPortOpen = false;

            writeLogEntryForPortClosed();
        }
    }

    /**
     * @throws IOException if the port is not open
     */
    private void assertPortOpen() throws IOException {
        if (!this.isPortOpen){
            throw new IOException("Attempted to access a resource that " +
                    "requires the port to be open");
        }
    }

    private void writeLogEntryForInitialization(String portName){
        log.debug(
                "Port wrapper initialized for RS232 port with name {}",
                portName
        );
    }

    private void writeLogEntryForInputStreamRequest(){
        log.debug(
                "Input stream requested for RS232 port {}", this.portName
        );
    }

    private void writeLogEntryForParametersSet(
            int baudRate, int dataBits, int stopBits, int parityBits
    ){
        String parameterTemplate = "Parameter {} for port {} was set to {}.";

        log.info(
                parameterTemplate, "Baud Rate", portName, baudRate
        );
        log.info(
                parameterTemplate, "Data Bits", portName, dataBits
        );
        log.info(
                parameterTemplate, "Stop Bits", portName, stopBits
        );
        log.info(
                parameterTemplate, "Stop Bits", portName, parityBits
        );
    }

    private void writeLogEntryForPortOpen(){
        log.info("Port {} was opened.", portName);
    }

    private void writeLogEntryForPortClosed(){
        log.info("Port {} was closed", portName);
    }
}
