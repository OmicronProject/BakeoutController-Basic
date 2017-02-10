package exceptions;

/**
 * Thrown if the MODBUS library fscks up. The documentation or the exception
 * API isn't specific on what goes down when an exception is thrown, so I
 * went ahead and wrapped it in this.
 */
public class WrappedExceptionFromModbus extends Exception {
    public WrappedExceptionFromModbus(Throwable error){
        super(error);
    }
}
