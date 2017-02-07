package kernel.serial_ports.comm_port_wrapper;

import java.util.Enumeration;

/**
 * Describes how the wrapper works
 */
public interface PortIdentifierGetter {
    Enumeration getPortIdentifiers();
}
