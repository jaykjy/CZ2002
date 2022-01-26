package reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.ParseException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import database.SerializeDB;
import database.exceptions.FailReadException;
import database.exceptions.FailWriteException;
import table.Table;
import table.TableController;
import table.TableStatus;
import table.exception.TableNotFound;
import reservation.exception.ReservationNotFound;

/**
 * Contains the methods to create, remove and display reservations to the user.
 * @author Jordon
 * @version 1.0
 * @since 2021-04-15
 */
public class ReservationController {
	
	/**
	 * Creates a file containing an ArrayList of reservations.
	 */
	public static void createReservationDatabaseFiles() {
		try {
			ArrayList<Reservation> reservations = new ArrayList<Reservation>();
			SerializeDB.writeSerializeObject("reservation.dat", reservations);
		} catch (FailWriteException e) {
            System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Checks for reservations that has expired for 30 minutes or longer and removes them. 
	 */
	public static void expiredReservations() {
		try {
			List<Reservation> reservations = SerializeDB.readSerializedObject("reservation.dat");
			Date ldate = new Date();
			float ltime = ldate.getTime();
			boolean expired = false;
			
			for (Reservation reservation : reservations) {
				float rtime = reservation.getDatetime().getTime();
				float timeDifference = ((ltime - rtime) / (1000 * 60)) % 60;
				if (timeDifference >= 30) {
					System.out.println("Reservation made by " + reservation.getContactNumber() + " has expired and is removed.");
					removeReservation(reservation.getContactNumber());
					expired = true;
				}
			}
			if (expired == false) System.out.println("No expired reservations found.");
		} catch (FailReadException | FailWriteException | ReservationNotFound | TableNotFound e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Creates a new reservation with at most 30 days in advance and reserves tables accordingly.
	 * 
	 * @param pax Number of customers of the reservation.
	 * @param contactNumber Contact number of the customer who made the reservation.
	 * @param datetime Date and time of the reservation.
	 * @throws FailReadException If the reservation or table files cannot be read.
	 * @throws FailWriteException If the created reservation cannot be written into the reservation file.
	 * @throws ParseException If the date and time cannot be parsed.
	 */
	public static void newReservation(int pax, int contactNumber, String datetime) throws FailReadException, FailWriteException, ParseException {
		Date rdatetime = new SimpleDateFormat("dd/MM/yyyy,HH:mm").parse(datetime);
		Date ldatetime = new Date();
		float timeDifference = rdatetime.getTime() - ldatetime.getTime();
		float dayDifference = timeDifference / (1000 * 60 * 60 * 24);
		
		if (dayDifference > 30) {
			System.out.println("Reservation can only be made in at most 1 month in advance.");
		} else {
			List<Reservation> reservations = SerializeDB.readSerializedObject("reservation.dat");
			if (TableController.tableAvailable() && pax <= 10) {
				String[] splitdatetime = datetime.split(",");
				String[] timeonly = splitdatetime[1].split(":");
				int hour = Integer.parseInt(timeonly[0]);
				int tableId;
				boolean found = false;
				
				if (hour < 12) {
					for (Table table : TableController.amAvailableTables()) {
						if (pax == table.getSize()) {
							tableId = table.getTableId();
							table.setAmStatus(TableStatus.RESERVED);
							TableController.saveTable(table);
							reservations.add(new Reservation(rdatetime, pax, contactNumber, tableId));
							SerializeDB.writeSerializeObject("reservation.dat", reservations);
							System.out.println("Reservation successful. Table " + tableId + " is reserved.");
							found = true;
							break;
						}
					}
					
					if (!found) {
						for (Table table : TableController.amAvailableTables()) {
							if (pax < table.getSize()) {
								tableId = table.getTableId();
								table.setAmStatus(TableStatus.RESERVED);
								TableController.saveTable(table);
								reservations.add(new Reservation(rdatetime, pax, contactNumber, tableId));
								SerializeDB.writeSerializeObject("reservation.dat", reservations);
								System.out.println("Reservation successful. Table " + tableId + " is reserved.");
								found = true;
								break;
							}
						}
					}
				} else {
					for (Table table : TableController.pmAvailableTables()) {
						if (pax == table.getSize()) {
							tableId = table.getTableId();
							table.setPmStatus(TableStatus.RESERVED);
							TableController.saveTable(table);
							reservations.add(new Reservation(rdatetime, pax, contactNumber, tableId));
							SerializeDB.writeSerializeObject("reservation.dat", reservations);
							System.out.println("Reservation successful. Table " + tableId + " is reserved.");
							found = true;
							break;
						}
					}
					
					if (!found) {
						for (Table table : TableController.pmAvailableTables()) {
							if (pax < table.getSize()) {
								tableId = table.getTableId();
								table.setPmStatus(TableStatus.RESERVED);
								TableController.saveTable(table);
								reservations.add(new Reservation(rdatetime, pax, contactNumber, tableId));
								SerializeDB.writeSerializeObject("reservation.dat", reservations);
								System.out.println("Reservation successful. Table " + tableId + " is reserved.");
								found = true;
								break;
							}
						}
					}
				}
				if (!found) System.out.println("No tables available to match size request.");
			} else {
				System.out.println("No tables available.");
			}
		}
	}

	/**
	 * Displays the details of the desired reservation.
	 * 
	 * @param contactNumber Contact number of the customer who made the reservation.
	 * @throws FailReadException If the reservation file cannot be read.
	 * @throws ReservationNotFound If the reservation does not exist.
	 */
	public static void checkReservation(int contactNumber) throws FailReadException, ReservationNotFound {
		List<Reservation> reservations = SerializeDB.readSerializedObject("reservation.dat");
		boolean found = false;

		for (Reservation reservation : reservations) {
			if (reservation.getContactNumber() == contactNumber) {
				System.out.println("----- Reservation Details -----");
				System.out.println("Date and Time: " + reservation.getDatetime());
				System.out.println("Number of customers: " + reservation.getPax());
				System.out.println("Contact Number: " + reservation.getContactNumber());
				System.out.println("Table ID: " + reservation.getTableID());
				found = true;
				break;
			}
		}
		if (found == false) { throw new ReservationNotFound(); }
	}

	/**
	 * Removes a reservation and vacates that table.
	 * 
	 * @param contactNumber Contact number of the customer who made the reservation.
	 * @throws FailReadException If the reservation file cannot be read.
	 * @throws FailWriteException If the reservation cannot be removed from the reservation file.
	 * @throws ReservationNotFound If the reservation does not exist.
	 * @throws TableNotFound If the table does not exist.
	 */
	public static void removeReservation(int contactNumber) throws FailReadException, FailWriteException, ReservationNotFound, TableNotFound {
		List<Reservation> reservations = SerializeDB.readSerializedObject("reservation.dat");
		boolean found = false;

		for (Reservation reservation : reservations) {
			if (reservation.getContactNumber() == contactNumber) {
				
				Date datetime = reservation.getDatetime();
				DateFormat df = new SimpleDateFormat("E M d HH:mm:ss z y");
				String strDatetime = df.format(datetime);
				String[] splitDatetime = strDatetime.split(" ");
				String[] timeonly = splitDatetime[3].split(":");
				int hour = Integer.parseInt(timeonly[0]);
				Table table = TableController.getTable(reservation.getTableID());
				
				if (hour < 12) {
					table.setAmStatus(TableStatus.VACATED);
				} else {
					table.setPmStatus(TableStatus.VACATED);
				}
				TableController.saveTable(table);
				reservations.remove(reservation);
				SerializeDB.writeSerializeObject("reservation.dat", reservations);
				System.out.println("Reservation removed.");
				found = true;
				break;
			}
		}
		if (found == false) { throw new ReservationNotFound(); }
	}
}
