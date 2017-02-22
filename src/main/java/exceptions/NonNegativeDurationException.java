package exceptions;

import java.time.Duration;

/**
 * Thrown if a duration tries to be set that is non-negative
 */
public class NonNegativeDurationException extends Exception {
    public NonNegativeDurationException(Duration duration){
        super(constructMessage(duration));
    }

    private static String constructMessage(Duration duration){
        return String.format(
                "Attempted to set duration to %s", duration
        );
    }
}
