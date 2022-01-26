package order.exception;

/**
 * Contains the exception OrderNotFound.
 * Occurs when the user tries to retrieve an order that does not exist.
 * @author Jordon
 * @version 1.0
 * @since 2021-04-15
 */
public class OrderNotFound extends Exception {
	
	/**
	 * Prompts the user to create an order first.
	 */
    public OrderNotFound() {
        super("Order not found for this table. Please create a new order for this table.");
    }
}
