package promo.exception;

/**
 * Contains the exception NoMenuException.
 * Occurs when the user tries to remove a non-existing menu item from a promotion item.
 * @author Jordon
 * @version 1.0
 * @since 2021-04-15
 */
public class NoMenuException extends Exception {
	
	/**
	 * Alerts the user that no such menu item exists in the promotion item.
	 */
    public NoMenuException() {
        super("No menu in promotion");
    }
}
