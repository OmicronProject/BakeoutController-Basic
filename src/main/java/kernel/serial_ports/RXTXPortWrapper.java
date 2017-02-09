package kernel.serial_ports;

import gnu.io.PortInUseException;
import gnu.io.RXTXPort;
import gnu.io.UnsupportedCommOperationException;
import org.jetbrains.annotations.Contract;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Wraps the RXTX port
 */
public final class RXTXPortWrapper implements SerialPort, PortCommunicator,
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
     * The desired port configuration
     */
    private PortConfiguration desiredPortConfiguration;

    /**
     * Ensures that the port is closed if SIGTERM or an equivalent signal is
     * sent to the application
     */
    private Thread shutdownThread;

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
    @Contract(pure = true)
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
        writeLogEntryForOutputStreamRequest();
        return this.port.getOutputStream();
    }

    /**
     * @return Something capable of expressing the port's configuration
     */
    @Contract(pure = true)
    @Override public PortConfiguration getConfig(){
        return this;
    }

    /**
     * @return The current baud rate set on the port
     */
    @Contract(pure = true)
    @Override public int getBaudRate(){
        return this.port.getBaudRate();
    }

    /**
     * @return The current number of stop bits on the port
     */
    @Contract(pure = true)
    @Override public int getStopBits(){
        return this.port.getStopBits();
    }

    /**
     * @return The number of data bits on the port
     */
    @Contract(pure = true)
    @Override public int getDataBits(){
        return this.port.getDataBits();
    }

    /**
     * @return The number of parity bits set on the port
     */
    @Contract(pure = true)
    @Override public int getParityBits(){
        return this.port.getParity();
    }

    /**
     * Sets the port configuration to a new config
     * @param newConfig the new parameters required of the port
     */
    @Override public void setConfig(PortConfiguration newConfig) {
        this.desiredPortConfiguration = newConfig;
    }

    /**
     * Open the port
     * @throws PortInUseException if the port cannot be opened
     * @throws UnsupportedCommOperationException if the port can be opened,
     * but the configuration cannot be set
     */
    @Override public void open() throws PortInUseException,
        UnsupportedCommOperationException {
        if (!this.isPortOpen){
            this.port = new RXTXPort(this.portName);
            this.isPortOpen = true;

            shutdownThread = new PortShutdown(this);
            Runtime.getRuntime().addShutdownHook(shutdownThread);

            try {
                setConfigurationParametersForOpenPort();
            } catch (IOException error){
                throw new UnsupportedCommOperationException(
                        "Unable to set port configuration parameters"
                );
            }

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

            Runtime.getRuntime().removeShutdownHook(shutdownThread);

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

    /**
     * Transfer the desired port configuration from the desired
     * configuration to the open port.
     *
     * @throws UnsupportedCommOperationException if the parameters cannot be
     * set
     */
    private void setConfigurationParametersForOpenPort() throws
            UnsupportedCommOperationException, IOException {
        assertPortOpen();
        int baudRate = desiredPortConfiguration.getBaudRate();
        int dataBits = desiredPortConfiguration.getDataBits();
        int stopBits = desiredPortConfiguration.getStopBits();
        int parityBits = desiredPortConfiguration.getParityBits();

        this.port.setSerialPortParams(
                baudRate, dataBits, stopBits, parityBits
        );

        writeLogEntryForParametersSet(baudRate, dataBits, stopBits,
                parityBits);
    }

    /**
     * @param portName The name of the port that is managed by this object
     */
    private void writeLogEntryForInitialization(String portName){
        log.debug(
                "Port wrapper initialized for RS232 port with name {}",
                portName
        );
    }

    /**
     * Writes a log entry stating that the input stream for this port was
     * requested
     */
    private void writeLogEntryForInputStreamRequest(){
        log.debug(
                "Input stream requested for RS232 port {}", this.portName
        );
    }

    /**
     * Writes a log entry stating that the output stream for this port was
     * requested
     */
    private void writeLogEntryForOutputStreamRequest(){
        log.debug(
                "Output stream requested for RS232 port {}", this.portName
        );
    }

    /**
     * Writes a series of log entries communicating the parameters to which
     * the serial port was set
     *
     * @param baudRate The Baud rate (bits transferred per second)
     * @param dataBits The number of data bits in a frame
     * @param stopBits The number of bits in a frame indicating that the
     *                 message has ended
     * @param parityBits The scheme used to check parity
     */
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

    /**
     * Thread responsible for handling a termination signal. Ensures that
     * the port is cleaned up should the application be instructed to exit.
     */
    private final class PortShutdown extends Thread {
        /**
         * The thread's log
         */
        private final Logger log = LoggerFactory.getLogger(PortShutdown.class);

        /**
         * The serial port that this thread is managing
         */
        private final SerialPort port;

        /**
         * @param port The port to manage
         */
        public PortShutdown(SerialPort port){
            this.port = port;
        }

        /**
         * Shut down the port, and log it out
         */
        @Override
        public void run() {
            log.info("Port {} caught shutdown signal. Closing", portName);
            this.port.close();
            log.info("Port {} successfully closed", portName);
        }
    }
}
