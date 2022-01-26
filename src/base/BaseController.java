package base;

import java.util.ArrayList;

/**
 * Contains methods shared by Menu and Promo controller classes. 
 * @author Jordon
 * @version 1.0
 * @since 2021-04-15
 */
public class BaseController {
	/**
	 * Fixed keyword '-' for the user to enter if they do not wish to make changes to an attribute when updating a Menu or Promo object.
	 */
    private static final String SKIP = "-";
    
    /**
     * Checks if the user enters '-'.
     * 
     * @param userInput Input from the user.
     * @return True if the user enters '-'. Else, false.
     */
    public static boolean isSkipUpdate(String userInput) { return userInput.equals(SKIP); }
    
    /**
     * Gets keyword '-'.
     * 
     * @return Keyword '-'.
     */
    public static String getSkipKeyword() { return SKIP; }

    /**
     * Gets the Menu or Promo object.
     * 
     * @param <T> Object type (Menu or Promo only).
     * @param items ArrayList of objects (Menu or Promo only).
     * @param id ID of the Menu or Promo object.
     * @return Menu or promotion item.
     */
    public static <T extends BaseModel> T findById(ArrayList<T> items, int id) {
        for (BaseModel item: items) {
            if (item.getId() == id) {
                return (T)item;
            }
        }
        return null;
    }

    /**
     * Generates an ID for Menu or Promo object.
     * 
     * @param <T> Object type (Menu or Promo only).
     * @param items ArrayList of objects (Menu or Promo only).
     * @return Menu or Promo object.
     */
    public static <T extends BaseModel> int generateId(ArrayList<T> items) {
        if (items.size() == 0) {
            return 1;
        }
        T latestItem = items.get(items.size() - 1);
        return latestItem.getId() + 1;
    }
}
