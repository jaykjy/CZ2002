package database.exceptions;

/**
 * Contains the exception FailWriteException.
 * Occurs when the user tries to write an invalid list of objects into a file.
 * @author Jordon
 * @version 1.0
 * @since 2021-04-15
 */
public class FailWriteException extends Exception {
	/**
	 * Alerts the user that the file writing could not be executed.
	 */
    public FailWriteException() {
        super("Fail to save to file");
    }
}
