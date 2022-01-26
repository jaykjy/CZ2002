package base.crud;

/**
 * For the Menu and Promo classes to allow the user to delete a menu or promotion item.
 * @author Jordon
 * @version 1.0
 * @since 2021-04-15
 */
public interface Deletable {

	/**
	 * Deletes a menu or promotion object.
	 */
    void delete();
}
