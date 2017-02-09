package kernel;

import exceptions.UnableToCreateKernelException;
import kernel.models.Kernel;
import kernel.serial_ports.PortDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implements methods for bootstrapping the hardware kernel
 */
public final class ApplicationKernelFactory implements KernelFactory {
    /**
     * The port driver to add to the kernel
     */
    private PortDriver portDriver;

    /**
     * True if the kernel exists and false if not
     */
    private Boolean doesKernelExist;

    /**
     * The instance of the kernel that is to be returned
     */
    private kernel.Kernel kernelInstance;

    /**
     * True if a port driver has been supplied and false if not
     */
    private Boolean hasPortDriver;

    private static Logger log = LoggerFactory.getLogger
            (ApplicationKernelFactory.class);

    public ApplicationKernelFactory(){
        this.doesKernelExist = Boolean.FALSE;
        this.hasPortDriver = Boolean.FALSE;
    }

    /**
     * @param newPortDriver The port driver to use in the Kernel
     */
    @Override public void setPortDriver(PortDriver newPortDriver){
        log.debug("Setting port driver to {}", newPortDriver.toString());

        this.portDriver = newPortDriver;
        this.hasPortDriver = Boolean.TRUE;
    }

    /**
     * If the kernel already exists, returns the current Kernel installation
     * . If the kernel has not been instantiated, create the kernel and
     * return it.
     *
     * @return The kernel
     * @throws UnableToCreateKernelException If the kernel cannot be created
     */
    @Override public kernel.Kernel getKernelInstance() throws
            UnableToCreateKernelException {
        if (!canKernelBeStarted()){
            throw new UnableToCreateKernelException("Cannot create kernel. " +
                    "Parameter missing");
        }
        if (!doesKernelExist){
            createKernel();
        }
        return this.kernelInstance;
    }

    /**
     * @return True if the kernel can be started, otherwise false
     */
    @Override public Boolean canKernelBeStarted(){
        if (!hasPortDriver){
            return Boolean.FALSE;
        } else {
            return Boolean.TRUE;
        }
    }

    /**
     * Make the kernel. This should be called only if a kernel doesn't
     * already exist.
     */
    private void createKernel(){
        this.kernelInstance = new Kernel(
            this.portDriver
        );
        this.doesKernelExist = Boolean.TRUE;
    }
}
