package promo.exception;

/**
 * Contains the exception PromoNotFoundException.
 * Occurs when the user tries to retrieve a promotion item that does not exist.
 * @author Jordon
 * @version 1.0
 * @since 2021-04-15
 */
public class PromoNotFoundException extends Exception{
	
	/**
	 * Alerts the user that the promotion item does not exist.
	 */
    public PromoNotFoundException() {
        super("Promotion not found");
    }
}
