package ui;

import java.lang.annotation.Documented;

/**
 * Base interface defining a controller. Controllers are beans that contain
 * \@FXML fields. These fields are injected into the bean by JavaFX, and the
 * handlers defined in these controllers are used to perform actions
 */
@Documented
public @interface Controller {
}
