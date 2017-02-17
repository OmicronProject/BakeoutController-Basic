package kernel.views.variables;

import java.util.Date;

/**
 * Describes what it means to be a variable as a function of time
 */
public interface Variable<T> {
    Date getDate();

    T getValue();
}
