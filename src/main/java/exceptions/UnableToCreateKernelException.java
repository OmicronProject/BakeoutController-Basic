package exceptions;

/**
 * Thrown if the {@link kernel.KernelFactory} cannot create a
 * {@link kernel.Kernel}
 */
public class UnableToCreateKernelException extends IllegalStateException {
    /**
     * @param message A brief message explaining why the exception was thrown
     */
    public UnableToCreateKernelException(String message){
        super(message);
    }
}
