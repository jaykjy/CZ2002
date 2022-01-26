package menu.exception;

/**
 * Contains the exception MenuInPromoException.
 * Occurs when the user tries to remove a menu item from the Main Menu before removing it from the promotions list.
 * @author Jordon
 * @version 1.0
 * @since 2021-04-15
 */
public class MenuInPromoException extends Exception {
	
	/**
	 * Prompts the user to remove the menu item from the promotions list first.
	 * 
	 * @param promoId ID of the menu item that the user is trying to remove.
	 */
    public MenuInPromoException(int promoId) {
        super("Menu is in promotion ID " + promoId  + ". Please remove the menu from the promotion first.");
    }
}
