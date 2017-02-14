package exceptions;

/**
 * Thrown if the MODBUS library throws an {@link Exception}
 * The documentation or the exception
 * API isn't specific on what goes down when an exception is thrown, so I
 * went ahead and wrapped it in this.
 */
public class WrappedModbusException extends Exception {
    public WrappedModbusException(Throwable error){
        super(error);
    }
}
