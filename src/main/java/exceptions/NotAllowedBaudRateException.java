package exceptions;

/**
 * Thrown if attempting to set a Baud Rate for a device that does not support
 * the required Baud rate
 */
public class NotAllowedBaudRateException extends IllegalArgumentException {
    public NotAllowedBaudRateException(String message){
        super(message);
    }
}
