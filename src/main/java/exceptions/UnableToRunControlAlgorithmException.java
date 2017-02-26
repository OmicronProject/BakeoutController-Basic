package exceptions;

/**
 * Thrown if a control algorithm is run without the required parameters
 * running.
 */
public class UnableToRunControlAlgorithmException extends Exception {
    /**
     * @param message A message explaining why the exception was thrown
     */
    public UnableToRunControlAlgorithmException(String message){
        super(message);
    }
}
