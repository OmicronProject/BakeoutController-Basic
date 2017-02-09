package exceptions;

/**
 * Thrown if a singleton device is created twice
 */
public class DeviceAlreadyCreatedException extends Exception {
    public DeviceAlreadyCreatedException(String message){
        super(message);
    }
}
