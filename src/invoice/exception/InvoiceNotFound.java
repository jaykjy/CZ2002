package invoice.exception;

/**
 * Contains the exception InvoiceNotFound.
 * Occurs when the user tries to retrieve an invoice that does not exist.
 * @author Jordon
 * @version 1.0
 * @since 2021-04-15
 */
public class InvoiceNotFound extends Exception {
	
	/**
	 * Alerts the user that there are no matching invoice ID.
	 */
    public InvoiceNotFound() {
        super("No invoice with this ID found!");
    }
}
