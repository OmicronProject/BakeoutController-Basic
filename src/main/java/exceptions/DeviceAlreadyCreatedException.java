package exceptions;

/**
 * Thrown if a singleton device is created twice
 */
public class DeviceAlreadyCreatedException extends Exception {
    /**
     * Creates an exception with a given message
     * @param message A brief message describing why the exception was thrown
     */
    public DeviceAlreadyCreatedException(String message){
        super(message);
    }
}
