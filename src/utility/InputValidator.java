package utility;

/**
 * Validates user inputs in Menu and Promo classes.
 * @author Jordon
 * @version 1.0
 * @since 2021-04-15
 */
public class InputValidator {
	/**
	 * No attributes to be set by the constructor here.
	 */
    private InputValidator() {};
    
    /**
     * Checks if the user enters a negative number as input.
     * 
     * @param number Numeric input by the user.
     * @return True if the numeric input is negative. Else, false.
     */
    public static boolean isNegativeNumber(double number) { return number < 0; }
    
    /**
     * Checks if the user leaves a String field empty.
     * 
     * @param str String input by the user.
     * @return True if the input is empty. Else, false.
     */
    public static boolean isEmptyString(String str) { return str.isEmpty(); }
}
