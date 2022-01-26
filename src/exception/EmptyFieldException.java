package exception;

/**
 * Contains the exception EmptyFieldException.
 * Occurs when the user leaves a required field blank.
 * @author Jordon
 * @version 1.0
 * @since 2021-04-15
 */
public class EmptyFieldException extends Exception {
	
	/**
	 * Prompts the user to fill in the field.
	 */
    public EmptyFieldException() {
        super("This field is required.");
    }

    /**
     * Contains the specific message for EmptyFieldException.
     * 
     * @param msg The specific message for EmptyFieldException.
     */
    public EmptyFieldException(String msg) {
        super(msg);
    }
}
