package reservation;

import java.util.Scanner;
import java.text.ParseException;
import database.exceptions.FailReadException;
import database.exceptions.FailWriteException;
import reservation.exception.ReservationNotFound;
import table.TableController;
import table.exception.TableNotFound;

/**
 * Displays the Reservation UI when the user selects Reservation in the main UI.
 * @author Jordon
 * @version 1.0
 * @since 2021-04-15
 */
public class ReservationUI {

	/**
	 * Prompts the user to choose between creating, removing or viewing a reservation,
	 * or viewing the status of all tables,
	 * or removing expired reservations,
	 * or quit.  
	 */
	public void mainApp() {
		int option;
		Scanner sc = new Scanner(System.in);

		do {
			System.out.println("****************");
			System.out.println("Reservation Page");
			System.out.println("****************");
			System.out.println("1. Create a reservation");
			System.out.println("2. Check a reservation");
			System.out.println("3. Remove a reservation");
			System.out.println("4. Check table status");
			System.out.println("5. Clear expired reservations");
			System.out.println("6. Quit");

			option = sc.nextInt(); // Read user option
            sc.nextLine(); // Read newline character after hitting Enter key

			switch(option) {
				case 1:
					createReservationUI();
					break;
				case 2:
					checkReservationUI();
					break;
				case 3:
					removeReservationUI();
					break;
				case 4:
					checkTableStatusUI();
					break;
				case 5:
					clearExpiredUI();
					break;
				case 6:
					System.out.println("Exiting...");
					break;
				default:
                    System.out.println("Please choose a valid option.");
			}
		} while (option != 6);
	}
	
	/**
	 * Prompts the user to enter reservation details to make a new reservation.
	 */
	public static void createReservationUI() {
		try {
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter number of customers: ");
			int pax = sc.nextInt();
			System.out.println("Enter customer's contact number: ");
			int contactNumber = sc.nextInt();
			System.out.println("Enter reservation date and time (dd/MM/yyyy,HH:mm): ");
			String datetime = sc.next();
			ReservationController.newReservation(pax, contactNumber, datetime);
		} catch (FailReadException | FailWriteException | ParseException e) {
            System.out.println(e.getMessage());
		}
	};
	
	/**
	 * Prompts the user to enter the contact number of the customer who made the reservation they wish to view.
	 */
	public static void checkReservationUI() {
		try {
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter customer's contact number: ");
			int contactNumber = sc.nextInt();
			ReservationController.checkReservation(contactNumber);
		} catch (FailReadException | ReservationNotFound e) {
            System.out.println(e.getMessage());
        }
	};
	
	/**
	 * Prompts the user to enter the contact number of the customer who made the reservation they wish to remove.
	 */
	public static void removeReservationUI() {
		try {
			Scanner sc = new Scanner(System.in);
			System.out.println("Enter customer's contact number: ");
			int contactNumber = sc.nextInt();
			ReservationController.removeReservation(contactNumber);
		} catch (FailReadException | FailWriteException | ReservationNotFound | TableNotFound e) {
            System.out.println(e.getMessage());
        }
	};
	
	/**
	 * Displays the status of all tables. 
	 */
	public static void checkTableStatusUI() {
		try {
			TableController.showTableStatus();
		} catch (FailReadException e) {
            System.out.println(e.getMessage());
        }
	};
	
	/**
	 * Clears reservations that have expired for more than 30 minutes.
	 */
	public static void clearExpiredUI() {
		ReservationController.expiredReservations();
	}
}
