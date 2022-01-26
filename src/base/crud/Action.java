package base.crud;

/**
 * Contains the enum values of the actions a user can perform on the Main Menu and promotions list.
 * @author Jordon
 * @version 1.0
 * @since 2021-04-15
 */
public enum Action {
	/**
	 * Creates a Menu or Promo object.
	 */
	CREATE,
	/**
	 * Displays details of a Menu or Promo object.
	 */
	READ,
	/**
	 * Updates a Menu or Promo object.
	 */
	UPDATE,
	/**
	 * Removes a Menu or Promo object.
	 */
	DELETE
}
