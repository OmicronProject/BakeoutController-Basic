package exceptions;

/**
 * Created by mkononen on 06/02/17.
 */
public class DeviceNotCreatedException extends Exception {
    public DeviceNotCreatedException(String message){
        super(message);
    }

    public DeviceNotCreatedException(Throwable error){
        super(error);
    }
}
