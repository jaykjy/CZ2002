package reservation.exception;

/**
 * Contains the exception ReservationNotFound
 * Occurs when the user tries to retrieve a reservation that does not exist.
 * @author Jordon
 * @version 1.0
 * @since 2021-04-15
 */
public class ReservationNotFound extends Exception {
	
	/**
	 * Prompts the user to make a new reservation.
	 */
	public ReservationNotFound() {
        super("Reservation not found for this contact number. Please make a new reservation for this contact number.");
    }
}
