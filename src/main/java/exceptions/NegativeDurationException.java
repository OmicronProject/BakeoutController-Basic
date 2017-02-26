package exceptions;

import java.time.Duration;

/**
 * Thrown if a duration tries to be set that is non-negative
 */
public class NegativeDurationException extends Exception {
    /**
     * Create an instance of this exception
     * @param duration The offending duration that is to be reported in the
     *                 exception message
     */
    public NegativeDurationException(Duration duration){
        super(constructMessage(duration));
    }

    /**
     * Construct a string with a brief description of what went wrong
     * @param duration The duration from the constructor
     * @return A message describing the exception thrown
     */
    private static String constructMessage(Duration duration){
        return String.format(
                "Attempted to set duration to %s", duration
        );
    }
}
