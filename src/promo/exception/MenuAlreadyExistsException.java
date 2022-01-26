package promo.exception;

/**
 * Contains the exception MenuAlreadyExistsException.
 * Occurs when the user tries to add an existing menu item again into a promotion item.
 * @author Jordon
 * @version 1.0
 * @since 2021-04-15
 */
public class MenuAlreadyExistsException extends Exception {

	/**
	 * Alerts the user that the menu item already exists in the promotion item.
	 */
    public MenuAlreadyExistsException() {
        super("Menu already exists");
    }
}
