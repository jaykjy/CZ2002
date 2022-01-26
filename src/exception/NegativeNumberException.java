package exception;

/**
 * Contains the exception EmptyFieldException.
 * Occurs when the user enters a negative number in fields that do not allow negative numbers.
 * @author Jordon
 * @version 1.0
 * @since 2021-04-15
 */
public class NegativeNumberException extends Exception {

	/**
	 * Prompts the user to enter a non-negative number.
	 */
    public NegativeNumberException() {
        super("Number cannot be negative.");
    }

    /**
     * Contains the specific message for NegativeFieldException.
     * 
     * @param msg The specific message for NegativeFieldException.
     */
    public NegativeNumberException(String msg) {
        super(msg);
    }

}
