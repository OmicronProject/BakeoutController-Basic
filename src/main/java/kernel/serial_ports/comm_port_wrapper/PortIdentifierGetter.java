package kernel.serial_ports.comm_port_wrapper;

import gnu.io.CommPortIdentifier;

import java.util.Enumeration;

/**
 * Wraps the Java Communications API, so that static method calls are placed
 * into an interface. This allows stubbing of the API, and therefore allows
 * effective unit testing.
 */
public interface PortIdentifierGetter {

    /**
     * @return An {@link Enumeration} of {@link gnu.io.CommPortIdentifier}
     * available for communication to this application. This is the same
     * form as returned by {@link CommPortIdentifier#getPortIdentifiers()}
     */
    Enumeration getPortIdentifiers();
}
