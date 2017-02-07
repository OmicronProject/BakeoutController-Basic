package exceptions;

/**
 * Thrown if an invalid device address is set.
 */
public class NotAllowedAddressException extends IllegalArgumentException {
    public NotAllowedAddressException(String message){
        super(message);
    }
}
