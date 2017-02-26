package kernel.serial_ports;

import gnu.io.CommPortIdentifier;
import kernel.serial_ports.comm_port_wrapper.PortIdentifierGetter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

/**
 * Driver responsible for managing serial port names
 */
public class RXTXPortDriver implements PortDriver {
    private static Logger log = LoggerFactory.getLogger(RXTXPortDriver.class);

    /**
     * This name is used to sign out ports. A call from another application
     * to determine the owner of an RS232 port will see this name as the owner.
     */
    private final String applicationName;

    /**
     * Wrapper for the Java Communications API. Responsible for getting port
     * Identifiers
     */
    private final PortIdentifierGetter portWrapper;

    /**
     * @param applicationName The name to use when signing out ports
     * @param portWrapper An object capable of retrieving port names
     */
    public RXTXPortDriver(
            String applicationName, PortIdentifierGetter portWrapper
    ){
        this.applicationName = applicationName;
        this.portWrapper = portWrapper;
    }

    /**
     * @return The name of the application
     */
    @Override public String getApplicationName(){
        return this.applicationName;
    }

    /**
     * @return The names of all serial ports accessible to the system
     */
    @Override public List<String> getSerialPortNames(){
        Enumeration portIdentifiers = this.portWrapper.getPortIdentifiers();
        List<CommPortIdentifier> identifierList = castPortIdentifiersToList(
                portIdentifiers
        );
        List<CommPortIdentifier> serialPortList = filterForSerialPorts(
                identifierList
        );
        return getPortNamesForList(serialPortList);
    }

    /**
     * @param portName The name of the port that needs to be retrieved. An
     *                 example of a valid name is "/dev/ttyUSB0"
     * @return A wrapper for {@link gnu.io.RXTXPort} which can be used to
     * communicate with the required port
     */
    @Override public SerialPort getPortByName(String portName) {
        return new RXTXPortWrapper(portName);
    }

    /**
     * Iterate over an {@link Enumeration} of {@link CommPortIdentifier}s,
     * and cast each retrieved {@link CommPortIdentifier} to the
     * {@link CommPortIdentifier} type. This is a consequence of the Java
     * Communications API not specifying the type of port identifiers in the
     * {@link Enumeration} returned by
     * {@link CommPortIdentifier#getPortIdentifiers()}.
     * @param commPortIdentifiers An enumeration of identifiers needing to
     *                            be cast
     * @return A list of properly-cast identifiers
     */
    private static List<CommPortIdentifier> castPortIdentifiersToList(
            Enumeration commPortIdentifiers
    ){
        List<CommPortIdentifier> portIdentifierList = new Vector<>();
        CommPortIdentifier portIdentifier;

        while(commPortIdentifiers.hasMoreElements()){
            portIdentifier = castToPortIdentifier(
                commPortIdentifiers.nextElement()
            );
            portIdentifierList.add(portIdentifier);
        }

        return portIdentifierList;
    }

    /**
     * Like {@link #castPortIdentifiersToList(Enumeration)}, but only works on
     * one item in that list. This method performs the actual casting
     * @param portIdentifier The identifier to cast
     * @return A properly cast {@link CommPortIdentifier}
     */
    private static CommPortIdentifier castToPortIdentifier(
            Object portIdentifier){
        return (CommPortIdentifier)(portIdentifier);
    }

    /**
     * Builds a new {@link List} out of a {@link List} of
     * {@link CommPortIdentifier}s, keeping only the
     * {@link CommPortIdentifier}s that represent serial ports.
     * @param identifierList The list of {@link CommPortIdentifier}s to be
     *                       filtered
     * @return A list of serial ports available to the system
     */
    private List<CommPortIdentifier> filterForSerialPorts(
            List<CommPortIdentifier> identifierList){

        List<CommPortIdentifier> serialPorts = new ArrayList<>(
                identifierList.size()
        );

        for(CommPortIdentifier identifier: identifierList){
            if(identifier.getPortType() == CommPortIdentifier.PORT_SERIAL){
                writeLogEntryForSerialPort(identifier);
                serialPorts.add(identifier);
            }
        }

        return serialPorts;
    }

    /**
     * @param portIdentifiers The {@link List} of
     * {@link CommPortIdentifier}s for which names must be retrived
     * @return The names of ports accessible to the system
     */
    private static List<String> getPortNamesForList(
        List<CommPortIdentifier> portIdentifiers
    ){
        List<String> portNames = new ArrayList<>(portIdentifiers.size());

        for(CommPortIdentifier identifier: portIdentifiers){
            portNames.add(identifier.getName());
        }

        return portNames;
    }

    /**
     * Write a log entry indicating that a {@link CommPortIdentifier} was
     * identified as a {@link gnu.io.SerialPort}
     *
     * @param identifier The identifier for which the log entry is to be
     *                   written
     */
    private void writeLogEntryForSerialPort(CommPortIdentifier identifier){
        log.info(
                "Port identifier {} has been detected as a serial port",
                identifier
        );
    }
}
