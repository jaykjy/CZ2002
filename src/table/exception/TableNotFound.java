package table.exception;
/**
 * Contains the exception TableNotFound.
 * Occurs when the user tries to retrieve a table that does not exist.
 * @author Sitian
 * @version 1.0
 * @since 2021-04-15
 */
public class TableNotFound extends Exception {
    /**
	 * Prompts the user to enter a valid table ID.
	 */
    public TableNotFound() {
        super("Table not found. Please enter a valid table ID.");
    }
}
