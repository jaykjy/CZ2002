package base;

import java.io.Serializable;

/**
 * Template for Menu and Promo classes with constructor, ID attribute and ID getter.
 * @author Jordon
 * @version 1.0
 * @since 2021-04-15
 */
public class BaseModel implements Serializable {
	/**
	 * ID of the Menu or Promo object.
	 */
    private int id;

    /**
     * Constructor sets the ID of the Menu or Promo object.
     * 
     * @param id ID of the Menu or Promo object.
     */
    public BaseModel(int id) {
    	this.id = id;
    }
    
    /**
     * Gets the ID of the Menu or Promo object.
     * @return ID of the Menu of Promo object.
     */
    public int getId() { return id; }
}
