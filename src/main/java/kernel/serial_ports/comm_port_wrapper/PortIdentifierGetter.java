package kernel.serial_ports.comm_port_wrapper;

import java.util.Enumeration;

/**
 * Wraps the Java Communications API, so that static method calls are placed
 * into an interface. This allows stubbing of the API, and therefore allows
 * effective unit testing.
 */
public interface PortIdentifierGetter {
    Enumeration getPortIdentifiers();
}
