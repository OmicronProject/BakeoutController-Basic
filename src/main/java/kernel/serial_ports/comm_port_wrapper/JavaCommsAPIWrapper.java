package kernel.serial_ports.comm_port_wrapper;

import gnu.io.CommPortIdentifier;

import java.util.Enumeration;

/**
 * Implements a direct wrapper for static methods from the Java
 * Communications API
 */
public class JavaCommsAPIWrapper implements PortIdentifierGetter {
    /**
     * @return The result of {@link CommPortIdentifier#getPortIdentifiers()}
     */
    @Override public Enumeration getPortIdentifiers(){
        return CommPortIdentifier.getPortIdentifiers();
    }
}
