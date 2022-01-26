package menu.exception;

/**
 * Contains the exception MenuNotFound.
 * Occurs when the user tries to retrieve a menu item that does not exist.
 * @author Jordon
 * @version 1.0
 * @since 2021-04-15
 */
public class MenuNotFound extends Exception{
	
	/**
	 * Alerts the user about the missing menu item.
	 */
    public MenuNotFound() {
        super("Menu not found.");
    }
}
