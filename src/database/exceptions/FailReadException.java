package database.exceptions;

/**
 * Contains the exception FailReadException.
 * Occurs when the user tries to read data from a file that does not exist.
 * @author Jordon
 * @version 1.0
 * @since 2021-04-15
 */
public class FailReadException extends Exception {
	
	/**
	 * Alerts the user that the file reading could not be executed.
	 */
    public FailReadException() {
        super("Fail to read from file");
    }
}
