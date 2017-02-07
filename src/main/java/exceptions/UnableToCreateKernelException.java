package exceptions;

/**
 * Thrown if the {@link kernel.KernelFactory} cannot create a
 * {@link kernel.Kernel}
 */
public class UnableToCreateKernelException extends IllegalStateException {
    public UnableToCreateKernelException(String message){
        super(message);
    }
}
